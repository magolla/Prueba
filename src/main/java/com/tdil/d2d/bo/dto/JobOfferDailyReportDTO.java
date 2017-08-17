package com.tdil.d2d.bo.dto;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class JobOfferDailyReportDTO {

	private Date fromDate;
	private Date toDate;
	List<JobOfferReportItemDTO> list;
	
	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public List<JobOfferReportItemDTO> getList() {
		return list;
	}
	
	public void setList(List<JobOfferReportItemDTO> list) {
		this.list = list;
	}

	public List<String> getTextDays() {
		DateFormatSymbols sym = DateFormatSymbols.getInstance(new Locale("es","ar"));
        sym.setMonths(new String[]{"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre" });
        sym.setShortMonths(new String[]{"Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic" });
        SimpleDateFormat date = new SimpleDateFormat("dd-MMM", sym);
		
		List<String> days = new ArrayList<String>();
		
		Calendar now = Calendar.getInstance();
		now.setTime(fromDate);
		
		while (now.getTime().compareTo(toDate) < 0) {
			now.add(Calendar.DAY_OF_MONTH, 1);
			
			String dateString = date.format(now.getTime());
			days.add(dateString);
		}
		
		return days;
	}
}
