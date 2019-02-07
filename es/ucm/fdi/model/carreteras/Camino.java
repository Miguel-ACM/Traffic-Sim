package es.ucm.fdi.model.carreteras;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.cruces.CruceGenerico;

public class Camino extends Carretera{ 
	public Camino(String id, int lenght, int maxSpeed, CruceGenerico<?> src, CruceGenerico<?> dest) {
		super(id, lenght, maxSpeed, src, dest);
	}

	@Override
	protected int calculaVelocidadBase()
	{
		return velocidadMaxima;
	}

	protected int calculaFactorReduccion(int obstaculos)
	{
		/*int numAveriados=0;
		for (Vehiculo v : vehiculos){
			if(v.getTiempoDeInfraccion()>0)
				numAveriados++;
		}
		return 1 + numAveriados; */
		return 1 + obstaculos;
	}
	
	@Override
	public void completaDetallesSeccion(IniSection is) {
		is.setValue("type", "dirt");
		super.completaDetallesSeccion(is);
	}
}