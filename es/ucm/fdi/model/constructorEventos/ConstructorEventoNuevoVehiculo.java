package es.ucm.fdi.model.constructorEventos;


import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.eventos.Evento;
import es.ucm.fdi.model.eventos.EventoNuevoVehiculo;
import es.ucm.fdi.model.eventos.ParserCarreteras;

public class ConstructorEventoNuevoVehiculo extends ConstructorEventos{

	public ConstructorEventoNuevoVehiculo()
	{
		this.etiqueta = "new_vehicle";
		this.claves = new String[] {"time", "id", "itinerary", "max_speed"};
		this.valoresPorDefecto = new String[] {"", "", "", ""};
	}

	@Override
	public Evento parser(IniSection section) {
		if (section.getTag().equals(this.etiqueta) && section.getValue("type") == null)
		{
			int time = ConstructorEventos.parseaIntNoNegativo(section, "time", 0);
			String id =ConstructorEventos.identificadorValido(section, "id");
			int max = ConstructorEventos.parseaIntNoNegativo(section, "max_speed", 0);
			String[] itinerary = ParserCarreteras.parseaListaString(section, "itinerary");
			return new EventoNuevoVehiculo( time,id,max,itinerary);
		}
		return null;
	

	}
	
	@Override
	public String toString() { return "New vehicle"; }

}