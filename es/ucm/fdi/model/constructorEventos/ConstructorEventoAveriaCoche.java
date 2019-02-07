package es.ucm.fdi.model.constructorEventos;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.eventos.Evento;
import es.ucm.fdi.model.eventos.EventoAveriaCoche;
import es.ucm.fdi.model.eventos.ParserCarreteras;

public class ConstructorEventoAveriaCoche extends ConstructorEventos {

	public ConstructorEventoAveriaCoche()
	{
		this.etiqueta = "make_vehicle_faulty";
				
		this.claves = new String[] {"time", "vehicles", "duration"};
		this.valoresPorDefecto = new String[] {"", "", ""};
	}
    
	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) ||
				section.getValue("type") != null) return null;
		else
			return new EventoAveriaCoche(
					ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
					ParserCarreteras.parseaListaString(section,"vehicles"),
					ConstructorEventos.parseaIntNoNegativo(section, "duration", 0)
					);
	}
	
	@Override
	public String toString()
	{
		return "Make vehicle faulty";
	}
}