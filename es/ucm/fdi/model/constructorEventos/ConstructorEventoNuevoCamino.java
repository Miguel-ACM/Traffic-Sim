package es.ucm.fdi.model.constructorEventos;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.eventos.Evento;
import es.ucm.fdi.model.eventos.EventoNuevoCamino;

public class ConstructorEventoNuevoCamino extends ConstructorEventos{
	
	public ConstructorEventoNuevoCamino()
	{
		this.etiqueta = "new_road";
		this.claves = new String[] {"time", "id", "src", "dest", "max_speed", "length", "type"};
		this.valoresPorDefecto = new String[] {"", "", "", "", "", "", "dirt"};
	}

	
	@Override
	public Evento parser(IniSection section) {
		if (section.getTag().equals(this.etiqueta) && "dirt".equals(section.getValue("type")))  
		{
			int time = ConstructorEventos.parseaIntNoNegativo(section, "time", 0);
			String id = ConstructorEventos.identificadorValido(section, "id");
			String src = ConstructorEventos.identificadorValido(section, "src");
			String dest = ConstructorEventos.identificadorValido(section, "dest");
			int max_speed = ConstructorEventos.parseaIntNoNegativo(section, "max_speed", 0);
			int length = ConstructorEventos.parseaIntNoNegativo(section, "length", 0);
		    return new EventoNuevoCamino(time, id, src, dest, max_speed, length);
		}
		return null;
		
	}
	
	public String toString()
	{
		return "New dirt road";
	}

}
