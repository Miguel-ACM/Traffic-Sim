package es.ucm.fdi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.carreteras.Carretera;
import es.ucm.fdi.model.cruces.CruceGenerico;
import es.ucm.fdi.model.vehiculos.Vehiculo;

public class MapaCarreteras {
	private List<Carretera>	carreteras;
	private List<CruceGenerico<?>> cruces;
	private List<Vehiculo> vehiculos;
	// estructuras para agilizar la búsqueda (id,valor)
	private Map<String, Carretera> mapaDeCarreteras;
	private Map<String, CruceGenerico<?>> mapaDeCruces;
	private Map<String, Vehiculo> mapaDeVehiculos;

	public MapaCarreteras() {
		carreteras = new ArrayList<>();
		cruces = new ArrayList<>();
		vehiculos = new ArrayList<>();
		mapaDeCarreteras = new HashMap<>();
		mapaDeCruces = new HashMap<>();
		mapaDeVehiculos = new HashMap<>();
	}

	public void addCruce(String idCruce, CruceGenerico<?> cruce) throws ErrorDeSimulacion{
		if (!mapaDeCruces.containsKey(idCruce))
		{
			cruces.add(cruce);
			mapaDeCruces.put(idCruce,cruce);
		}
		else 
			throw new ErrorDeSimulacion("El cruce " + idCruce + " ya esta incluido.");
		// comprueba que “idCruce” no existe en el mapa.
		// Si no existe, lo añade a “cruces” y a “mapaDeCruces”.
		// Si existe lanza una excepción.
	}

	public void addVehiculo(String idVehiculo, Vehiculo vehiculo) throws ErrorDeSimulacion{
		if (!mapaDeVehiculos.containsKey(idVehiculo))
		{
			try
			{
				vehiculo.moverASiguienteCarretera();
				vehiculos.add(vehiculo);
				mapaDeVehiculos.put(idVehiculo,vehiculo);
			}
			catch (ErrorDeSimulacion e)
			{
				throw new ErrorDeSimulacion("El vehiculo "+ vehiculo.getId() + "no pudo introducirse en la carretera.");
			}
			
		}
		else 
			throw new ErrorDeSimulacion("El vehiculo " + idVehiculo + " ya esta incluido.");
		// comprueba que “idVehiculo” no existe en el mapa.
		// Si no existe, lo añade a “vehiculos” y a “mapaDeVehiculos”,
		// y posteriormente solicita al vehiculo que se mueva a la siguiente
		// carretera de su itinerario (moverASiguienteCarretera).
		// Si existe lanza una excepción.
	}

	public void addCarretera(String idCarretera,
			CruceGenerico<?> origen,
			Carretera carretera,
			CruceGenerico<?> destino) throws ErrorDeSimulacion
	{
		if (!mapaDeCarreteras.containsKey(idCarretera))
		{
			carreteras.add(carretera);
			mapaDeCarreteras.put(idCarretera,carretera);
			origen.addCarreteraSalienteAlCruce(destino, carretera);
			destino.addCarreteraEntranteAlCruce(idCarretera, carretera);
		}
		else 
			throw new ErrorDeSimulacion("La carretera " + idCarretera + "ya esta incluida.");
		// comprueba que “idCarretera” no existe en el mapa.
		// Si no existe, lo añade a “carreteras” y a “mapaDeCarreteras”,
		// y posteriormente actualiza los cruces origen y destino como sigue:
		// - Añade al cruce origen la carretera, como “carretera saliente”
		// - Añade al crude destino la carretera, como “carretera entrante”
		// Si existe lanza una excepción.
	}

	public String generateReport(int time) {
		String report = "";
		
		for (CruceGenerico<?> c : cruces)
			report += c.generaInforme(time);
		
		for (Carretera c : carreteras)
			report += c.generaInforme(time);

		for (Vehiculo v : vehiculos)
			report += v.generaInforme(time);
		
		return report;
	}

	public void actualizar() throws ErrorDeSimulacion
	{
		for (Carretera ca : carreteras)
			ca.avanza();
		for (CruceGenerico<?> c : cruces)
			c.avanza();
	}

	public CruceGenerico<?> getCruce(String id) throws ErrorDeSimulacion{
		
		CruceGenerico<?> c = mapaDeCruces.get(id);
		if (c != null)
			return c;
		throw new ErrorDeSimulacion("El cruce " + id + " no se ha encontrado.");
		// devuelve el cruce con ese “id” utilizando el mapaDeCruces.
		// sino existe el cruce lanza excepción.
	}

	public Vehiculo getVehiculo(String id) throws ErrorDeSimulacion {
		Vehiculo v = mapaDeVehiculos.get(id);
		if (v != null)
			return v;
		throw new ErrorDeSimulacion("El vehiculo " + id + " no se ha encontrado.");
		// devuelve el vehículo con ese “id” utilizando el mapaDeVehiculos.
		// sino existe el vehículo lanza excepción.
	}

	public Carretera getCarretera(String id) throws ErrorDeSimulacion {
		Carretera c = mapaDeCarreteras.get(id);
		if (c != null)
			return c;
		throw new ErrorDeSimulacion("La carretera " + id + " no se ha encontrado.");
		// devuelve la carretera con ese “id” utilizando el mapaDeCarreteras.
		// sino existe la carretra lanza excepción.
	}
	
	public List<CruceGenerico<?>> getCruces()
	{
		return cruces; //Crear copia?
	}
	
	public List<Carretera> getCarreteras()
	{
		return carreteras;
	}

	public List<Vehiculo> getVehiculos() {
		return vehiculos;
	}

	public void reinicia() {
		carreteras.removeAll(carreteras);
		vehiculos.removeAll(vehiculos);
		cruces.removeAll(cruces);
		mapaDeCruces.clear();
		mapaDeVehiculos.clear();
		mapaDeCarreteras.clear();
		
	}
	
}
