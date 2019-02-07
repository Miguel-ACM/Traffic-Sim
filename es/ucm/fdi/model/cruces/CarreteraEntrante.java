package es.ucm.fdi.model.cruces;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.carreteras.Carretera;
import es.ucm.fdi.model.vehiculos.Vehiculo;

public class CarreteraEntrante {
	protected Carretera carretera;
	protected List<Vehiculo> colaVehiculos;
	protected boolean semaforo; //0 rojo, 1 verde
	//private int unidadesDeTiempoUsadas;
	//private int intervaloDeTiempo;
	
	public CarreteraEntrante(Carretera carretera)
	{
		this.carretera = carretera;
		semaforo = false;
		colaVehiculos = new ArrayList<>();
	}
	
	void ponSemaforo(boolean color)
	{
		semaforo = color;
	}
	
	protected void avanzaPrimerVehiculo() throws ErrorDeSimulacion
	{
		if (colaVehiculos.size() > 0)
		{
			colaVehiculos.get(0).moverASiguienteCarretera();
			colaVehiculos.remove(0);
		}
	}
	
	public String getId()
	{
		return carretera.getId();
	}

	public void addVehiculo(Vehiculo v)
	{
		colaVehiculos.add(v);
	}
	
	protected String escribeSemaforo()
	{
		return this.getSemaforo();
	}
	
	protected String escribeColaVehiculos()
	{
		String ret = "[";
		for(int j=0;j< colaVehiculos.size() ; j++ )
		{
			if (j != 0) ret += ",";
			ret += colaVehiculos.get(j).getId();
			
		}

		ret += "]" ;
		return ret;
	}
	@Override
	public String toString()
	{
		String ret = "";
		ret += "(";
		ret += this.getId();
		ret += ",";
		ret += this.escribeSemaforo();
		ret += ",";
		ret += this.escribeColaVehiculos();
		ret += ")";
		return ret;
		
	}

	public String getSemaforo() { return semaforo ? "green" : "red"; }
	
	protected int size() {	return colaVehiculos.size(); }

	public Carretera getCarretera() {
		return carretera;
	}

	public boolean tieneSemaforoVerde() {
		return semaforo;
	}

	
}
