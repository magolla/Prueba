package com.tdil.d2d.dbinit;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.service.ContactService;
import com.tdil.d2d.service.SpecialtyService;


public class DBInit {

	private static final Logger log = LoggerFactory.getLogger(DBInit.class);
	
	public static void main(String[] args) throws DAOException {
		initCategories(null);
	}
	
	public static void initCategories(SpecialtyService specialtyService) throws DAOException {
		// delete previous data
		//service.removeAllHGTVHomeColors();
		
		List<List<String>> result = new ArrayList<List<String>>();
		String csvFileName = "specialties.csv";
		readData(result, csvFileName);
		
		for (List<String> record : result) {
			try {
//				System.out.println(record);
				String occupation = record.get(0);
				String specialty = record.get(1);
//				String tasks = record.get(2);
				for (int i = 2; i < record.size(); i ++) {
					System.out.println(occupation + "-" + specialty + "-" + record.get(i));
					
					specialtyService.add(occupation, specialty, record.get(i));
				}
				
//				xxx
			} catch (Exception e) {
				System.out.println(record);
				//System.out.println(e);
				System.out.println(e.getCause());
			}
		}
	}

	private static void readData(List<List<String>> result, String csvFileName) {
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		InputStream inputStream;
		try {
			inputStream = DBInit.class.getResourceAsStream(csvFileName);
			br = new BufferedReader(new InputStreamReader(inputStream));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] country = line.split(cvsSplitBy);

				result.add(Arrays.asList(country));
			}
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(),e);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					log.error(e.getMessage(),e);
				}
			}
		}
	}

	public static void initContactMotives(ContactService contactService) throws DAOException {
		List<List<String>> result = new ArrayList<List<String>>();
		String csvFileName = "contactMotives.csv";
		readData(result, csvFileName);
		
		for (List<String> record : result) {
			try {
//				System.out.println(record);
				String contactMotive = record.get(0);
				contactService.addContactMotive(contactMotive);
//				xxx
			} catch (Exception e) {
				System.out.println(record);
				//System.out.println(e);
				System.out.println(e.getCause());
			}
		}
	}

}
