package com.user.eao;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.user.model.User;

/**
 * Session Bean implementation class UseEventEAO
 */
@Stateless
@LocalBean
public class UserEAO {

	@PersistenceContext
	EntityManager em;

	public UserEAO() {
	}

	public User getUserById(Long id) {
		User result = null;

		try {
			result = em.find(User.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public User getUserByEmail(String email) {
		User result = null;

		if (email == "" || email == null)
			return result;

		Query q = em
				.createQuery("SELECT u FROM USERS u WHERE u.email = :email");
		q.setParameter("email", email);

		try {
			result = (User) q.getSingleResult();
		} catch (Exception e) {
			// do nothing
		}
		// TODO: what to do when there is no user

		return result;
	}

	public User getUserByUserName(String username) {
		User result = null;

		if (username == "" || username == null)
			return result;

		Query q = em
				.createQuery("SELECT u FROM USERS u WHERE u.userName = :username");
		q.setParameter("username", username);

		try {
			result = (User) q.getSingleResult();
		} catch (Exception e) {
			// do nothing
		}
		// TODO: what to do when there is no user

		return result;
	}

	@SuppressWarnings("unchecked")
	public Collection<User> dumpUsers() {

		Collection<User> users;
		Query q = em.createQuery("SELECT u FROM USERS u");

		users = new ArrayList<User>(q.getResultList());

		return users;
	}

	public String createUser(String username, String firstName,
			String lastName, String email, String password, String salt)
			throws NoSuchAlgorithmException {

		User user = new User();

		user.setUserName(username);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);

		// checking if email || username exists
		User testUser = getUserByUserName(username);

		if (testUser != null)
			return "-1";
		
		testUser = getUserByEmail(email);

		if (testUser != null)
			return "-1";

		em.persist(user);

		return user.getId() + "";
	}

	public String getUserCount() {

		Long result;

		Query query = em.createQuery("SELECT COUNT(u.userName) FROM USERS u");

		result = (Long) query.getSingleResult();

		return result + "";
	}
}
