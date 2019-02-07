package es.ucm.fdi.view.swing.tabla;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.control.ObservadorSimuladorTrafico;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.eventos.Evento;

public class ModeloTabla<T> extends DefaultTableModel implements ObservadorSimuladorTrafico
{
	protected String[] columnIds;
	protected List<T> lista;

	public ModeloTabla(String[] columnIdEventos, Controlador ctrl){
		this.lista = new ArrayList<>();
		columnIds = columnIdEventos;
		ctrl.addObserver(this);
	}
	
	@Override
    public boolean isCellEditable(int row, int column) {
       return false;
    }
	
	@Override 
	public String getColumnName(int col){
		return this.columnIds[col];
	}
	
	@Override
	public int getColumnCount(){
		return this.columnIds.length;
	}
	
	@Override 
	public int getRowCount(){
		return this.lista == null ? 0 : this.lista.size();
	}
	
	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map,
			List<Evento> eventos, ErrorDeSimulacion e) {		
	}
	
	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
	}
	
	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
	}
	
	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
	}



}
