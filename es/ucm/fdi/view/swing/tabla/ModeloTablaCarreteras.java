package es.ucm.fdi.view.swing.tabla;

import java.util.List;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.carreteras.Carretera;
import es.ucm.fdi.model.eventos.Evento;

public class ModeloTablaCarreteras extends ModeloTabla<Carretera>
{
	
	public ModeloTablaCarreteras(String[] columnIdEventos, Controlador ctrl)
	{
		super(columnIdEventos, ctrl);
		//MAS COSEJAS
	}
	
	public Object getValueAt(int indiceFil, int indiceCol)
	{
		Object s = null;
		if (indiceFil >= 0 && indiceFil <= lista.size())
		{switch(indiceCol)
		{
		case 0:
			s = lista.get(indiceFil).getId();
			break;
		case 1:
			s = lista.get(indiceFil).getCruceOrigen();
			break;
		case 2: 
			s = lista.get(indiceFil).getCruceDestino();
			break;
		case 3:
			s = lista.get(indiceFil).getLength();
			break;
		case 4: 
			s = lista.get(indiceFil).getVelocidadMaxima();
			break;
		case 5: 
			s = lista.get(indiceFil).getVehiculos();
			break;
		default: assert(false);
		}
		}
		return s;
	}
	
	
	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos)
	{
		lista = mapa.getCarreteras();
		this.fireTableStructureChanged();
	}
	
	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos)
	{
		lista = mapa.getCarreteras();
		this.fireTableStructureChanged();
	}

}
