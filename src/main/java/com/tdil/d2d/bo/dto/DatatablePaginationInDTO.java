package com.tdil.d2d.bo.dto;

import javax.servlet.http.HttpServletRequest;

public class DatatablePaginationInDTO {
	
	
	private String length;
	private String start;
	private String draw;
	private String search;
	
	public DatatablePaginationInDTO(HttpServletRequest request) {
    	this.length = request.getParameter("length");
    	this.start = request.getParameter("start");
    	this.draw = request.getParameter("draw");
    	this.search = request.getParameter("search[value]");
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getDraw() {
		return draw;
	}

	public void setDraw(String draw) {
		this.draw = draw;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

}
