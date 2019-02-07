package es.ucm.fdi.model;

import java.util.HashMap;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.ini.IniSection;

public abstract class ObjetoSimulacion {
	protected String id;
	private HashMap<Integer, String> report;
	
	public ObjetoSimulacion(String id)
	{
		report = new HashMap<>();
		this.id = id;
	}
	
	public String getId()
	{
		return id;
	}
	
	@Override
	public String toString()
	{
		return id; 
	}
	
	public String generaInforme(int tiempo)
	{
		IniSection is = new IniSection(this.getNombreSeccion());
		is.setValue("id", id);
		is.setValue("time", tiempo);
		completaDetallesSeccion(is);
		
		report.put(tiempo, is.toString() + System.lineSeparator());
		return is.toString() + System.lineSeparator();
	}
	
	public String getReport(int tiempo)
	{
		return report.get(new Integer(tiempo));
	}
	
	protected abstract String getNombreSeccion();
	
	public abstract void completaDetallesSeccion(IniSection is);
	
	public abstract void avanza() throws ErrorDeSimulacion;
}
