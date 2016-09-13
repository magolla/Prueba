package com.tdil.d2d.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
	
	public List<GeoLevelDTO> search(String text) throws ServiceException {
		try {
			List<GeoLevelDTO> result = new ArrayList<>();
			result.addAll(toDto4(geoDAO.listGeo4(text)));
			result.addAll(toDto3(geoDAO.listGeo3(text)));
			result.addAll(toDto2(geoDAO.listGeo2(text)));
			return result;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	private Collection<? extends GeoLevelDTO> toDto4(List<Geo4> levels) {
		return levels.stream().map(s -> toDto(s)).collect(Collectors.toList());
	}
	
	private Collection<? extends GeoLevelDTO> toDto3(List<Geo3> levels) {
		return levels.stream().map(s -> toDto(s)).collect(Collectors.toList());
	}
	
	private Collection<? extends GeoLevelDTO> toDto2(List<Geo2> levels) {
		return levels.stream().map(s -> toDto(s)).collect(Collectors.toList());
	}

	private GeoLevelDTO toDto(Geo4 s) {
		GeoLevelDTO result = new GeoLevelDTO();
		result.setLevel(4);
		result.setId(s.getId());
		// TODO setear el path hasta el 2
		result.setName(s.getName());
		return result;
	}
	
	private GeoLevelDTO toDto(Geo3 s) {
		GeoLevelDTO result = new GeoLevelDTO();
		result.setLevel(3);
		result.setId(s.getId());
		// TODO setear el path hasta el 2
		result.setName(s.getName());
		return result;
	}
	
	private GeoLevelDTO toDto(Geo2 s) {
		GeoLevelDTO result = new GeoLevelDTO();
		result.setLevel(2);
		result.setId(s.getId());
		result.setName(s.getName());
		return result;
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
