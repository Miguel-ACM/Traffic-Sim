
package es.ucm.fdi.model.constructorEventos;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.eventos.Evento;
import es.ucm.fdi.model.eventos.EventoNuevoCruceCongestionado;

public class ConstructorEventoNuevoCruceCongestionado extends ConstructorEventos{

	public ConstructorEventoNuevoCruceCongestionado()
	{
		this.etiqueta = "new_junction";
		this.claves = new String[] {"time", "id", "type"};
		this.valoresPorDefecto = new String[] {"", "", "mc"};
	}
	
	@Override
	public Evento parser(IniSection section) {
		if (section.getTag().equals(this.etiqueta) && "mc".equals(section.getValue("type")))
	{
		int time = ConstructorEventos.parseaIntNoNegativo(section, "time", 0);
		String id = ConstructorEventos.identificadorValido(section, "id");
		return new EventoNuevoCruceCongestionado(time, id);
												
	}
		return null;
	}
	
	
	@Override
	public String toString()
	{
		return "New most-crowded";
	}


	
}
