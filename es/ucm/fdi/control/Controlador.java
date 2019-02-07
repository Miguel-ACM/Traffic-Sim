package es.ucm.fdi.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.SimuladorTrafico;
import es.ucm.fdi.model.carreteras.Carretera;
import es.ucm.fdi.model.cruces.CruceGenerico;
import es.ucm.fdi.model.eventos.Evento;
import es.ucm.fdi.model.eventos.ParserEventos;
import es.ucm.fdi.model.vehiculos.Vehiculo;

public class Controlador {

	private SimuladorTrafico simulador;
	private OutputStream ficheroSalida;
	private InputStream ficheroEntrada;
	private int pasosSimulacion;


	public void setFicheroEntrada(InputStream input)
	{
		ficheroEntrada = input;
	}
	
	public void setFicheroSalida(OutputStream output)
	{
		ficheroSalida = output;
	}
	
	public Controlador(SimuladorTrafico sim, int limite, InputStream input, OutputStream output)
	{
		pasosSimulacion = limite;
		ficheroEntrada = input;
		ficheroSalida = output;
		simulador = sim;
	}

	public void ejecuta() 
	{
		try 
		{
			cargaEventos(ficheroEntrada);
			simulador.ejecuta(pasosSimulacion + 1, ficheroSalida); //+1 para que escriba hasta el tiempo (pasosSimulacion)
			System.out.println("Done!\n");
		}
		catch (Exception e)
		{
			System.out.println(e.getLocalizedMessage()+ '\n');
		}

	}
	
	public void ejecuta(int pasos) throws ErrorDeSimulacion, IOException
	{
		simulador.ejecuta(pasos, ficheroSalida);
	}
	
	public void cargaEventos(InputStream inStream) throws ErrorDeSimulacion
	{
		Ini ini;
		try {
			// lee el fichero y carga su atributo iniSections
			ini = new Ini(inStream);
		}
		catch (Exception e) {
			throw new ErrorDeSimulacion("Error en la lectura de eventos (" + e.getLocalizedMessage() + ")");
		}
		// recorremos todas los elementos de iniSections para generar el evento
		// correspondiente
		for (IniSection sec : ini.getSections()) {
			// parseamos la sección para ver a que evento corresponde
			try
			{
				Evento e = ParserEventos.parseaEvento(sec);
				if (e != null)
					this.simulador.insertaEvento(e);
				else
					throw new ErrorDeSimulacion("Evento desconocido: " + sec.getTag());
			}
			catch (IllegalArgumentException e)
			{
				throw new ErrorDeSimulacion(e.getLocalizedMessage());
			}

		}
	}

	public void reinicia()
	{
		simulador.reinicia();
	}
	
	public void addObserver(ObservadorSimuladorTrafico o)
	{
		simulador.addObservador(o);
	}
	
	public void removeObserver(ObservadorSimuladorTrafico o)
	{
		simulador.removeObservador(o);
	}	

	public String generaInformes(List<CruceGenerico<?>> cruces, List<Carretera> carreteras,
			List<Vehiculo> vehiculos) {
		return simulador.getReporteAcumulado(vehiculos, carreteras, cruces);
		
	}
}

