package com.webpdf.dao;


import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.webpdf.model.WebpdfModel;

@Repository
public class WebpdfDao {
	public void insertId(WebpdfModel u) throws SQLException
	{
		System.out.println("dao class");
	}

}
