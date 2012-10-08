package com.android.counterapp.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Count {

	private String name;

	private Date date;

	private Collection<Click> clicks;

	public Count() {
		this.date = new Date();
		this.clicks = new ArrayList<Click>();
	}

	public Count(String name) {
		this.name = name;
		this.date = new Date();
		this.clicks = new ArrayList<Click>();
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

	public void addClicks(Collection<Click> clicks) {
		this.clicks.addAll(clicks);
	}

	public Collection<Click> getClicks() {
		return this.clicks;
	}
}
