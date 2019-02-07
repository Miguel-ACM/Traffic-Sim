package es.ucm.fdi.model.constructorEventos;


import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.eventos.Evento;
import es.ucm.fdi.model.eventos.EventoNuevoCruce;

public class ConstructorEventoNuevoCruce extends ConstructorEventos {


	public ConstructorEventoNuevoCruce()
	{
		this.etiqueta = "new_junction";
		this.claves = new String[] {"time", "id"};
		this.valoresPorDefecto = new String[] {"", ""};
	}


	@Override
	public Evento parser(IniSection section) {
		if (section.getTag().equals(this.etiqueta) && section.getValue("type") == null)
	{
		int time = ConstructorEventos.parseaIntNoNegativo(section, "time", 0);
		String id = ConstructorEventos.identificadorValido(section, "id");
		return new EventoNuevoCruce(time, id);
												
	}
		return null;
	}
	
		@Override
		public String toString()
		{
			return "New junction";
		}
}