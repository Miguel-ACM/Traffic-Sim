package es.ucm.fdi.model.eventos;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.constructorEventos.*;

public class ParserEventos {
	private static ConstructorEventos[] eventos = {
			new ConstructorEventoNuevoCruce(),
			new ConstructorEventoNuevoCruceCircular(),
			new ConstructorEventoNuevoCruceCongestionado(),
			new ConstructorEventoNuevaCarretera(),
			new ConstructorEventoNuevoCamino(),
			new ConstructorEventoNuevaAutopista(),
			new ConstructorEventoNuevoVehiculo(),
			new ConstructorEventoNuevoCoche(),
			new ConstructorEventoNuevaBicicleta(),
			new ConstructorEventoAveriaCoche()
	};
	
	// bucle de prueba y error
	public static Evento parseaEvento(IniSection sec) 
	{
		int i = 0;
		boolean seguir = true;
		Evento e = null;
		while (i < ParserEventos.eventos.length && seguir )
		{
			// ConstructorEventos contiene el método parse(sec)
			e = ParserEventos.eventos[i].parser(sec);
			if (e != null) seguir = false;
			else i++;
		}
		return e;
	}

	public static ConstructorEventos[] getConstructoresEventos() {
		return eventos;
	}
}
