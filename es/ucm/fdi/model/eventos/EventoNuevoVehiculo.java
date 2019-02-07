package es.ucm.fdi.model.eventos;

import java.util.List;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.cruces.CruceGenerico;
import es.ucm.fdi.model.vehiculos.Vehiculo;

public class EventoNuevoVehiculo extends Evento{
	protected String id;
	protected Integer velocidadMaxima;
	protected String[] itinerario;

	public EventoNuevoVehiculo(int tiempo, String id, int velocidadMaxima,
			String[] itinerario) 
	{
		super(tiempo);
		this.id = id;
		this.velocidadMaxima = velocidadMaxima;
		this.itinerario = itinerario;
	}
	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion{
		try
		{
			List<CruceGenerico<?>> iti = ParserCarreteras.parseaListaCruces(this.itinerario,mapa);
			Vehiculo veh = new Vehiculo(id, velocidadMaxima, iti);	
			mapa.addVehiculo(id,veh);
		}
		catch (ErrorDeSimulacion e)
		{
			throw new ErrorDeSimulacion("El vehiculo " + id + " no pudo ser construido al no reconocer uno de los cruces en su itinerario.");

		}
	}

	@Override
	public String toString() {
		return "New Vehicle (" + this.id + ")";
	}

}
