package com.tdil.d2d.bo.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class AdminLogsController {

	@RequestMapping(value = {"/logs"} , method = RequestMethod.GET)
	public ModelAndView homePage() {

		ModelAndView model = new ModelAndView();
		model.setViewName("admin/logs");

		return model;

	}

	@RequestMapping(value = "/downloadLog", method = RequestMethod.GET)
	@Produces("text/plain")
	public void getFile(HttpServletResponse response) {
		try {

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
			Date date = new Date();
			String formatedDate = dateFormat.format(date);

			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment;filename=D2DLog-" + formatedDate + ".txt"); 
			String path = System.getProperty( "catalina.base" );

			//path = path + "/logs/catalina.out";
			path = path + "/bin/target/server.log";

			System.out.println("El path relativo es: " + path);
			FileInputStream is = new FileInputStream(path);
			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
		} catch (IOException ex) {
			//	      log.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
			throw new RuntimeException("IOError writing file to output stream");
		}

	}
}

