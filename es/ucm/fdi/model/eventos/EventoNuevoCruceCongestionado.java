package es.ucm.fdi.model.eventos;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.cruces.CruceCongestionado;

public class EventoNuevoCruceCongestionado extends EventoNuevoCruce{

	public EventoNuevoCruceCongestionado(int time, String id) {
		super(time, id);
	}

	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion{
		CruceCongestionado cru = new CruceCongestionado(id);
		mapa.addCruce(id,cru);
	}
	
	@Override
	public String toString() {
		return "New most-crowded (" + this.id + ")";
	}
	
}
