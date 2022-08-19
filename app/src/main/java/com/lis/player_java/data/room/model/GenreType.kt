package com.lis.player_java.data.room.model

import kotlin.collections.HashMap

enum class GenreType(private val id: Int) {
    IDK(0),
    Rock(1),
    Pop(2),
    Rap_Hip_Hop(3),
    Easy_Listening(4),
    House_Dance(5),
    Instrumental(6),
    Metal(7),
    Dubstep(8),
    Drum_Bass(10),
    Trance(11),
    Chanson(12),
    Ethnic(13),
    Acoustic_Vocal(14),
    Reggae(15),
    Classical(16),
    Indie_Pop(17),
    Other(18),
    Speech(19),
    Alternative(21),
    Electropop_Disco(22),
    Jazz_Blues(1001);

    companion object {
        private val map = HashMap<Int, GenreType>()
        fun getGenreById(id: Int): GenreType {
            return map.getOrDefault(id, IDK)
        }

        init {
            for (v in values()) {
                map[v.id] = v
            }
        }
    }
}