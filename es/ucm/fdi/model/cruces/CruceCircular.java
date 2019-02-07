package es.ucm.fdi.model.cruces;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.carreteras.Carretera;

public class CruceCircular extends CruceGenerico<CarreteraEntranteConIntervalo>{

	int min_time_slice;
	int max_time_slice;
	
	public CruceCircular(String id, int max_time_slice, int min_time_slice) {
		super(id);
		this.max_time_slice = max_time_slice;
		this.min_time_slice = min_time_slice;
	}

	@Override
	protected void actualizaSemaforos() throws ErrorDeSimulacion
	{
		if (indiceSemaforoVerde != -1)
		{	
			carreterasEntrantes.get(indiceSemaforoVerde).avanzaPrimerVehiculo();
			CarreteraEntranteConIntervalo ri = carreterasEntrantes.get(indiceSemaforoVerde);
			if (ri.tiempoConsumido())
			{
				ri.ponSemaforo(false);
				indiceSemaforoVerde = (indiceSemaforoVerde + 1) % carreterasEntrantes.size();
				
				CarreteraEntranteConIntervalo riNueva = carreterasEntrantes.get(indiceSemaforoVerde);
				
				if (ri.usoCompleto())
				{
					ri.setIntervaloDeTiempo(Math.min(ri.getIntervaloDeTiempo() + 1, max_time_slice));
				}
				else if (!ri.usada())
				{
					ri.setIntervaloDeTiempo(Math.max(ri.getIntervaloDeTiempo() - 1, min_time_slice));
				}
				riNueva.resetUnidadesDeTiempoUsadas(); //Se hace esto para poner el tiempo usado a 0 de nuevo.
				riNueva.ponSemaforo(true);
				
			}
		}
		
		else
		{
			/*for (CarreteraEntranteConIntervalo ce : carreterasEntrantes)
			{
				ce.setIntervaloDeTiempo(max_time_slice);
			}*/
			indiceSemaforoVerde++;
			carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(true);

			
		}
		/*
		- Si no hay carretera con semáforo en verde (
				indiceSemaforoVerde == -1
				) entonces se 
				selecciona la primera carretera entrante (la de la posición 0) y se pone su 
				semáforo en verde.
				- Si hay carretera entrante "
				ri
				" con su semáforo en verde, (
				indiceSemaforoVerde != 
				-1
				) entonces:
				1. Si ha consumido su intervalo de tiempo en verde ("
				ri.tiempoConsumido()
				"):
				1.1. Se pone el semáforo de 
				"ri"
				 a rojo.
				1.2. Si ha sido usada en todos los pasos (
				“ri.usoCompleto()”
				), se fija 
				el intervalo de tiempo a ... Sino, si no ha sido usada 
				(
				“!ri.usada()”
				) se fija el intervalo de tiempo a ... 
				1.3. Se coge como nueva carretera con semáforo a verde la inmediatamente
				Posterior a 
				“ri”
			*/
	}

	@Override
	protected CarreteraEntranteConIntervalo creaCarreteraEntrante(Carretera carretera) {
		CarreteraEntranteConIntervalo c = new CarreteraEntranteConIntervalo(carretera);
		c.setIntervaloDeTiempo(max_time_slice);
		return c;
	}
	
	@Override
	public void completaDetallesSeccion(IniSection is)
	{
		super.completaDetallesSeccion(is);
	    is.setValue("type", "rr");

	}

}
