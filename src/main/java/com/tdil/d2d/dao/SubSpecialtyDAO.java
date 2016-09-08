package com.tdil.d2d.dao;

import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.SubSpecialty;

public interface SubSpecialtyDAO {
	
	public SubSpecialty getById(Class<SubSpecialty> aClass, long id) throws DAOException;

	public void save(SubSpecialty subSpecialty) throws DAOException;

}
