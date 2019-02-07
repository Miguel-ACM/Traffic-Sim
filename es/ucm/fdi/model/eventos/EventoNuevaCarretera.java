package es.ucm.fdi.model.eventos;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.carreteras.Carretera;
import es.ucm.fdi.model.cruces.CruceGenerico;

public class EventoNuevaCarretera extends Evento {
	protected String id;
	protected Integer velocidadMaxima;
	protected Integer longitud;
	protected String cruceOrigenId;
	protected String cruceDestinoId;
	
	public EventoNuevaCarretera(int tiempo, String id, String origen,
	String destino, int velocidadMaxima, int longitud) {
		super(tiempo);
		this.id = id;
		this.velocidadMaxima = velocidadMaxima;
		this.longitud = longitud;
		this.cruceOrigenId = origen;
		this.cruceDestinoId = destino;
		
	}
	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion{
		
	// obten cruce origen y cruce destino utilizando el mapa
	// crea la carretera
		// añade al mapa la carretera
		try 
		{
			CruceGenerico<?> origen = mapa.getCruce(cruceOrigenId);
			CruceGenerico<?> destino = mapa.getCruce(cruceDestinoId);
			mapa.addCarretera(id,origen, new Carretera(this.id, longitud, velocidadMaxima, origen, destino), destino);
		}
		catch (Exception e)
		{
			throw new ErrorDeSimulacion("La carretera " + id + " no pudo ser generada."); //ADADAD
		}
	}

	@Override
	public String toString() {
		return "New road (" + this.id + ")";
	}
	
}
