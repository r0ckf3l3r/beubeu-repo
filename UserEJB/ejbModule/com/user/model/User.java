package com.user.model;

import java.io.Serializable;
import javax.persistence.*;

import com.google.api.client.util.Key;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity(name="USERS")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	private String firstName;
	
	private String lastName;
	
	private String userName;

	@Key
	private String email;
	
	private String password;
	
	public User() {
		super();
	}
	
	public User(String name, String userName) {
		super();
		
		this.firstName = name;
		this.userName = userName;
		
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String name) {
		this.firstName = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String name) {
		this.lastName = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		String result = "";

		result += "user:\n";
		result += "\n id: " + this.id;
		result += "\n firstName: " + this.firstName;
		result += "\n lastName: " + this.lastName;
		result += "\n email: " + this.email;
		result += "\n password: " + this.password;
		
		return result;
	}
	
   
}
