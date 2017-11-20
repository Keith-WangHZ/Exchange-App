package com.example.huazi.campusexchange.model.bean;

import java.io.Serializable;

public class CityModel implements Serializable {

	private int cityID;
	private int parentId;
	private int level;
	private String name;
	private String pinyin;

	public CityModel(){}

	public CityModel(int cityID, int parentId, int level, String name, String pinyin){
		this.cityID = cityID;
		this.parentId = parentId;
		this.level = level;
		this.name = name;
		this.pinyin = pinyin;
	}

	public int getCityID() {
		return cityID;
	}

	public void setCityID(int cityID) {
		this.cityID = cityID;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}


}
