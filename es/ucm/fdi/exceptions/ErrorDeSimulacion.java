package es.ucm.fdi.exceptions;

public class ErrorDeSimulacion extends Exception {

	private String error;
	
	public ErrorDeSimulacion(String mensaje)
	{
		super(mensaje);
		this.error = mensaje;
	}
	
	@Override 
	public String toString()
	{
		return error;
	}

}
