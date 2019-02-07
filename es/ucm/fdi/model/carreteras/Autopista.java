package es.ucm.fdi.model.carreteras;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.cruces.CruceGenerico;

public class Autopista extends Carretera{

	private int lanes;
	
	public Autopista(String id, int lenght, int maxSpeed, CruceGenerico<?> src, CruceGenerico<?> dest, int lanes) {
		super(id, lenght, maxSpeed, src, dest);
		this.lanes = lanes;
	}

	@Override
	protected int calculaVelocidadBase()
	{
		return Math.min(velocidadMaxima,1 + (velocidadMaxima * lanes)/Math.max(vehiculos.size(),1));
	}

	@Override
	protected int calculaFactorReduccion(int obstaculos)
	{
		return (obstaculos >= lanes ? 2 : 1);
	}
	
	@Override
	public void completaDetallesSeccion(IniSection is)
	{
		is.setValue("type", "lanes");
		super.completaDetallesSeccion(is);
	}
}
