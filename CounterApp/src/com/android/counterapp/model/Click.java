package com.android.counterapp.model;

import java.util.Date;

public class Click {

	private Date date;

	private double latitude;

	private double longitude;

	private String name;

	private int count;

	private long userId;

	public Click() {
	}

	public Click(long userId, double latitude, double longitude, String name,
			int count, Date date) {
		super();

		this.userId = userId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.name = name;
		this.date = date;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

}
