package com.tdil.d2d.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.controller.api.dto.GeoLevelDTO;
import com.tdil.d2d.dao.GeoDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.Geo2;
import com.tdil.d2d.persistence.Geo3;
import com.tdil.d2d.persistence.Geo4;
import com.tdil.d2d.service.GeoService;

@Transactional
@Service
public class GeoServiceImpl implements GeoService { 
	
	@Autowired
	private GeoDAO geoDAO;
	
	public List<GeoLevelDTO> search(String text) {
		return null;
	}

	@Override
	public void initDbWithTestData() throws ServiceException {
		try {
			Geo2 bsas = new Geo2();
			bsas.setName("Buenos Aires");
			geoDAO.save(bsas);
			
			Geo3 lp = new Geo3();
			lp.setName("La Plata");
			lp.setGeo2(bsas);
			geoDAO.save(lp);
			
			Geo4 sl = new Geo4();
			sl.setName("Altos de San Lorenzo");
			sl.setGeo3(lp);
			geoDAO.save(sl);
			
			Geo2 caba = new Geo2();
			caba.setName("Ciudad de Buenos AIres");
			geoDAO.save(caba);
			
			Geo3 reco = new Geo3();
			reco.setName("Recoleta");
			reco.setGeo2(caba);
			geoDAO.save(reco);
			
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
