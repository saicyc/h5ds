package com.chinait.service;

import java.util.List;

import com.chinait.domain.Music;
import com.chinait.vo.MusicVO;
import com.chinait.vo.PageVO;

public interface MusicService {

	void saveMusic(Music music);

	List<MusicVO> findUserMusic();

	void deleteMusic(int musicId);

	void updateMusic(int musicId, String musicName);

	void findUserMusic(PageVO<MusicVO> page);
}
