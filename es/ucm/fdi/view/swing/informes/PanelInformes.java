package es.ucm.fdi.view.swing.informes;

import java.util.List;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.control.ObservadorSimuladorTrafico;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.eventos.Evento;
import es.ucm.fdi.view.swing.JTextAreaOutputStream;
import es.ucm.fdi.view.swing.PanelAreaTexto;

public class PanelInformes extends PanelAreaTexto implements ObservadorSimuladorTrafico{
	
	Controlador ctrl;
	
	JTextAreaOutputStream OutputArea;
	
	public PanelInformes(String titulo, boolean editable, Controlador ctrl){
		super(titulo,editable);
		this.ctrl = ctrl;
		OutputArea = new JTextAreaOutputStream(areatexto);
		ctrl.addObserver(this);
	}	
	
	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map,
			List<Evento> eventos, ErrorDeSimulacion e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.areatexto.setText("");
	}
	
	public void setOutput(boolean value)
	{
		if (value)
			ctrl.setFicheroSalida(OutputArea);
		else
			ctrl.setFicheroSalida(null);
	}


}
