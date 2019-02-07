  package es.ucm.fdi.model.eventos;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.carreteras.Autopista;
import es.ucm.fdi.model.cruces.CruceGenerico;

public class EventoNuevaAutopista  extends EventoNuevaCarretera {
  	int lanes;
  
  	public EventoNuevaAutopista(int tiempo, String id, String origen, String destino, int velocidadMaxima, int longitud, int lanes) {
		super(tiempo, id, origen, destino, velocidadMaxima, longitud);
      this.lanes = lanes ; 
	}
  @Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion
	{
		
	// obten cruce origen y cruce destino utilizando el mapa
	// crea la carretera
		// añade al mapa la carretera
		try 
		{
			CruceGenerico<?> origen = mapa.getCruce(cruceOrigenId);
			CruceGenerico<?> destino = mapa.getCruce(cruceDestinoId);
			mapa.addCarretera(id,origen, new Autopista(this.id, longitud, velocidadMaxima, origen, destino, lanes), destino);
		}
		catch (Exception e)
		{
			throw new ErrorDeSimulacion("La carretera " + id + " no pudo ser generada."); 
		}
	}

  	@Override
  	public String toString() {
		return "New freeway (" + this.id + ")";
	}
  	
}