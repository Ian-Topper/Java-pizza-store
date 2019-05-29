package com.pizzastore.data;

import java.io.Serializable;

public class User implements Serializable {
	
	private int userId;
	private String role;
	private String userName;
	private String password;
	private String name;
	private String mailAddress;
	private String emailAddress;
	private String phoneNumber;
	
	public User() {
		
	}
	
	public User(int userId, String role, String userName, String password, String name, String mailAddress, String emailAddress, String phoneNumber) {
		this.userId = userId;
		this.role = role;
		this.userName = userName;
		this.password = password;
		this.name = name;
		this.mailAddress = mailAddress;
		this.emailAddress = emailAddress;
		this.phoneNumber = phoneNumber;
	}
	
	public User(int userID, String userName, String password){
		this.userId = userID;
		this.userName = userName;
		this.password = password;
	}
	
	public User(String userName, String password){
		this.userName = userName;
		this.password = password;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", name=" + name + ", mailAddress=" + mailAddress
				+ ", emailAddress=" + emailAddress + ", phoneNumber=" + phoneNumber + "]";
	}
	
}
