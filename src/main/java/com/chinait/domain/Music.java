package com.chinait.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.chinait.domain.User;
import javax.persistence.ManyToOne;
import com.chinait.domain.MusicType;

@Entity
public class Music {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String filePath;
	private String musicName;
	private boolean isDelete;
	@ManyToOne
	private User user;
	@ManyToOne
	private MusicType musicType;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public User getUser() {
	    return user;
	}
	public void setUser(User param) {
	    this.user = param;
	}
	public String getMusicName() {
		return musicName;
	}
	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}
	public MusicType getMusicType() {
	    return musicType;
	}
	public void setMusicType(MusicType param) {
	    this.musicType = param;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
}

