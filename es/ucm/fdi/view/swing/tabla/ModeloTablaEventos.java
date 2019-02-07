package es.ucm.fdi.view.swing.tabla;

import java.util.List;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.eventos.Evento;

public class ModeloTablaEventos extends ModeloTabla<Evento>
{
	
	public ModeloTablaEventos(String[] columnIdEventos, Controlador ctrl)
	{
		super(columnIdEventos, ctrl);
		//MAS COSEJAS
	}
	
	public Object getValueAt(int indiceFil, int indiceCol)
	{
		Object s = null;
		switch(indiceCol)
		{
		case 0:
			s = indiceFil;
			break;
		case 1:
			s = this.lista.get(indiceFil).getTiempo();
			break;
		case 2: 
			s = this.lista.get(indiceFil).toString(); //TIPO
			break;
		default: assert(false);
		}
		return s;
	}
		
	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos)
	{
		this.lista = eventos; 
		this.fireTableStructureChanged();
	}
	
	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos)
	{
		this.lista = eventos;
		this.fireTableStructureChanged();
	}
	
	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos)
	{
		this.lista = eventos; 
		this.fireTableStructureChanged();
	}

}
