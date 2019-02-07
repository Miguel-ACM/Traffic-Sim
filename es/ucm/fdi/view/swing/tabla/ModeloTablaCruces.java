package es.ucm.fdi.view.swing.tabla;

import java.util.List;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.cruces.CruceGenerico;
import es.ucm.fdi.model.eventos.Evento;

public class ModeloTablaCruces extends ModeloTabla<CruceGenerico<?>>
{
	
	public ModeloTablaCruces(String[] columnIdEventos, Controlador ctrl)
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
			s = lista.get(indiceFil).getId();
			break;
		case 1:
			s = lista.get(indiceFil).getCarreteraVerde();
			break;
		case 2: 
			s = lista.get(indiceFil).getCarreterasRojas();
			break;
		default: assert(false);
		}
		return s;
	}
	
	//...
	
	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos)
	{
		lista = mapa.getCruces();
		this.fireTableStructureChanged();
	}
	
	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos)
	{
		lista = mapa.getCruces();
		this.fireTableStructureChanged();
	}

}
