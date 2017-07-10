package com.chinait.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.chinait.domain.Music;
import com.chinait.domain.User;

public interface MusicRepository extends CrudRepository<Music,Integer>{

	List<Music> findByUser(User user);

	List<Music> findByMusicTypeIdAndIsDeleteFalse(int musicTypeId);

	List<Music> findByIsDeleteFalse();

	Page<Music> findByIsDeleteFalse(Pageable page);

}
