package es.ucm.fdi.model.cruces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.ObjetoSimulacion;
import es.ucm.fdi.model.carreteras.Carretera;
import es.ucm.fdi.model.vehiculos.Vehiculo;

public abstract class CruceGenerico<T extends CarreteraEntrante> extends ObjetoSimulacion {

protected int indiceSemaforoVerde;
	
	protected List<T> carreterasEntrantes;
	
	// para optimizar las busquedas de las carreteras entrantes.
	// (IdCarretera, CarreteraEntrante)
	protected Map<String, T> mapaCarreterasEntrantes;
	protected Map<CruceGenerico<?>, Carretera> carreterasSalientes;
	
	public CruceGenerico(String id) 
	{
		super(id);
		carreterasEntrantes = new ArrayList<>();
		indiceSemaforoVerde = -1;
		mapaCarreterasEntrantes = new HashMap<>();
		carreterasSalientes = new HashMap<>();
	}
	
	/**
	 * 
	 * @param cruce
	 * @return null if it doesn't exist, the road otherwise
	 */
	public Carretera carreteraHaciaCruce(CruceGenerico<?> cruce)
	{
		return carreterasSalientes.get(cruce); 
		//devuelve la carretera que llega a ese cruce desde "this"
	}
	
	
	abstract protected T creaCarreteraEntrante(Carretera carretera);
	
	
	public void addCarreteraEntranteAlCruce(String idCarretera, Carretera carretera)
	{
		T ri = creaCarreteraEntrante(carretera);
		//CarreteraEntrante c = new CarreteraEntrante(carretera);
		carreterasEntrantes.add(ri);
		mapaCarreterasEntrantes.put(ri.getId(), ri);
		// añada una carretera entrante al mapacarreteraentrante y a las carreterasEntrantes
	}
	
	public void addCarreteraSalienteAlCruce(CruceGenerico<?> destino, Carretera carretera) //Añadido el primer argumento, creará problemas
	{
		//carreterasSalientes.put(carretera.getDestino(),carretera); //era asi
		carreterasSalientes.put(destino, carretera);
		//añade una carretera saliente
	}
	
	public void entraVehiculoAlCruce(String idCarretera, Vehiculo vehiculo)
	{
		CarreteraEntrante c = mapaCarreterasEntrantes.get(idCarretera);
		if (c != null)
			c.addVehiculo(vehiculo);
	}
	
	abstract protected void actualizaSemaforos() throws ErrorDeSimulacion;
	
	@Override
	public void avanza() throws ErrorDeSimulacion
	{
		if (carreterasEntrantes.size() != 0)
		{
			actualizaSemaforos();
		}
	}
	
	@Override
	protected String getNombreSeccion()
	{
		return "junction_report";
	}
	
	@Override
	public void completaDetallesSeccion(IniSection is)
	{
		String carreteras = "";
		for(int i=0; i < this.carreterasEntrantes.size(); i++){
			if (i > 0) carreteras+= ",";
			carreteras += carreterasEntrantes.get(i).toString();
		}
	    
	    is.setValue("queues",carreteras);
	}

	public List<T> getCarreterasEntrantes() {
		return carreterasEntrantes;
	}

	public String getCarreterasRojas()
	{
		String ret = "";
		for (int i = 0; i < carreterasEntrantes.size();i++)
		{
			if (i != indiceSemaforoVerde)
			{
				if (i != 0 && !(i == 1 && indiceSemaforoVerde == 0)) ret += ",";
				ret += carreterasEntrantes.get(i).toString();
			}
		}
		return ret;
	}
	
	public String getCarreteraVerde() {
		return indiceSemaforoVerde < 0 ? "" : carreterasEntrantes.get(indiceSemaforoVerde).toString();
	}

	
}
