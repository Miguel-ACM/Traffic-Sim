package es.ucm.fdi.model.constructorEventos;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.eventos.Evento;
import es.ucm.fdi.model.eventos.EventoNuevoCruceCircular;

public class ConstructorEventoNuevoCruceCircular extends ConstructorEventos {


	public ConstructorEventoNuevoCruceCircular()
	{
		this.etiqueta = "new_junction";
		this.claves = new String[] {"time", "id", "max_time_slice", "min_time_slice", "type"};
		this.valoresPorDefecto = new String[] {"", "", "", "", "rr"};
	}


	@Override
	public Evento parser(IniSection section) {
		if (section.getTag().equals(this.etiqueta) && "rr".equals(section.getValue("type"))) 
		{
			int time = ConstructorEventos.parseaIntNoNegativo(section, "time", 0);
			String id = ConstructorEventos.identificadorValido(section, "id");
			return new EventoNuevoCruceCircular(time, id, ConstructorEventos.parseaIntNoNegativo(section, "min_time_slice", 1), 
					ConstructorEventos.parseaIntNoNegativo(section, "max_time_slice", 1));
		}
		return null;
	}
	
	@Override
	public String toString()
	{
		return "New round-robin";
	}
	
	
}
