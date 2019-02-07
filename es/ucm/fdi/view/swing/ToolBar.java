package es.ucm.fdi.view.swing;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.control.ObservadorSimuladorTrafico;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.eventos.Evento;

public class ToolBar extends JToolBar implements ObservadorSimuladorTrafico {

	private JSpinner steps;
	private JSpinner delay;
	private JTextField time;
	
	private JButton botonSalir;
	private JToggleButton botonCambiaSalida;
	private JButton botonCargar;
	private JButton botonPlay;
	private JButton botonReiniciaSimulador;
	private JButton botonLimpiaInformes;
	private JButton botonGeneraReports;
	private JButton botonInserta;
	private JButton botonGuardar;
	private JButton botonReinicio;
	
	public ToolBar(VentanaPrincipal mainWindow, Controlador controlador)
	{
		super();
		this.setFloatable(false);
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		controlador.addObserver(this);
		
		////////////////////BOTON CARGAR///////////////////////////////////////////////////////////
		
		botonCargar = new JButton();
		botonCargar.setToolTipText("Carga un fichero de eventos.");
		botonCargar.setIcon(new ImageIcon(("resources/icons/open.png")));
		botonCargar.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0) {
						mainWindow.cargaFichero();
					}
				});
		this.add(botonCargar);
		
		////////////////////BOTON GUARDAR///////////////////////////////////////////////////////////

		botonGuardar = new JButton();
		botonGuardar.setToolTipText("Guarda un fichero de eventos.");
		botonGuardar.setIcon(new ImageIcon(("resources/icons/save.png")));
		botonGuardar.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0) {
						mainWindow.guardaFichero();
					}
				});
		this.add(botonGuardar);
		
		////////////////////BOTON REINICIO///////////////////////////////////////////////////////////
		
		botonReinicio = new JButton();
		botonReinicio.setToolTipText("Limpia la zona de eventos.");
		botonReinicio.setIcon(new ImageIcon(("resources/icons/clear.png")));
		botonReinicio.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0) {
						mainWindow.limpia();
					}
				});
		this.add(botonReinicio);
		
		this.addSeparator();
		
		////////////////////BOTON INSERTAR///////////////////////////////////////////////////////////
		
		botonInserta = new JButton();
		botonInserta.setToolTipText("Inserta eventos en la simulacion.");
		botonInserta.setIcon(new ImageIcon(("resources/icons/calendar.png")));
		botonInserta.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0) {
						mainWindow.cargaEvento();
					}
				});
		this.add(botonInserta);
		
		
		this.steps = new JSpinner(new SpinnerNumberModel(5,1,1000,1));
		this.steps.setToolTipText("pasos a ejecutar: 1-1000");
		this.steps.setMaximumSize(new Dimension(70,70));
		this.steps.setMinimumSize(new Dimension(70,70));
		this.steps.setValue(1);
		
		////////////////////BOTON PLAY///////////////////////////////////////////////////////////
		
		botonPlay = new JButton();
		botonPlay.setToolTipText("Lanza la simulación.");
		botonPlay.setIcon(new ImageIcon(("resources/icons/play.png")));
		botonPlay.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.ejecuta();
			}
		});
		
		this.add(botonPlay);
		
		//////////////////BOTON PARAR////////////////////////////////////////////////////
		
		JButton botonPause = new JButton();
		botonPause.setToolTipText("Para la simulación.");
		botonPause.setIcon(new ImageIcon(("resources/icons/stop.png")));
		botonPause.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0) {
						 mainWindow.interruptThread();
					}
				});
		this.add(botonPause);
		
		////////////////////BOTON REINICIA SIMULADOR///////////////////////////////////////////////////////////

		botonReiniciaSimulador = new JButton();
		botonReiniciaSimulador.setToolTipText("Reinicia la simulación.");
		botonReiniciaSimulador.setIcon(new ImageIcon(("resources/icons/redo.png")));
		botonReiniciaSimulador.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controlador.reinicia(); 
			}
		});
		this.add(botonReiniciaSimulador);
		
		this.addSeparator();
				
		this.add(new JLabel(" Delay: "));
		
		this.delay = new JSpinner(new SpinnerNumberModel(100,0,Integer.MAX_VALUE,100));
		this.delay.setMaximumSize(new Dimension(70,70));
		this.delay.setMinimumSize(new Dimension(70,70));
		this.delay.setToolTipText("Tiempo de espera entre pasos: 0-100000 (ms)");
		
		this.add(delay);
		
		this.add(new JLabel(" Pasos: "));
		this.add(steps);
		
		this.add(new JLabel(" Tiempo: "));
		this.time = new JTextField("0", 5);
		this.time.setToolTipText("Tiempo actual");
		this.time.setMaximumSize(new Dimension(70,70));
		this.time.setMinimumSize(new Dimension(70,70));
		this.time.setEditable(false);
		this.addSeparator();
		this.add(this.time);
		
		this.addSeparator();
		
		//OPCIONAL
		botonGeneraReports = new JButton();
		botonGeneraReports.setToolTipText("Genera informes.");
		botonGeneraReports.setIcon(new ImageIcon(("resources/icons/file.png")));
		botonGeneraReports.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {	
						mainWindow.generaInformes();
					}
				});
		this.add(botonGeneraReports);
		
		botonCambiaSalida = new JToggleButton();
		botonCambiaSalida.setIcon(new ImageIcon(("resources/icons/fileClear.png")));
		botonCambiaSalida.addItemListener(new ItemListener()
				{
					@Override
					public void itemStateChanged(ItemEvent e) {
						if (botonCambiaSalida.isSelected())
						{
							botonCambiaSalida.setToolTipText("Desactiva la salida inmediata del informe.");
							mainWindow.setOutput(true);
						}
						else
						{
							botonCambiaSalida.setToolTipText("Activa la salida inmediata del informe.");
							mainWindow.setOutput(false);
						}
					}
		
					
				});
		botonCambiaSalida.setSelected(true);
		this.add(botonCambiaSalida);
		
		botonLimpiaInformes = new JButton();
		botonLimpiaInformes.setToolTipText("Limpia los informes.");
		botonLimpiaInformes.setIcon(new ImageIcon(("resources/icons/fileDelete.png")));
		botonLimpiaInformes.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {	
						mainWindow.limpiaInformes();
					}
				});
		this.add(botonLimpiaInformes);
		
		this.addSeparator();
		
		botonSalir = new JButton();
		botonSalir.setToolTipText("Sale del programa.");
		botonSalir.setIcon(new ImageIcon(("resources/icons/exit.png")));
		botonSalir.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {	
						mainWindow.closeGUI();
					}
				});
		this.add(botonSalir);
	
	}
	
	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.time.setText(""+tiempo);
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {

	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.time.setText("0");
	}

	public int getSteps() {
		return (int) steps.getValue();
	}
	
	public int getDelay()
	{
		return (int) delay.getValue();
	}
	
	public void setEnable(boolean isEnabled)
	{
		botonSalir.setEnabled(isEnabled);
		botonCambiaSalida.setEnabled(isEnabled);
		botonCargar.setEnabled(isEnabled);
		botonPlay.setEnabled(isEnabled);
		botonReiniciaSimulador.setEnabled(isEnabled);
		botonLimpiaInformes.setEnabled(isEnabled);
		botonGeneraReports.setEnabled(isEnabled);
		botonInserta.setEnabled(isEnabled);
		botonGuardar.setEnabled(isEnabled);
		botonReinicio.setEnabled(isEnabled);
		steps.setEnabled(isEnabled);
		//time.setEnabled(isEnabled);
		delay.setEnabled(isEnabled);
	}
	

}
