package com.tdil.d2d.exceptions;

public class ServiceException extends Exception {
	
    private static final long serialVersionUID = 1L;
    private int error;
	private String descripcion;
	
	@Deprecated
	public ServiceException() {
		super();
	}
	
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	@Deprecated
	public ServiceException(String message,int error, String descripcion) {
		super(message);
		this.setError(error);
		this.setDescripcion(descripcion);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
