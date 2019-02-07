package es.ucm.fdi.model.constructorEventos;


import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.eventos.Evento;
import es.ucm.fdi.model.eventos.EventoNuevaCarretera;

public class ConstructorEventoNuevaCarretera extends ConstructorEventos {

	public ConstructorEventoNuevaCarretera()
	{
		this.etiqueta = "new_road";
		this.claves = new String[] {"time", "id", "src", "dest", "max_speed", "length"};
		this.valoresPorDefecto = new String[] {"","","","","",""};
	}

	
	@Override
	public Evento parser(IniSection section) {
		if (section.getTag().equals(this.etiqueta) && section.getValue("type") == null)  
		{
			int time = ConstructorEventos.parseaIntNoNegativo(section, "time", 0);
			String id = ConstructorEventos.identificadorValido(section, "id");
			String src = ConstructorEventos.identificadorValido(section, "src");
			String dest = ConstructorEventos.identificadorValido(section, "dest");
			int max_speed = ConstructorEventos.parseaIntNoNegativo(section, "max_speed", 0);
			int length = ConstructorEventos.parseaIntNoNegativo(section, "length", 0);
			return new EventoNuevaCarretera(time, id, src, dest, max_speed, length);	
		}
		return null;
	}
	
	@Override
	public String toString()
	{
		return "New road";
	}

}
