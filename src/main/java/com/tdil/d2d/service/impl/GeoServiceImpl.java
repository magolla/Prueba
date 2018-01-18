package com.tdil.d2d.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.bo.dto.GeosDto;
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

	@SuppressWarnings("unchecked")
	public List<GeoLevelDTO> listGeoLevel2() throws ServiceException {
		try {
			return (List<GeoLevelDTO>) toDto2(geoDAO.getListGeo2());
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
		Geo3 geo3 = s.getGeo3();
		result.setName(s.getName() + ", " + geo3.getName() + ", " + geo3.getGeo2().getName());
		return result;
	}

	private GeoLevelDTO toDto(Geo3 s) {
		GeoLevelDTO result = new GeoLevelDTO();
		result.setLevel(3);
		result.setId(s.getId());
		// TODO setear el path hasta el 2
		result.setName(s.getName() + ", " + s.getGeo2().getName());
		return result;
	}

	private GeoLevelDTO toDtoBackend(Geo3 s) {
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
		//		try {
		//			Geo2 bsas = new Geo2();
		//			bsas.setName("Buenos Aires");
		//			geoDAO.save(bsas);
		//			
		//			Geo3 lp = new Geo3();
		//			lp.setName("La Plata");
		//			lp.setGeo2(bsas);
		//			geoDAO.save(lp);
		//			
		//			Geo4 sl = new Geo4();
		//			sl.setName("Altos de San Lorenzo");
		//			sl.setGeo3(lp);
		//			geoDAO.save(sl);
		//			
		//			Geo2 caba = new Geo2();
		//			caba.setName("Ciudad de Buenos AIres");
		//			geoDAO.save(caba);
		//			
		//			Geo3 reco = new Geo3();
		//			reco.setName("Recoleta");
		//			reco.setGeo2(caba);
		//			geoDAO.save(reco);
		//			
		//			
		//		} catch (DAOException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}

	}

	@Override
	public List<GeosDto> getGeoByIndex(String length, String start, String search) throws DAOException {
		List<Geo4> geoList = this.geoDAO.getGeoByIndex(length, start, search);
		return geoList.stream().map(geo -> toDtoGeo(geo)).collect(Collectors.toList());
	}

	private GeosDto toDtoGeo(Geo4 geo) {
		GeosDto geosDto = new GeosDto();
		geosDto.setGeo4Id(geo.getId());
		geosDto.setGeo4Name(geo.getName());
		geosDto.setGeo3Id(geo.getGeo3().getId());
		geosDto.setGeo3Name(geo.getGeo3().getName());
		geosDto.setGeo2Id(geo.getGeo3().getGeo2().getId());
		geosDto.setGeo2Name(geo.getGeo3().getGeo2().getName());
		return geosDto;
	}

	@Override
	public int getGeoCount(String search) throws DAOException {
		return geoDAO.geoCount(search);
	}

	@Override
	public void add(String geo2,String geo3, String geo4) throws DAOException {

		String g2 = geo2.trim();
		Geo2 ge2 = geoDAO.searchGeo2(geo2);
		if (ge2 == null) {
			ge2 = new Geo2();
			ge2.setName(geo2);
			geoDAO.save(ge2);
		}
		String g3 = geo3.trim();
		Geo3 ge3 = geoDAO.searchGeo3(geo3);
		if (ge3 == null) {
			ge3 = new Geo3();
			ge3.setGeo2(ge2);
			ge3.setName(geo3);
			geoDAO.save(ge3);
		}
		String g4 = geo4.trim();
		Geo4 ge4 = geoDAO.searchGeo4(geo4);
		if (ge4 == null) {
			ge4 = new Geo4();
			ge4.setGeo3(ge3);
			ge4.setName(geo4);
			geoDAO.save(ge4);
		}

	}


	@Override
	public void addBackend(String geo2,String geo3, String geo4) throws DAOException {

		String g2 = geo2.trim();
		Geo2 ge2 = geoDAO.searchGeo2(geo2);
		if (ge2 == null) {
			ge2 = new Geo2();
			ge2.setName(geo2);
			geoDAO.save(ge2);
			Geo3 ge3 = new Geo3();
			ge3.setGeo2(ge2);
			ge3.setName(geo3);
			geoDAO.save(ge3);
			Geo4 ge4 = new Geo4();
			ge4.setGeo3(ge3);
			ge4.setName(geo4);
			geoDAO.save(ge4);
		}

	}

	@Override
	public void addGeo3(String geo3ProvinceId, String newRegionName) throws NumberFormatException, DAOException {
		Geo2 geoLevel = (Geo2) geoDAO.getGeoByIdAndLevel(Long.valueOf(geo3ProvinceId), 2);
		if(geoLevel == null) {
			throw new DAOException(new Throwable("Error al obtener provincia"));
		}
		List<Geo3> geo3List = geoDAO.getListGeo3ByGeo2(Long.valueOf(geo3ProvinceId));

		if(geo3List.size() == 1 && geo3List.get(0).getName().equals("")) { 
			List<Geo4> geo4List = geoDAO.getListGeo4ByGeo3(geo3List.get(0).getId());

			if(geo4List.size() == 1 && geo4List.get(0).getName().equals("")) { 
				Geo3 geo3 = geo3List.get(0); 
				geo3.setName(newRegionName); 
				geoDAO.save(geo3);   
			} else {
				Geo3 newGeo3 = new Geo3(); 
				newGeo3.setName(newRegionName); 
				newGeo3.setGeo2(geoLevel);
				geoDAO.save(newGeo3); 

				Geo4 t = new Geo4(); 
				t.setGeo3(newGeo3);
				t.setName(""); 
				geoDAO.save(t); 
			}

		} else { 
			Geo3 newGeo3 = new Geo3(); 
			newGeo3.setName(newRegionName); 
			newGeo3.setGeo2(geoLevel);
			geoDAO.save(newGeo3); 

			Geo4 t = new Geo4(); 
			t.setGeo3(newGeo3);
			t.setName(""); 
			geoDAO.save(t); 
		} 


	}

	@Override
	public void addGeo4(String cityGeo3Id, String cityName) throws NumberFormatException, DAOException {
		List<Geo4> geo4List = geoDAO.getListGeo4ByGeo3(Long.valueOf(cityGeo3Id));

		if(geo4List.size() == 1 && geo4List.get(0).getName().equals("")) {
			Geo4 g4 = geo4List.get(0);
			g4.setName(cityName);
			geoDAO.save(g4);
		} else {
			Geo3 g3 = (Geo3) geoDAO.getGeoByIdAndLevel(Long.valueOf(cityGeo3Id), 3);
			Geo4 geo4 = new Geo4();
			geo4.setName(cityName);
			geo4.setGeo3(g3);
			geoDAO.save(geo4);
		}
	}

	@Override
	public Collection<GeoLevelDTO> listGeoLevel3ByProvince(long provinceId) throws ServiceException {
		try {

			List<Geo3> geoList = geoDAO.getListGeo3ByGeo2(provinceId);

			return geoList.stream().map(s -> toDtoBackend(s)).collect(Collectors.toList());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void editProvince(long id, String name) throws DAOException {
		Geo2 geo2 = (Geo2)geoDAO.getGeoByIdAndLevel(id, 2);
		geo2.setName(name);
		geoDAO.save(geo2);
	}

	@Override
	public void editRegion(long id, String name) throws DAOException {
		Geo3 geo3 = (Geo3)geoDAO.getGeoByIdAndLevel(id, 3);
		geo3.setName(name);
		geoDAO.save(geo3);
	}

	@Override
	public void editCity(long id, String name) throws DAOException {
		Geo4 geo4 = (Geo4)geoDAO.getGeoByIdAndLevel(id, 4);
		geo4.setName(name);
		geoDAO.save(geo4);

	}





}
