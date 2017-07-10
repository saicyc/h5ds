package com.chinait.service;

import java.util.List;

import com.chinait.domain.MusicType;

public interface MusicTypeService {

	List<MusicType> findMusicType();

	void addMusicType(String musicTypeName);

}
