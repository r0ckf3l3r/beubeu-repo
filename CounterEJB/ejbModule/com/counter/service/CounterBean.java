package com.counter.service;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jws.WebService;

import com.counter.dto.ClickDTO;
import com.counter.eao.ClickEAO;
import com.counter.model.Click;
import com.counter.util.Conversion;

/**
 * Session Bean implementation class UserBean
 */
@WebService
@Stateless
@LocalBean
public class CounterBean implements CounterBeanInterface {

	@EJB
	ClickEAO clicksManager;

	@EJB
	Conversion conversion;

	public CounterBean() {
		// System.out.println("UserBean: creating new bean");
	}

	@Override
	public String createClick(long userId, double latitude, double longitude,
			String name, int count) {

		String result = "-1";

		result = clicksManager.createClick(userId, latitude, longitude, name,
				count);

		return result;
	}

	@Override
	public Collection<ClickDTO> dumpClicks() {
		Collection<ClickDTO> result;

		Collection<Click> clicks = clicksManager.dumpClicks();

		result = conversion.convertClicks(clicks);

		return result;
	}

	@Override
	public String getClickCount() {
		return clicksManager.getClickCount();
	}

}
