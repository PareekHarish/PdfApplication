package com.webpdf.services;

import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.webpdf.model.WebpdfModel;
@Service
public class WebpdfService {

	public void insertId(WebpdfModel u) throws SQLException {
			System.out.println("service");
	}

}

