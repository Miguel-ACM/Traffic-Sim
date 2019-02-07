package es.ucm.fdi.model.eventos;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.cruces.Cruce;

public class EventoNuevoCruce extends Evento{
	protected String id;
	public EventoNuevoCruce(int time, String id) {
		super(time);
		this.id = id;
	}
	
	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion{
		Cruce cru = new Cruce(id);
		mapa.addCruce(id,cru);
	}

	@Override
	public String toString() {
		return "New junction (" + this.id + ")";
	}

}
