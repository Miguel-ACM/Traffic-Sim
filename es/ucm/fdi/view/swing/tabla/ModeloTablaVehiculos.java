package es.ucm.fdi.view.swing.tabla;

import java.util.List;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.eventos.Evento;
import es.ucm.fdi.model.vehiculos.Vehiculo;

public class ModeloTablaVehiculos extends ModeloTabla<Vehiculo>
{
	
	public ModeloTablaVehiculos(String[] columnIdEventos, Controlador ctrl)
	{
		super(columnIdEventos, ctrl);
	}
	
	public Object getValueAt(int indiceFil, int indiceCol)
	{
		Object s = null;
		switch(indiceCol)
		{
		case 0:
			s = this.lista.get(indiceFil).getId();;
			break;
		case 1:
			s = this.lista.get(indiceFil).getCarretera();
			break;
		case 2: 
			s = this.lista.get(indiceFil).getLocalizacionString();
			break;
		case 3: 
			s = this.lista.get(indiceFil).getVelocidad();
			break;
		case 4: 
			s = this.lista.get(indiceFil).getKilometrage();
			break;
		case 5: 
			s = this.lista.get(indiceFil).getTiempoDeInfraccion();
			break;
		case 6: 
			s = this.lista.get(indiceFil).getItinerario();
			break;
		default: assert(false);
		}
		return s;
	}
	
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos)
	{
		this.lista = mapa.getVehiculos(); 
		this.fireTableStructureChanged();
	}
	
	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos)
	{
		this.lista = mapa.getVehiculos(); 
		this.fireTableStructureChanged();
	}

}
