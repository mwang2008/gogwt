package com.gogwt.apps.tracking.data;

import java.util.Date;

import com.gogwt.apps.tracking.formbean.BaseBean;

public class CustomerProfile extends BaseBean {
	private String id;
	private String groupId;
	private String groupName;
	private String firstName;
	private String lastName;
	private String email;
	private String userName;
	private String password;
	private String confirmPassword;
	private Date createDate;
	private boolean isLogin;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

/*	public boolean isLogin() {
		return isLogin;
	}*/

	public boolean getLogin() {
		return isLogin;
	}
	
	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public boolean getIsLogin() {
		return isLogin;
	}
	
	public void setIsLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
	
}
