package com.lis.player_java.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.RemoteMediator
import com.lis.player_java.data.room.model.MusicDB
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.room.withTransaction
import com.lis.player_java.data.model.Item
import com.lis.player_java.data.repository.MusicRepository
import com.lis.player_java.data.room.MusicDatabase
import com.lis.player_java.data.room.model.AlbumForMusic
import com.lis.player_java.data.room.model.GenreType
import com.lis.player_java.data.room.model.RemoteKeys
import okio.IOException
import retrofit2.*

private const val STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class MusicRemoteMediator(
    private val repository: MusicRepository,
    private val database: MusicDatabase
) : RemoteMediator<Int, MusicDB>() {


    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MusicDB>
    ): MediatorResult {
        val pageSize = state.config.pageSize
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeysClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1)?: STARTING_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeysForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
        }

        try {
            val (musicList, endOfPaginationReached) = getMusicList(pageSize, pageSize * page)
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.musicDao().deleteMusic()
                    database.musicDao().clearRemoteKeysTable()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                database.musicDao().insertMusicList(musicList)
                val keys =
                    database.musicDao().musicList.takeLast(pageSize).map {
                        RemoteKeys(
                            it.id,
                            prevKey?:0,
                            nextKey?:0
                        )
                    }

                database.musicDao().insertAllKeys(keys)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getMusicList(
        count: Int,
        offset: Int
    ): Pair<List<MusicDB>?, Boolean> {
        val musicList =
            repository.getMusicList(count, offset).awaitResponse().body()?.response?.items?.map {
                MusicDB(
                    it.id,
                    it.ownerID,
                    "${it.id}_${it.ownerID}",
                    it.artist,
                    it.title,
                    it.url,
                    it.album.thumb.photo600,
                    it.album.thumb.photo1200,
                    it.duration,
                    it.isExplicit,
                    AlbumForMusic(it.id, it.ownerID, it.accessKey),
                    it.lyricsID,
                    GenreType.getGenreById(it.genreID.toInt())
                )
            }
        val endOfPaginationReached = musicList.isNullOrEmpty()
        return Pair(musicList, endOfPaginationReached)
    }

    private suspend fun getRemoteKeysClosestToCurrentPosition(state: PagingState<Int, MusicDB>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id.let { musicId ->
                database.musicDao().getRemoteKey(musicId)
            }
        }
    }

    private suspend fun getRemoteKeysForLastItem(state: PagingState<Int, MusicDB>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty()}?.data?.lastOrNull()?.let { repo ->
            database.musicDao().getRemoteKey(repo.id)
        }
    }

}