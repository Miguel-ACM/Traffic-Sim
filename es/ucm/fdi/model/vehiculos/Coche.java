package es.ucm.fdi.model.vehiculos;

import java.util.List;
import java.util.Random;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.cruces.CruceGenerico;

public class Coche extends Vehiculo {


	private int resistenciaKm;
	private double probabilidadDeAveria;
	private int maximaDuracionAveria;
	private Random numAleatorio;
	private int distanciaUltimaAveria;

	public Coche(String id, int velocidadMaxima, List<CruceGenerico<?>> iti, 
		int res, double faultProbability, int maxFaultDuration, long seed) throws ErrorDeSimulacion {
		super(id, velocidadMaxima, iti);
		resistenciaKm = res;
		probabilidadDeAveria = faultProbability;
		maximaDuracionAveria = maxFaultDuration;
		distanciaUltimaAveria = 0;
		numAleatorio = new Random(seed);
	}
	
	@Override
	public void avanza() {
		if (tiempoAveria == 0 && distanciaUltimaAveria > resistenciaKm && probabilidadDeAveria > numAleatorio.nextDouble())
		{
			setVelocidadActual(0);
			distanciaUltimaAveria = 0;
			setTiempoAveria(numAleatorio.nextInt(maximaDuracionAveria) + 1);
		}
		int kilometrajeAnterior = kilometraje;
		super.avanza();
		distanciaUltimaAveria += kilometraje - kilometrajeAnterior;
	}

	@Override
	public void completaDetallesSeccion(IniSection is)
	{
		is.setValue("type", "car");
		super.completaDetallesSeccion(is);
	}
}