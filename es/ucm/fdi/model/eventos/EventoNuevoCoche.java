package es.ucm.fdi.model.eventos;

import java.util.List;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.cruces.CruceGenerico;
import es.ucm.fdi.model.vehiculos.Coche;
import es.ucm.fdi.model.vehiculos.Vehiculo;

public class EventoNuevoCoche extends EventoNuevoVehiculo {

	private int resistenciaKm;
	private double probabilidadDeAveria;
	private int maximaDuracionAveria;
	private long numAleatorio;

	public EventoNuevoCoche(int tiempo, String id, int velocidadMaxima, String[] itinerario,int resistance,double probabilidadAveria,
			int maxFaultDuration,long seed) {
		super(tiempo, id, velocidadMaxima, itinerario);
		resistenciaKm = resistance;
		probabilidadDeAveria = probabilidadAveria;
		maximaDuracionAveria = maxFaultDuration;
		numAleatorio = seed;
	}

	public EventoNuevoCoche(int tiempo, String id, int velocidadMaxima, String[] itinerario,int resistance,double probabilidadAveria,
			int maxFaultDuration) {
		super(tiempo, id, velocidadMaxima, itinerario);
		resistenciaKm = resistance;
		probabilidadDeAveria = probabilidadAveria;
		maximaDuracionAveria = maxFaultDuration;
		numAleatorio = System.currentTimeMillis();
	}

	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion{
		try
		{
			List<CruceGenerico<?>> iti = ParserCarreteras.parseaListaCruces(this.itinerario,mapa);

			Vehiculo veh = new Coche(id, velocidadMaxima, iti, resistenciaKm, probabilidadDeAveria, maximaDuracionAveria, numAleatorio);//mas argumentos
			mapa.addVehiculo(id,veh);
		}
		catch (ErrorDeSimulacion e)
		{
			throw new ErrorDeSimulacion("El coche " + id + " no pudo ser construido al no reconocer uno de los cruces en su itinerario.");

		}
	}
	
	@Override
	public String toString() {
		return "New car (" + this.id + ")";
	}
	
	
}