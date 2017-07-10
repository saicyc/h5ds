package com.chinait.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MusicType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String musicTypeName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMusicTypeName() {
		return musicTypeName;
	}
	public void setMusicTypeName(String musicTypeName) {
		this.musicTypeName = musicTypeName;
	}
}
