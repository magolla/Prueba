package com.tdil.d2d.exceptions;

public class DAOException extends Exception {
	
    private static final long serialVersionUID = 1L;
    private int error;
	private String descripcion;
	
	@Deprecated
	public DAOException() {
		super();
	}
	
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	@Deprecated
	public DAOException(String message,int error, String descripcion) {
		super(message);
		this.setError(error);
		this.setDescripcion(descripcion);
	}

	public DAOException(Throwable cause) {
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
