package es.ucm.fdi.model.cruces;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.carreteras.Carretera;

public class CarreteraEntranteConIntervalo extends CarreteraEntrante{

	private int intervaloDeTiempo; //tiempo que ha de transcurrir para poner el semáforo de la carretera en rojo
	private int unidadesDeTiempoUsadas; //Se incrementa cada vez que pasa un vehículo
	private boolean usoCompleto; //Controla que en cada paso con el semaforo en verde ha pasado un vehiculo
	private boolean usadaPorUnVehiculo; //Ha pasado al menos un vehiculo mientras el semáforo estaba en verde
	
	public CarreteraEntranteConIntervalo(Carretera carretera) {
		
		super(carretera);
		intervaloDeTiempo = 1;
		unidadesDeTiempoUsadas = 0;
		usoCompleto = true;
		usadaPorUnVehiculo = false;
	}
	
	@Override
	protected void avanzaPrimerVehiculo() throws ErrorDeSimulacion
	{
		unidadesDeTiempoUsadas++;
		if (colaVehiculos.size() > 0)
		{
			colaVehiculos.get(0).moverASiguienteCarretera();
			colaVehiculos.remove(0);
			usadaPorUnVehiculo = true;
		}
		else
			usoCompleto = false;
	}
	
	//Se confia en que el usuario introduzca un numero válido.
	public void setIntervaloDeTiempo(int intervalo)
	{
		intervaloDeTiempo = intervalo;
	}
	
	public void resetUnidadesDeTiempoUsadas()
	{
		usoCompleto = true;
		usadaPorUnVehiculo = false;
		unidadesDeTiempoUsadas = 0;
	}
	
	public boolean tiempoConsumido()
	{
		return intervaloDeTiempo <= unidadesDeTiempoUsadas;
	}
	
	public boolean usoCompleto()
	{
		return usoCompleto;
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		ret += "(";
		ret += this.getId();
		ret += ",";
		ret += super.escribeSemaforo();
		if (semaforo)
		{
			int restante = this.intervaloDeTiempo - this.unidadesDeTiempoUsadas;
			ret += restante != 0 ? ":" + restante : "";
		}
		ret += ",";
		ret += this.escribeColaVehiculos();
		ret += ")";
		return ret;
		
	}
	
	public boolean usada()
	{
		return usadaPorUnVehiculo;
	}

	public int getIntervaloDeTiempo()
	{
		return this.intervaloDeTiempo;
	}
	
	public int getUnidadesDeTiempoUsadas()
	{
		return this.unidadesDeTiempoUsadas;
	}

}
