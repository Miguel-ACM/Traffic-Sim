package es.ucm.fdi.model.eventos;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.cruces.CruceGenerico;
import es.ucm.fdi.model.vehiculos.Vehiculo;

public class ParserCarreteras {

	public static String[] parseaListaString(IniSection ini, String clave)
	{
		String s = ini.getValue(clave);
		return s.trim().split(",");
	}

	public static List<CruceGenerico<?>> parseaListaCruces(String[] iti, MapaCarreteras mapa) throws ErrorDeSimulacion
	{
		List<CruceGenerico<?>> cruces = new ArrayList<>();
		for (int i = 0; i < iti.length; i++)
		{
			cruces.add(mapa.getCruce(iti[i]));
		}
		return cruces;
	}

	public static List<Vehiculo> parseaListaVehiculos(String[] lista, MapaCarreteras mapa) throws ErrorDeSimulacion
	{
		List<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
		for (int i = 0; i < lista.length; i++)
		{
			vehiculos.add(mapa.getVehiculo(lista[i]));
		}
		return vehiculos;
	}

}