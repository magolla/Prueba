package com.tdil.d2d.dao;

import java.util.List;

import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.Note;

public interface ReportsDAO {

	List<Note> getAllJobOffers() throws DAOException; 
}
