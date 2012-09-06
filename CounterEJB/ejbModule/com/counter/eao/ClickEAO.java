package com.counter.eao;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.counter.model.Click;

/**
 * Session Bean implementation class UseEventEAO
 */
@Stateless
@LocalBean
public class ClickEAO {

	@PersistenceContext
	EntityManager em;

	public ClickEAO() {
	}

	public Click getClickByClickName(String Clickname) {
		Click result = null;

		if (Clickname == "" || Clickname == null)
			return result;

		Query q = em
				.createQuery("SELECT u FROM ClickS u WHERE u.ClickName = :Clickname");
		q.setParameter("Clickname", Clickname);

		try {
			result = (Click) q.getSingleResult();
		} catch (Exception e) {
			// do nothing
		}
		// TODO: what to do when there is no Click

		return result;
	}

	@SuppressWarnings("unchecked")
	public Collection<Click> dumpClicks() {

		Collection<Click> Clicks;
		Query q = em.createQuery("SELECT u FROM Click u");

		Clicks = new ArrayList<Click>(q.getResultList());

		return Clicks;
	}

	public String createClick(long userId, double latitude, double longitude,
			String name, int count) {

		String result = "-11";

		Click Click = new Click();

		Click.setName(name);
		Click.setUserId(userId);
		Click.setLatitude(latitude);
		Click.setLongitude(longitude);
		Click.setCount(count);

		try {
			em.persist(Click);

			result = "1";

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return result;
		}
		return result;

	}

	public String getClickCount() {

		Long result;

		Query query = em.createQuery("SELECT COUNT(u.name) FROM Click u");

		result = (Long) query.getSingleResult();

		return result + "";
	}
}
