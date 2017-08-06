package com.tdil.d2d.bo.dto;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class JobOfferReportDTO {

	private Integer startMonth;
	private Integer startYear;

	private Integer endMonth;
	private Integer endYear;
	
	List<JobOfferReportItemDTO> list;
	
	public void setStartMonth(Integer startMonth) {
		this.startMonth = startMonth;
	}
	
	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}
	
	public void setEndMonth(Integer endMonth) {
		this.endMonth = endMonth;
	}
	
	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}
	
	public List<JobOfferReportItemDTO> getList() {
		return list;
	}
	
	public void setList(List<JobOfferReportItemDTO> list) {
		this.list = list;
	}
	
	public Integer getQuantityOfMonths() {
		int m1 = startYear * 12 + startMonth;
	    int m2 = endYear * 12 + endMonth;
	    int quantity = m2 - m1 + 1;
		return quantity;
	}
	
	public List<String> getTextMonths() {
		DateFormatSymbols sym = DateFormatSymbols.getInstance(new Locale("es","ar"));
        sym.setMonths(new String[]{"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre" });
        sym.setShortMonths(new String[]{"Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic" });
        SimpleDateFormat date = new SimpleDateFormat("MMM-yyyy", sym);
		
		List<String> months = new ArrayList<String>();
		
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DAY_OF_MONTH, 1);
		now.set(Calendar.MONTH, startMonth - 2);
		now.set(Calendar.YEAR, startYear);
		
		for (int i=0; i<this.getQuantityOfMonths(); i++) {
			now.add(Calendar.MONTH, 1);
			
			String dateString = date.format(now.getTime());
			months.add(dateString);
		}
		
		return months;
	}
}
