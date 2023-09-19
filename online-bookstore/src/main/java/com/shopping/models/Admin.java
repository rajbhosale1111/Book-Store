package com.shopping.models;
import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class Admin {
	
	@Id
	private String userid;
	private String uname;
	private String pwd;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	
}
