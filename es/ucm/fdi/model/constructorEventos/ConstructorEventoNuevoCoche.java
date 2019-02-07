package es.ucm.fdi.model.constructorEventos;


import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.eventos.Evento;
import es.ucm.fdi.model.eventos.EventoNuevoCoche;
import es.ucm.fdi.model.eventos.ParserCarreteras;

public class ConstructorEventoNuevoCoche extends ConstructorEventos{

	public ConstructorEventoNuevoCoche()
	{
		this.etiqueta = "new_vehicle";
		this.claves = new String[] {"time", "id", "itinerary", "max_speed", "resistance", "fault_probability", "max_fault_duration", "seed", "type"};
		this.valoresPorDefecto = new String[] {"", "", "", "", "", "", "", "", "car"};
	}

	@Override
	public Evento parser(IniSection section) {
		if (section.getTag().equals(this.etiqueta) && "car".equals(section.getValue("type")))
		{
			int time = ConstructorEventos.parseaIntNoNegativo(section, "time", 0);
			String id =ConstructorEventos.identificadorValido(section, "id");
			int max = ConstructorEventos.parseaIntNoNegativo(section, "max_speed", 0);
			String[] itinerary = ParserCarreteras.parseaListaString(section, "itinerary");
			int resistance = ConstructorEventos.parseaIntNoNegativo(section, "resistance", 0);
			double fault_probability = ConstructorEventos.parseaDoubleNoNegativo(section, "fault_probability");
			int max_fault_duration = ConstructorEventos.parseaIntNoNegativo(section,"max_fault_duration", 0);
			
			if (section.getValue("seed") != null)
				return new EventoNuevoCoche(time, id, max, itinerary, resistance, fault_probability, max_fault_duration,
						ConstructorEventos.parseaIntNoNegativo(section,"seed", 0));
			else
				return new EventoNuevoCoche(time, id, max, itinerary, resistance, fault_probability, max_fault_duration);
		}
		return null;

	}


	@Override
	public String toString() { return "New car"; }

}