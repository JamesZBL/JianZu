package com.zbl.anju.model.data;

import com.tencent.mapsdk.raster.model.LatLng;

/**
 * 房源
 * Created by James on 17-9-2.
 */

public class House {

	private String id;
	private String name;
	private String description;
	private String xiaoquName;
	private String distance;
	private String releaseTime;
	private String price;
	private String hostName;
	private String hostPhoneNum;
	private String urlVideo;
	private String urlVideoThumbnail;
	private String canDao;
	private String canAddFir;
	private LatLng latLng;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getXiaoquName() {
		return xiaoquName;
	}

	public void setXiaoquName(String xiaoquName) {
		this.xiaoquName = xiaoquName;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getHostPhoneNum() {
		return hostPhoneNum;
	}

	public void setHostPhoneNum(String hostPhoneNum) {
		this.hostPhoneNum = hostPhoneNum;
	}

	public String getUrlVideo() {
		return urlVideo;
	}

	public void setUrlVideo(String urlVideo) {
		this.urlVideo = urlVideo;
	}

	public String getUrlVideoThumbnail() {
		return urlVideoThumbnail;
	}

	public void setUrlVideoThumbnail(String urlVideoThumbnail) {
		this.urlVideoThumbnail = urlVideoThumbnail;
	}

	public String getCanDao() {
		return canDao;
	}

	public void setCanDao(String canDao) {
		this.canDao = canDao;
	}

	public String getCanAddFir() {
		return canAddFir;
	}

	public void setCanAddFir(String canAddFir) {
		this.canAddFir = canAddFir;
	}

	public LatLng getLatLng() {
		return latLng;
	}

	public void setLatLng(LatLng latLng) {
		this.latLng = latLng;
	}
}
