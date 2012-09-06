package com.counter.dto;

public class ClickDTO {

	private double latitude;

	private double longitude;

	private String name;

	private int count;

	private long userId;

	public ClickDTO(long userId, double latitude, double longitude,
			String name, int count) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.name = name;
		this.count = count;
		this.userId = userId;
	}

	public ClickDTO() {
		super();
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
