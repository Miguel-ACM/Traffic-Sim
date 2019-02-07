package es.ucm.fdi.model.cruces;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.carreteras.Carretera;

public class CruceCongestionado extends CruceGenerico<CarreteraEntranteConIntervalo>{

	public CruceCongestionado(String id) {
		super(id);
	}

	@Override
	protected void actualizaSemaforos() throws ErrorDeSimulacion
	{
		if (indiceSemaforoVerde != -1)
		{	
			carreterasEntrantes.get(indiceSemaforoVerde).avanzaPrimerVehiculo();
			if (carreterasEntrantes.get(indiceSemaforoVerde).tiempoConsumido())
			{
				carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(false);
				int indiceAntiguo = indiceSemaforoVerde;
				int i = (indiceSemaforoVerde + 1) % carreterasEntrantes.size();

				//estas 3 lineas siguientes aseguran que se haga bien con solo 1 semaforo
				CarreteraEntranteConIntervalo cSig = carreterasEntrantes.get(indiceSemaforoVerde);
				indiceSemaforoVerde = i;
				int maxSize =  cSig.size();
				while (i != indiceAntiguo)
				{
					if (carreterasEntrantes.get(i).size() > maxSize)
					{
						indiceSemaforoVerde = i;
						cSig = carreterasEntrantes.get(i);
						maxSize = cSig.size();
					}
					i = (i + 1) % carreterasEntrantes.size();
				}
				//POR QUE NO SE PUEDE SUSTITUIR carreterasEntrantes.get(indiceSemaforoVerde) por cSig????????? 
				asignaIntervalo(carreterasEntrantes.get(indiceSemaforoVerde), maxSize);
				//cSig.ponSemaforo(true);
				carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(true);
			}
		}
		else
		{
			int i = 0;
			int maxSize = -1;
			for (CarreteraEntranteConIntervalo ce : carreterasEntrantes)
			{
				if (ce.size() > maxSize)
				{
					maxSize = ce.size();
					indiceSemaforoVerde = i;
				}
				ce.setIntervaloDeTiempo(0);
				i++;
			}
			CarreteraEntranteConIntervalo cVerde = carreterasEntrantes.get(indiceSemaforoVerde);
			cVerde.ponSemaforo(true);
			asignaIntervalo(cVerde,maxSize);
		}
	}

	private void asignaIntervalo(CarreteraEntranteConIntervalo cSig, int size)
	{
		cSig.setIntervaloDeTiempo(Math.max(size/2, 1));
		cSig.resetUnidadesDeTiempoUsadas();
	}

	@Override
	protected CarreteraEntranteConIntervalo creaCarreteraEntrante(Carretera carretera) {
		return new CarreteraEntranteConIntervalo(carretera);
	}
	
	@Override
	public void completaDetallesSeccion(IniSection is)
	{
		super.completaDetallesSeccion(is);
	    is.setValue("type", "mc");
	}
}
