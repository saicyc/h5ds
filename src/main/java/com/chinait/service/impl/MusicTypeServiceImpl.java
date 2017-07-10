package com.chinait.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinait.dao.MusicTypeRepository;
import com.chinait.domain.MusicType;
import com.chinait.service.MusicTypeService;
@Service
public class MusicTypeServiceImpl implements MusicTypeService {
	@Autowired
	MusicTypeRepository musicTypeRepository;
	@Override
	public List<MusicType> findMusicType() {
		return (List<MusicType>)musicTypeRepository.findAll();
	}
	@Override
	public void addMusicType(String musicTypeName) {
		MusicType type = new MusicType();
		type.setMusicTypeName(musicTypeName);
		musicTypeRepository.save(type);
	}

}
