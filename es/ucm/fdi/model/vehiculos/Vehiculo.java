package es.ucm.fdi.model.vehiculos;

import java.util.List;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.ObjetoSimulacion;
import es.ucm.fdi.model.carreteras.Carretera;
import es.ucm.fdi.model.cruces.CruceGenerico;

public class Vehiculo extends ObjetoSimulacion{
	protected Carretera carretera;
	protected int velocidadMaxima;
	protected int velocidadActual;
	protected int kilometraje;
	protected int localizacion;
	protected int tiempoAveria;
	protected List<CruceGenerico<?>> itinerario;
	protected boolean estaEnCruce; 
	protected boolean haLlegado;
	
	public Vehiculo(String id, int velocidadMaxima, List<CruceGenerico<?>> iti) throws ErrorDeSimulacion
	{
		super(id);
		if (iti.size() < 2)
			throw new ErrorDeSimulacion("El itinerario del coche "+ id + " es inferior a 2."); 
		if (velocidadMaxima < 0)
			throw new ErrorDeSimulacion("La velocidad maxima del coche " + id + "es inferior a 0.");
		this.velocidadMaxima = velocidadMaxima;
		this.itinerario = iti;
		localizacion = 0;
		kilometraje = 0;
		tiempoAveria = 0;
		haLlegado = false;
	}
	
	public int getLocalizacion()
	{
		return localizacion;
	}
	
	public String getLocalizacionString()
	{
		return haLlegado ? "arrived" : new Integer(localizacion).toString();
	}
	
	public int getTiempoDeInfraccion()
	{
		return tiempoAveria;
	}
	
	public void setVelocidadActual(int velocidad)
	{
		if (velocidad < 0 || tiempoAveria > 0 || estaEnCruce)
			velocidadActual = 0;
		else if (velocidad > velocidadMaxima)
			velocidadActual = velocidadMaxima;
		else velocidadActual = velocidad;
	}
	
	public void setTiempoAveria(Integer duracionAveria)
	{
		/*if (duracionAveria < 0)
			throw new ErrorDeSimulacion("La duracion de la averia es negativa."); //Sustituir
		*/
		if (carretera != null) 
			tiempoAveria += duracionAveria; 			
	}
	

	@Override
	public String getNombreSeccion() {
		return "vehicle_report";
	}

	@Override
	public void completaDetallesSeccion(IniSection is) {
		is.setValue("speed",this.velocidadActual);
		is.setValue("kilometrage",this.kilometraje);
		is.setValue("faulty",this.tiempoAveria);
		is.setValue("location", this.haLlegado ? "arrived" : 
			"(" + this.carretera + "," + this.getLocalizacion() + ")");
	}


	@Override
	public void avanza() {
		if (tiempoAveria > 0)
			tiempoAveria--;
		else if (!estaEnCruce)
		{
			localizacion += velocidadActual;
			kilometraje += velocidadActual;
			if (localizacion >= carretera.getLength())
			{
				kilometraje -= localizacion - carretera.getLength();
				localizacion = carretera.getLength();
				carretera.entraVehiculoAlCruce(this);
				velocidadActual = 0;
				//indicar a la carretera que el vehiculo entra en el cruce
				this.estaEnCruce = true;
			}
		}
	}

	public void moverASiguienteCarretera() throws ErrorDeSimulacion
	{
		if (carretera == null && itinerario.size() != 0) //Se puede hacer esto porque el constructor asegura que el itinerario tiene al menos 2.
		{
			carretera = itinerario.get(0).carreteraHaciaCruce(itinerario.get(1));
			itinerario.remove(0);
			if (carretera == null)
				throw new ErrorDeSimulacion("El vehiculo " + id + " no encuentra ninguna carretera"
						+ "para alcanzar el cruce " + itinerario.get(0).getId());
			localizacion = 0;
			carretera.entraVehiculo(this);
		}
		else if (carretera != null)
		{
			velocidadActual = 0;
			localizacion = 0;
			carretera.saleVehiculo(this);
			CruceGenerico<?> cru = itinerario.get(0); 
			itinerario.remove(0);

			//Si es igual al ultimo entonces...
			if (itinerario.size() == 0)
			{
				haLlegado = true;
				carretera = null;
				estaEnCruce = true;
			}
			else
			{
				carretera = cru.carreteraHaciaCruce(itinerario.get(0));
				if (carretera == null)
				{
					throw new ErrorDeSimulacion("El vehiculo " + id + " no encuentra ninguna carretera"
							+ "para alcanzar el cruce " + itinerario.get(0).getId());
				}else
					carretera.entraVehiculo(this);

				estaEnCruce = false;
			}



		}
		//		// Si la carretera no es null, sacar el vehículo de la carretera.
		//		  // Si hemos llegado al último cruce del itinerario, entonces:
		//		      1. Se marca que el vehículo ha llegado (this.haLlegado = true).
		//		      2. Se pone su carretera a null.
		//		      3. Se pone su “velocidadActual” y “localizacion” a 0.
		//		  // y se marca que el vehículo está en un cruce (this.estaEnCruce = true).
		//		  // En otro caso:
		//		      1. Se calcula la siguiente carretera a la que tiene que ir.
		//		      2. Si dicha carretera no existe, se lanza excepción.
		//		      3. En otro caso, se introduce el vehículo en la carretera.
		//		      4. Se inicializa su localización.

	}

	public Carretera getCarretera() {
		return this.carretera;
	}

	public int getVelocidad() {
		return velocidadActual;
		
	}

	public int getKilometrage() {
		return this.kilometraje;
	}

	public List<CruceGenerico<?>> getItinerario() {
		return this.itinerario;
	}

}
