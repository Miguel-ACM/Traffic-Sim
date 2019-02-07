package es.ucm.fdi.model;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import es.ucm.fdi.control.Observador;
import es.ucm.fdi.control.ObservadorSimuladorTrafico;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.carreteras.Carretera;
import es.ucm.fdi.model.cruces.CruceGenerico;
import es.ucm.fdi.model.eventos.Evento;
import es.ucm.fdi.model.utils.SortedArrayList;
import es.ucm.fdi.model.vehiculos.Vehiculo;

public class SimuladorTrafico implements Observador<ObservadorSimuladorTrafico> {
	private int contadorTiempo;
	private MapaCarreteras mapa;
	private List<Evento> eventos; //ordenada
	private List<ObservadorSimuladorTrafico> observadores;
	
	public SimuladorTrafico() 
	{
		this.mapa = new MapaCarreteras();
		this.contadorTiempo = 0;
		
		Comparator<Evento> cmp = new Comparator<Evento>() 
		{
			public int compare(Evento a, Evento b)
			{
				if (a.getTiempo() > b.getTiempo()) return 1;
				else if (a.getTiempo() < b.getTiempo()) return -1;
				return 0;
			}
		};
		this.eventos = new SortedArrayList<>(cmp); // estructura ordenada por tiempo
		observadores = new ArrayList<>();
	}

	public synchronized void ejecuta(int pasosSimulacion, OutputStream ficheroSalida) throws IOException, ErrorDeSimulacion
	{
		String report = "";
		int limiteTiempo = contadorTiempo + pasosSimulacion;
		try
		{
			for (; contadorTiempo < limiteTiempo; contadorTiempo++)
			{
				if (contadorTiempo != 0) {
					mapa.actualizar();
					report += mapa.generateReport(contadorTiempo);
				}
				while (!eventos.isEmpty() && eventos.get(0).getTiempo() == contadorTiempo)
				{
					eventos.get(0).ejecuta(mapa);
					eventos.remove(0);
					//contadorEventos++;
				}

			}

			notificaAvanza();
			generaInforme(report, ficheroSalida); 
		}
		catch (ErrorDeSimulacion e)
		{
			notificaError(e);
			throw e;
		}
	}

	public void insertaEvento(Evento e) throws ErrorDeSimulacion
	{
		if (e != null)
		{
			if (e.getTiempo()>= this.contadorTiempo)
			{
				this.eventos.add(e);
				this.notificaNuevoEvento();
			}
			/*else
			{
				//ErrorDeSimulacion err = new ErrorDeSimulacion("Un evento tiene un tiempo menor al tiempo actual."); //NO AÑADE NADA SI ESTA MAL
				//this.notificaError(err);
				//throw err;
			}
			 */
			
		}
		else
		{
			ErrorDeSimulacion err = new ErrorDeSimulacion("Un evento dado es NULL.");
			this.notificaError(err);
			throw err;
		}
	}
	
	public void reinicia()
	{
		mapa.reinicia(); //Se podria crear MapaCarreteras.vacia();
		this.contadorTiempo = 0;
		//this.contadorEventos = 0;
		
		eventos.removeAll(eventos);// = new SortedArrayList<>(cmp); // estructura ordenada por tiempo
		notificaReinicia();
	}

	private void notificaError(ErrorDeSimulacion e) {
		for (ObservadorSimuladorTrafico o : this.observadores)
			o.errorSimulador(this.contadorTiempo, this.mapa, this.eventos, e);
	}

	private void notificaNuevoEvento() {
		for (ObservadorSimuladorTrafico o : this.observadores)
			o.addEvento(this.contadorTiempo, this.mapa, this.eventos);
	}
	
	private void notificaAvanza()
	{
		for (ObservadorSimuladorTrafico o : this.observadores)
			o.avanza(this.contadorTiempo, this.mapa, this.eventos);
	}

	private void notificaReinicia()
	{
		for (ObservadorSimuladorTrafico o : this.observadores)
			o.reinicia(this.contadorTiempo, this.mapa, this.eventos);
	}

	private void generaInforme(String report, OutputStream salida) throws IOException
	{
		//System.out.println(report); //Descomentar para escribir la salida también por consola.
		
		if (salida != null)
		{
			byte[] reportBytes = report.getBytes();
			salida.write(reportBytes);
		}
	}

	@Override
	public void addObservador(ObservadorSimuladorTrafico o) {
		if (o != null && !this.observadores.contains(o))
		observadores.add(o);
	}

	@Override
	public void removeObservador(ObservadorSimuladorTrafico o) {
		if (o != null && this.observadores.contains(o))
		observadores.remove(o);
	}

	public String getReporteAcumulado(List<Vehiculo> vehiculos, List<Carretera> carreteras, List<CruceGenerico<?>> cruces) {
		int tiempo = 0;
		String reportAcumulado = "";
		String aux;
		while (tiempo < this.contadorTiempo)
		{
			if (cruces != null)
				for (CruceGenerico<?> c: cruces)
				{
					aux = c.getReport(tiempo);
					if (aux != null) reportAcumulado += aux;
				}

			if (carreteras != null)
				for (Carretera c: carreteras)
				{
					aux = c.getReport(tiempo);
					if (aux != null) reportAcumulado += aux;
				}

			if (vehiculos != null)
				for (Vehiculo v: vehiculos)
				{
					aux = v.getReport(tiempo);
					if (aux != null) reportAcumulado += aux;
				}
			tiempo++;

		}
		return reportAcumulado;
	}
}
	
