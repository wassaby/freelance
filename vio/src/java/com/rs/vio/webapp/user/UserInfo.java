package com.rs.vio.webapp.user;

public class UserInfo {
	
	private String lname;
	private String fname;
	private String login;
	private String password;
	private String account_type;
	private long id;
	private String phone_identifier;
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPhone_identifier() {
		return phone_identifier;
	}
	public void setPhone_identifier(String phone_identifier) {
		this.phone_identifier = phone_identifier;
	}
	
}
