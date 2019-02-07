package es.ucm.fdi.control;

import java.util.List;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.eventos.Evento;

public interface ObservadorSimuladorTrafico {
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e);
	
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos);
	
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos);
	
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos);
}
