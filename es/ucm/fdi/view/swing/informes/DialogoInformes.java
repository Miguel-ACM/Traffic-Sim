package es.ucm.fdi.view.swing.informes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.control.ObservadorSimuladorTrafico;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.carreteras.Carretera;
import es.ucm.fdi.model.cruces.CruceGenerico;
import es.ucm.fdi.model.eventos.Evento;
import es.ucm.fdi.model.vehiculos.Vehiculo;
import es.ucm.fdi.view.swing.VentanaPrincipal;

public class DialogoInformes extends JDialog implements ObservadorSimuladorTrafico{
	 protected static final char TECLALIMPIAR = 'c'; 
	 private PanelBotones panelBotones;
	 private JPanel panelPrincipal;
	 private JPanel panelCentral;
	 private PanelObjSim<Vehiculo> panelVehiculos;
	 private PanelObjSim<Carretera> panelCarreteras;
	 private PanelObjSim<CruceGenerico<?>> panelCruces;
	 private Controlador controlador;
	 private VentanaPrincipal ventanaPrincipal;
	 //...
	 
	 
	 
	 public DialogoInformes(VentanaPrincipal ventanaPrincipal,
			Controlador controlador) {
		 setModalityType(ModalityType.APPLICATION_MODAL);
		 this.setTitle("Generar informe especifico");
		 this.ventanaPrincipal = ventanaPrincipal;
		 this.controlador = controlador;
		 controlador.addObserver(this);
		 initGUI();
		 ocultar();
	}

	private void initGUI() {
		 //..
		 panelPrincipal = new JPanel();
		 panelPrincipal.setLayout(new BorderLayout());
		
		 panelCentral = new JPanel();
		 panelCentral.setLayout(new GridLayout(1,3));
		 this.setMinimumSize(new Dimension (500,300));
		 this.setPreferredSize(new Dimension (500,300));
		 
		 this.panelVehiculos = new PanelObjSim<Vehiculo>("Vehiculos");
		 this.panelCarreteras = new PanelObjSim<Carretera>("Carreteras");
		 this.panelCruces = new PanelObjSim<CruceGenerico<?>>("Cruces");
		 this.panelBotones = new PanelBotones(this);
		 panelCentral.add(panelVehiculos);
		 panelCentral.add(panelCarreteras);
		 panelCentral.add(panelCruces);
		 InformationPanel panelInfo = new InformationPanel();
		 panelPrincipal.add(panelInfo,BorderLayout.PAGE_START);
		 panelPrincipal.add(panelCentral,BorderLayout.CENTER);
		 panelPrincipal.add(panelBotones,BorderLayout.PAGE_END);
		 this.add(panelPrincipal);
	}
	 
	 private void ocultar()
	 {
		 this.setVisible(false);
	 }
	
	 public void mostrar() {
		 this.setLocationRelativeTo(ventanaPrincipal);
		 this.setVisible(true);
	 }
	 
	 private void setMapa(MapaCarreteras mapa) {
		 this.panelVehiculos.setList(mapa.getVehiculos());
		 this.panelCarreteras.setList(mapa.getCarreteras());
		 this.panelCruces.setList(mapa.getCruces());
		}
	 
	 public List<Vehiculo> getVehiculosSeleccionados() {
		 return this.panelVehiculos.getSelectedItems();
	 }
	 
	 public List<Carretera> getCarreterasSeleccionadas() {
		 return this.panelCarreteras.getSelectedItems();
		}
	 
	 public List<CruceGenerico<?>> getCrucesSeleccionados() {
		 return this.panelCruces.getSelectedItems();
		}
	 
	 @Override
	 public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		 this.setMapa(mapa);
	 }
	 
	 @Override
	 public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		 this.setMapa(mapa);
	 }
	 
	 @Override
	 public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
	  this.setMapa(mapa);
	 }

	public void cancelar() {
		this.ocultar();
	}

	public void generar() {
		ventanaPrincipal.setAreaInformes(controlador.generaInformes(getCrucesSeleccionados(), getCarreterasSeleccionadas(), getVehiculosSeleccionados()));
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null, "Informe generado.", "Info", JOptionPane.INFORMATION_MESSAGE);		
		this.ocultar();
		
	}

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map,
			List<Evento> eventos, ErrorDeSimulacion e) {		
	}
}
