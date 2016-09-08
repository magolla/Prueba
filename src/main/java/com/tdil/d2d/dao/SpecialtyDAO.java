package com.tdil.d2d.dao;

import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.Specialty;

public interface SpecialtyDAO {
	
	public Specialty getById(Class<Specialty> aClass, long id) throws DAOException;

	public void save(Specialty specialty) throws DAOException;

}
