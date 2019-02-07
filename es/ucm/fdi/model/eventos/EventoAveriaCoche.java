package es.ucm.fdi.model.eventos;

import java.util.List;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.vehiculos.Vehiculo;

public class EventoAveriaCoche extends Evento{
	private int tiempoAveria;
	private String[] listaCoches;

	public EventoAveriaCoche(int tiempo, String[] coches, int tiempoAveria) {
		super(tiempo);
		this.tiempoAveria = tiempoAveria;
		this.listaCoches = coches;
	}

	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion{
		try
		{
			List<Vehiculo> listaVehiculos = ParserCarreteras.parseaListaVehiculos(listaCoches,mapa);
			for (Vehiculo v : listaVehiculos)
			{
				v.setTiempoAveria(tiempoAveria);
			}
		}
		catch (ErrorDeSimulacion e)
		{
			throw new ErrorDeSimulacion("No se pudo encontrar el vehiculo a averiar en el tiempo " + tiempo);
		}

	}
	
	@Override
	public String toString() {
		String ret = "Make vehicle faulty (";
		boolean primero = true;
		for (String c : listaCoches)
		{
			if(!primero)
			{
				ret += ", ";
			}
			primero = false;
			ret += c;
		}
		ret += ")"; 
		return ret;
	}
	
}