package es.ucm.fdi.model.eventos;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.cruces.CruceCircular;

public class EventoNuevoCruceCircular extends EventoNuevoCruce{
	
	int minIntervalo;
	int maxIntervalo;

	public EventoNuevoCruceCircular(int time, String id, int minIntervalo, int maxIntervalo) {
		super(time, id);
		this.minIntervalo = minIntervalo;
		this.maxIntervalo = maxIntervalo;
		
	}

	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion{
		CruceCircular cru = new CruceCircular(id,maxIntervalo,minIntervalo);
		mapa.addCruce(id,cru);
	}
	
	@Override
	public String toString() {
		return "New round-robin (" + this.id + ")";
	}
	
}
