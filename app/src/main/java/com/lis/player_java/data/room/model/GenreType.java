package com.lis.player_java.data.room.model;

import java.util.HashMap;
import java.util.Map;

public enum GenreType {
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

	private final int id;

	GenreType(int id) {
		this.id = id;
	}

	private static final Map<Integer,GenreType> map;
	static {
		map = new HashMap();
		for (GenreType v : GenreType.values()) {
			map.put(v.id, v);
		}
	}
	public static GenreType getGenreById(int id) {
		return map.get(id);
	}
}
