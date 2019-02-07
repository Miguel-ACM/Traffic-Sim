package es.ucm.fdi.model.cruces;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.carreteras.Carretera;

public class Cruce extends CruceGenerico<CarreteraEntrante>{
	public Cruce(String id) {
		super(id);
	}

	protected void actualizaSemaforos() throws ErrorDeSimulacion
	{
		if(indiceSemaforoVerde != -1)
		{
			carreterasEntrantes.get(indiceSemaforoVerde).avanzaPrimerVehiculo();
			carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(false);
			indiceSemaforoVerde = (indiceSemaforoVerde + 1) % carreterasEntrantes.size();
			carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(true);
		}
		else
		{
			indiceSemaforoVerde++;
			carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(true);
			carreterasEntrantes.get(indiceSemaforoVerde).avanzaPrimerVehiculo();
		}
	}
	//Pone el semaforo de la carretera actual a rojo, y busca la siguiente carretera entrante para ponerlo a verde.

	@Override
	protected CarreteraEntrante creaCarreteraEntrante(Carretera carretera) {
		return new CarreteraEntrante(carretera);
	}
	
}
