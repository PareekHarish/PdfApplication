package com.webpdf.model;

import org.springframework.web.multipart.MultipartFile;

public class MultiPartModel {
	private MultipartFile[] userfiles;
	private String email;
	private String name;
	private String pageno;
	private String userpass;
	private String adminpass;
	String[] data;
	public String[] getData() {
		return data;
	}
	public void setData(String[] data) {
		this.data = data;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAdminpass() {
		return adminpass;
	}
	public void setAdminpass(String adminpass) {
		this.adminpass = adminpass;
	}
	public String getUserpass() {
		return userpass;
	}
	public void setUserpass(String userpass) {
		this.userpass = userpass;
	}
	public String getPageno() {
		return pageno;
	}
	public void setPageno(String pageno) {
		this.pageno = pageno;
	}
	public MultipartFile[] getUserfiles() {
		return userfiles;
	}
	public void setUserfiles(MultipartFile[] userfiles) {
		this.userfiles = userfiles;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
