package com.tdil.d2d.dao;

import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.Payment;

public interface PaymentDAO {
	
	public Payment getById(Class<Payment> aClass, long id) throws DAOException;

	public void save(Payment ActivityLog) throws DAOException;

}
