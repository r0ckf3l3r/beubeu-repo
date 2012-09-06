package com.counter.service;

import java.util.Collection;

import javax.ejb.Remote;

import com.counter.dto.ClickDTO;

@Remote
public interface CounterBeanInterface {

	public String createClick(long userId, double latitude, double longitude,
			String name, int count);

	public Collection<ClickDTO> dumpClicks();

	public String getClickCount();

}
