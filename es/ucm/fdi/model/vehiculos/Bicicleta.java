package es.ucm.fdi.model.vehiculos;

import java.util.List;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.cruces.CruceGenerico;

public class Bicicleta extends Vehiculo{

	public Bicicleta(String id, int velocidadMaxima, List<CruceGenerico<?>> iti) throws ErrorDeSimulacion {
		super(id, velocidadMaxima, iti);
	}
	
	@Override
	public void setTiempoAveria(Integer duracionAveria)
	{
		/*if (duracionAveria < 0)
			throw new ErrorDeSimulacion("La duracion de la averia es negativa."); 
		*/
		if (carretera != null && velocidadActual > velocidadMaxima / 2) 
			tiempoAveria += duracionAveria; 			
	}
	
	@Override
	public void completaDetallesSeccion(IniSection is)
	{
		is.setValue("type", "bike");
		super.completaDetallesSeccion(is);
	}

}
