package com.chinait.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.chinait.dao.MusicRepository;
import com.chinait.domain.Music;
import com.chinait.service.MusicService;
import com.chinait.vo.MusicVO;
import com.chinait.vo.PageVO;
@Service
public class MusicServiceImpl implements MusicService{
	@Autowired
	MusicRepository musicRepository;
	@Override
	@Transactional
	public void saveMusic(Music music) {
		musicRepository.save(music);
	}
	@Override
	public List<MusicVO> findUserMusic() {
		List<Music> list = musicRepository.findByIsDeleteFalse();
		List<MusicVO> returnList = new ArrayList<MusicVO>();
		for(Music music:list){
			MusicVO musicVo = new MusicVO();
			musicVo.setFilePath(music.getFilePath());
			musicVo.setMusicName(music.getMusicName());
			returnList.add(musicVo);
		}
		return  returnList;
	}
	@Override
	@Transactional
	public void deleteMusic(int musicId) {
		Music music = musicRepository.findOne(musicId);
		music.setDelete(true);
		musicRepository.save(music);
	}
	@Override
	public void findUserMusic(PageVO<MusicVO> pageVO) {
		Pageable page = new PageRequest(pageVO.getCurrentPage(), pageVO.getPageSize());
		Page<Music> pages = musicRepository.findByIsDeleteFalse(page);
		List<MusicVO> returnList = new ArrayList<MusicVO>();
		for(Music music:pages.getContent()){
			MusicVO musicVo = new MusicVO();
			musicVo.setId(music.getId());
			musicVo.setFilePath(music.getFilePath());
			musicVo.setMusicName(music.getMusicName());
			returnList.add(musicVo);
		}
		pageVO.setReturnList(returnList);
		pageVO.setTotalApps(pages.getTotalElements());
    	pageVO.setTotalPages(pages.getTotalPages());
	}
	@Override
	@Transactional
	public void updateMusic(int musicId, String musicName) {
		Music music = musicRepository.findOne(musicId);
		music.setMusicName(musicName);
		musicRepository.save(music);
	}
}
