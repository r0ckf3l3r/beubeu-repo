package com.user.service;

import java.security.NoSuchAlgorithmException;
import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jws.WebService;

import com.user.dto.UserDTO;
import com.user.eao.UserEAO;
import com.user.model.User;
import com.user.util.BCrypt;
import com.user.util.Conversion;

/**
 * Session Bean implementation class UserBean
 */
@WebService
@Stateless
@LocalBean
public class UserBean implements UserBeanInterface {

	@EJB
	UserEAO userManager;

	@EJB
	Conversion conversion;

	public UserBean() {
		// System.out.println("UserBean: creating new bean");
	}

	@Override
	public UserDTO getUserById(Long id) {

		User user = userManager.getUserById(id);

		UserDTO result = null;

		if (user != null)
			result = conversion.fromEntity(user);

		return result;
	}

	@Override
	public String createUser(String username, String firstName,
			String lastName, String email, String password) {

		String result = "-1";

		String salt = BCrypt.gensalt();
		// Hash a password for the first time
		String hashed = BCrypt.hashpw(password, salt);

		try {
			result = userManager.createUser(username, firstName, lastName,
					email, hashed, salt);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public Collection<UserDTO> dumpUsers() {
		Collection<UserDTO> result;

		Collection<User> users = userManager.dumpUsers();

		result = conversion.convertUsers(users);

		return result;
	}

	@Override
	public String getUserCount() {
		return userManager.getUserCount();
	}

	@Override
	public String checkUser(String email, String password) {

		User user = userManager.getUserByEmail(email);

		if (BCrypt.checkpw(password, user.getPassword()))
			return user.getId() + "";

		return "-1";
	}
}
