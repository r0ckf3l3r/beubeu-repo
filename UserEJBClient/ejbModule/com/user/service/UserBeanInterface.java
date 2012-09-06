package com.user.service;

import java.util.Collection;

import javax.ejb.Remote;

import com.user.dto.UserDTO;

@Remote
public interface UserBeanInterface {

	public UserDTO getUserById(Long id);

	public String createUser(String username, String firstName, String lastName,
			String email, String password);
	
	public Collection<UserDTO> dumpUsers();
	
	public String getUserCount();

	public String checkUser(String email, String password);

}
