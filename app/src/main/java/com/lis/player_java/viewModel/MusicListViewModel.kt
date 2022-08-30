package com.lis.player_java.viewModel

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lis.player_java.data.MusicRemoteMediator
import com.lis.player_java.data.repository.MusicRepository
import com.lis.player_java.data.room.MusicDB
import com.lis.player_java.data.room.MusicDatabase
import kotlinx.coroutines.flow.Flow

class MusicListViewModel(
    private val repository: MusicRepository,
    private val database: MusicDatabase
) : ViewModel() {
    val pagingMusicList: Flow<PagingData<MusicDB>>

    init {
        pagingMusicList = getMusicList()
    }

    private fun getMusicList(): Flow<PagingData<MusicDB>> {
        val pagingSourceFactory = { database.musicDao().getPagingMusicList() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = true,
                initialLoadSize = INITIAL_SIZE
            ),
            remoteMediator = MusicRemoteMediator(repository, database),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    companion object {
        const val PAGE_SIZE = 20
        const val INITIAL_SIZE = 2
    }

}