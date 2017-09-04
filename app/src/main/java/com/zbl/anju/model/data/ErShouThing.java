package com.zbl.anju.model.data;

/**
 * 二手物品
 * Created by James on 17-9-4.
 */

public class ErShouThing {
	private String name;
	private String price;
	private String distance;
	private String rerleaseTime;
	private String description;
	private String imgUrl;

	public ErShouThing() {

	}

	public ErShouThing(String name, String price, String distance, String rerleaseTime, String description, String imgUrl, String videoUrl) {
		this.name = name;
		this.price = price;
		this.distance = distance;
		this.rerleaseTime = rerleaseTime;
		this.description = description;
		this.imgUrl = imgUrl;
		this.videoUrl = videoUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getRerleaseTime() {
		return rerleaseTime;
	}

	public void setRerleaseTime(String rerleaseTime) {
		this.rerleaseTime = rerleaseTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	private String videoUrl;
}
