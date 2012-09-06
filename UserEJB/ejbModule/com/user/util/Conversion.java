package com.user.util;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.user.dto.UserDTO;
import com.user.model.User;

/**
 * Session Bean implementation class Conversion
 */
@Stateless
@LocalBean
public class Conversion {

	public Conversion() {
	}

	public UserDTO fromEntity(User user) {

		UserDTO result = new UserDTO();

		result.setFirstName(user.getFirstName());
		result.setLastName(user.getLastName());
		result.setEmail(user.getEmail());

		return result;
	}

	public Collection<UserDTO> convertUsers(Collection<User> users) {
		Collection<UserDTO> result = new ArrayList<UserDTO>();

		UserDTO userDTO;
		for (User user : users) {
			userDTO = this.fromEntity(user);
			result.add(userDTO);
		}

		return result;
	}

}
