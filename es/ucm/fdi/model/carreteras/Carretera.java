package es.ucm.fdi.model.carreteras;

import java.util.Comparator;
import java.util.List;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.ObjetoSimulacion;
import es.ucm.fdi.model.cruces.CruceGenerico;
import es.ucm.fdi.model.utils.SortedArrayList;
import es.ucm.fdi.model.vehiculos.Vehiculo;

public class Carretera extends ObjetoSimulacion{
	protected int longitud;
	protected int velocidadMaxima;
	protected CruceGenerico<?> cruceOrigen;
	protected CruceGenerico<?> cruceDestino;
	protected List<Vehiculo> vehiculos;
	
	protected Comparator<Vehiculo> comparadorVehiculo;
	
	public Carretera(String id, int lenght, int maxSpeed, CruceGenerico<?> src, CruceGenerico<?> dest)
	{
		super(id);
		this.longitud = lenght;
		this.velocidadMaxima = maxSpeed;
		this.id = id;
		cruceOrigen = src;
		cruceDestino = dest;
		
		comparadorVehiculo = new Comparator<Vehiculo>()
				{
					public int compare(Vehiculo v1, Vehiculo v2)
					{
						if (v1.getLocalizacion() > v2.getLocalizacion()) return -1;
						else if (v1.getLocalizacion() < v2.getLocalizacion()) return 1;
						return 0;
					}
				};
		vehiculos = new SortedArrayList<>(comparadorVehiculo);
	}
	
	public void avanza()
	{
		int velocidadBase = calculaVelocidadBase();
		int obstaculos = 0;
		int factorReduccion = calculaFactorReduccion(obstaculos);
		for (Vehiculo i : vehiculos) //Se asume que a la izquierda estan los que antes salen
		{
			if (i.getTiempoDeInfraccion() > 0)
			{
				obstaculos++;
				factorReduccion = calculaFactorReduccion(obstaculos);
			}
			i.setVelocidadActual(velocidadBase/factorReduccion);
			i.avanza();
		}
		vehiculos.sort(comparadorVehiculo);
	}
	
	public void entraVehiculo(Vehiculo vehiculo)
	{
		int i = 0;
		boolean encontrado = false;
		while (i < vehiculos.size() && !encontrado){
			encontrado = (vehiculo.getId().equals(vehiculos.get(i).getId()));
			i++;
		}
		if (!encontrado)
		{
			vehiculos.add(vehiculo);
		}
		//Si el vehiculo no existe, se añade a la lista (de forma ordenada)
		//Si existe nada
	}
	
	public void saleVehiculo(Vehiculo vehiculo)
	{
		vehiculos.remove(vehiculo); //elimina el vehiculo de la lista de vehiculos
	}
	
	public void entraVehiculoAlCruce(Vehiculo v)
	{
		cruceDestino.entraVehiculoAlCruce(id,v);
		// añade el vehiculo al cruce destino de la carretera
	}
	
	protected int calculaVelocidadBase()
	{
		return Math.min(velocidadMaxima, 1 + velocidadMaxima/(Math.max(vehiculos.size(), 1)));
	}
	
	protected int calculaFactorReduccion(int obstaculos)
	{
		return obstaculos == 0 ? 1 : 2;
	}
	
	@Override
	public String getNombreSeccion()
	{
		return "road_report";
	}
	
	@Override
	public void completaDetallesSeccion(IniSection is) {
		String vehicles = "";
		for(int i = 0; i < vehiculos.size(); i++)
		{
			if (i != 0) vehicles += ",";
			vehicles += "(";
			vehicles += vehiculos.get(i).getId();
			vehicles += ",";
			vehicles += vehiculos.get(i).getLocalizacion();
			vehicles += ")";

		}
		is.setValue("state", vehicles);
	}
	
	public int getLength()
	{
		return longitud;
	}
	
	public List<Vehiculo> getVehiculos()
	{
		return this.vehiculos;
	}
	/*public CruceGenerico<?> getDestino()
	{
		return cruceDestino;
	}*/

	public CruceGenerico<?> getCruceOrigen() {
		return cruceOrigen;
	}
	
	public CruceGenerico<?> getCruceDestino() {
		return cruceDestino;
	}

	public int getVelocidadMaxima() {
		return velocidadMaxima;
	}
	
}
