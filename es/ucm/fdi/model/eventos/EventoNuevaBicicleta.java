package es.ucm.fdi.model.eventos;

import java.util.List;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.cruces.CruceGenerico;
import es.ucm.fdi.model.vehiculos.Bicicleta;
import es.ucm.fdi.model.vehiculos.Vehiculo;

public class EventoNuevaBicicleta extends EventoNuevoVehiculo{

	public EventoNuevaBicicleta(int tiempo, String id, int velocidadMaxima, String[] itinerario) {
		super(tiempo, id, velocidadMaxima, itinerario);
	}

	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion{
		try
		{
			List<CruceGenerico<?>> iti = ParserCarreteras.parseaListaCruces(this.itinerario,mapa);
			Vehiculo veh = new Bicicleta(id, velocidadMaxima, iti);
			mapa.addVehiculo(id,veh);
		}
		
		catch (ErrorDeSimulacion e)
		{
			throw new ErrorDeSimulacion("La bicicleta " + id + " no pudo ser construida al no reconocer uno de los cruces en su itinerario.");
		}
	}
	
	@Override
	public String toString() {
		return "New bike (" + this.id + ")";
	}
	
}
