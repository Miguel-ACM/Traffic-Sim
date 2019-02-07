package es.ucm.fdi.view.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.control.ObservadorSimuladorTrafico;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.carreteras.Carretera;
import es.ucm.fdi.model.cruces.CruceGenerico;
import es.ucm.fdi.model.eventos.Evento;
import es.ucm.fdi.model.vehiculos.Vehiculo;
import es.ucm.fdi.view.swing.grafico.ComponenteMapa;
import es.ucm.fdi.view.swing.informes.DialogoInformes;
import es.ucm.fdi.view.swing.informes.PanelInformes;
import es.ucm.fdi.view.swing.tabla.ModeloTablaCarreteras;
import es.ucm.fdi.view.swing.tabla.ModeloTablaCruces;
import es.ucm.fdi.view.swing.tabla.ModeloTablaEventos;
import es.ucm.fdi.view.swing.tabla.ModeloTablaVehiculos;
import es.ucm.fdi.view.swing.tabla.PanelTabla;

public class VentanaPrincipal extends JFrame implements ObservadorSimuladorTrafico{
	public static Border bordePorDefecto = BorderFactory.createLineBorder(Color.black,2);
	
	//THREAD EJECUTA
	private Thread ejecutaThread;
	
	//BARRA MENU
	BarraMenu menubar;
	
	//SUPERIOR PANEL
	static private final String[] columnIdEventos = { "#","Tiempo", "Tipo"};
	
	private PanelAreaTexto panelEditorEventos;
	private PanelInformes panelInformes;
	private PanelTabla<Evento> panelColaEventos;
	
	//MENU AND TOOL BAR
	private JFileChooser fc;
	private ToolBar toolbar;
	
	//GRAPHIC PANEL
	private ComponenteMapa componenteMapa;
	
	//STATUS BAR (INFO AT THE BOTTOM OF THE WINDOW)
	private PanelBarraEstado panelBarraEstado;
	
	//INFERIOR PANEL
	static private final String[] columnIdVehiculo = {"ID", "Carretera",
								"Localizacion", "Vel." , "Km", "Tiempo.Ave." , "Itinerario"};
	
	static private final String[] columnIdCarretera = {"Id", "Origen", 
								"Destino", "Longitud", "Vel.Max", "Vehiculos"};
	static private final String[] columnIdCruce = {"Id", "Verde", "Rojo"};
	
	private PanelTabla<Vehiculo> panelVehiculos;
	private PanelTabla<Carretera> panelCarreteras;
	private PanelTabla<CruceGenerico<?>> panelCruces;
	
	//REPORT DIALOG
	private DialogoInformes dialogoInformes; 
	
	//MODEL PART - VIEW CONTROLLER MODEL
	private File ficheroActual;
	private Controlador controlador;	
	
	public VentanaPrincipal(String ficheroEntrada,Controlador ctrl) {
		super("Simulador de Trafico");
		this.controlador = ctrl;
		this.ficheroActual = ficheroEntrada != null ? new File(ficheroEntrada) : null;
		this.initGUI();
		if (ficheroActual != null)
		{
			try {
				ctrl.setFicheroEntrada(new FileInputStream(ficheroActual));
				try {
					ctrl.cargaEventos(new FileInputStream(ficheroActual));
				} catch (ErrorDeSimulacion e) {
					muestraDialogoError(e.getLocalizedMessage());
				}

			} catch (FileNotFoundException e) {
				ctrl.setFicheroEntrada(null);
			}

		}
		
		ctrl.addObserver(this);
	}

	private void initGUI(){
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowListener(){
			@Override
			public void windowOpened(WindowEvent e) {}
			@Override
			public void windowClosing(WindowEvent e) {
				closeGUI();
			}
			@Override
			public void windowClosed(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowActivated(WindowEvent e) {}
			@Override
			public void windowDeactivated(WindowEvent e) {}
		});
		JPanel panelPrincipal = this.creaPanelPrincipal();
		this.setContentPane(panelPrincipal);
		
		//BARRA DE ESTADO INFERIOR
		//(contiene una JLabel para mostar el estado del simulador)
		this.addBarraEstado(panelPrincipal);
		
		//PANEL QUE CONTIENE EL RESTO DE LOS COMPONENTES
		//(lo dividimos en dos paneles(superior e inferior))
		JPanel panelCentral = this.createPanelCentral();
		panelPrincipal.add(panelCentral,BorderLayout.CENTER);
		
		//Panel Superior
		this.createPanelSuperior(panelCentral);
		//menu
		menubar = new BarraMenu(this,this.controlador);
		this.setJMenuBar(menubar);
		
			
		//Panel inferior
		this.createPanelInferior(panelCentral);
		
		//Barra de herramientas
		this.addToolBar(panelPrincipal);
		
		//FILE CHOOSER 
		this.fc = new JFileChooser();
		
		//Report dialog
		this.dialogoInformes = new DialogoInformes(this,this.controlador);
		this.setVisible(true);
		this.pack();
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//this.setLocationRelativeTo(null);


	}
	
	
	private void createPanelInferior(JPanel panelCentral) {
		JPanel panelInferior = new JPanel();
		panelCentral.add(panelInferior,BorderLayout.PAGE_END);
		panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.X_AXIS));
		
		JPanel panelTablas = new JPanel();
		panelTablas.setLayout(new GridLayout(3,1));
		
		panelInferior.add(panelTablas);
		
		this.panelVehiculos = new PanelTabla<Vehiculo>("Vehiculos", new ModeloTablaVehiculos(VentanaPrincipal.columnIdVehiculo,this.controlador));
		this.panelCarreteras = new PanelTabla<Carretera>("Carreteras", new ModeloTablaCarreteras(VentanaPrincipal.columnIdCarretera,this.controlador));
		this.panelCruces = new PanelTabla<CruceGenerico<?>>("Cruces", new ModeloTablaCruces(VentanaPrincipal.columnIdCruce,this.controlador));
		
		panelTablas.add(panelVehiculos);
		panelTablas.add(panelCarreteras);
		panelTablas.add(panelCruces);
		
		
		this.componenteMapa = new ComponenteMapa(this.controlador);
		//añadir un ScroolPane al panel inferior donde se coloca la componente
		panelInferior.add(new JScrollPane(componenteMapa,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
	}

	private void createPanelSuperior(JPanel panelCentral) {
		JPanel panelSuperior = new JPanel();
		panelCentral.add(panelSuperior,BorderLayout.NORTH);

		panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.X_AXIS));
				
		String texto = "";
		String titulo = "Eventos";
		if (ficheroActual != null)
		try
		{
			texto = this.leeFichero(this.ficheroActual);
			titulo = this.ficheroActual.getName();
		}
		catch (IOException e)
		{
			this.ficheroActual = null;
			this.muestraDialogoError("Error durante la lectura del fichero: " + e.getMessage());
		}

		this.panelEditorEventos = new PanelEditorEventos(titulo,texto,true,this);
		this.panelColaEventos = new PanelTabla<Evento>("Cola Eventos: ", new ModeloTablaEventos(VentanaPrincipal.columnIdEventos, this.controlador));
		this.panelInformes = new PanelInformes("Informes: ", false, this.controlador);
		panelSuperior.add(panelEditorEventos);
		panelSuperior.add(panelColaEventos);
		panelSuperior.add(panelInformes);
	}
	
	private String leeFichero(File ficheroActual2) throws IOException{
		String s = "";
		
		try (BufferedReader reader = new BufferedReader(new FileReader(ficheroActual2)))
		{
		String line = reader.readLine();
		while (line != null)
		{
			s += line;
			s += System.lineSeparator();
			line = reader.readLine();
		}
		reader.close();
		}
		return s;
	}

	private void muestraDialogoError(String mensaje)
	{
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
//	public void muestraDialogoInformacion(String mensaje)
//	{
//		Toolkit.getDefaultToolkit().beep();
//		JOptionPane.showMessageDialog(null, mensaje, "Info", JOptionPane.INFORMATION_MESSAGE);
//	}

	private void addToolBar(JPanel panelPrincipal) {
		this.toolbar = new ToolBar(this,this.controlador);
		panelPrincipal.add(this.toolbar, BorderLayout.PAGE_START);
	}
	   
	private JPanel createPanelCentral() {
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new GridLayout(2,1));
		return panelCentral;
	}

	private JPanel creaPanelPrincipal() {
		
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new BorderLayout());
		//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//panelPrincipal.setPreferredSize(new Dimension((int) screenSize.getWidth() * 9 / 12, (int) screenSize.getHeight() * 7 / 8));
		return panelPrincipal;
	}
	
	private void addBarraEstado(JPanel panelPrincipal)
	{
		this.panelBarraEstado = new PanelBarraEstado("Bienvenido al simulador!", this.controlador);
		//se añade al panel principal
		panelPrincipal.add(this.panelBarraEstado, BorderLayout.PAGE_END);
	}
	
	public void cargaFichero()
	{
		int returnVal = this.fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			File fichero = this.fc.getSelectedFile();
			try 
			{
				String s = leeFichero(fichero);
				this.controlador.reinicia();
				
				InputStream newfile = new FileInputStream(fichero);
				controlador.setFicheroEntrada(newfile);
				this.ficheroActual = fichero;
				this.panelEditorEventos.setTexto(s);
				this.panelEditorEventos.setBorde(this.ficheroActual.getName());
				controlador.cargaEventos(newfile);
				this.panelBarraEstado.setMensaje("Fichero " + fichero.getName() + " de eventos cargado en el editor.");
			}
			catch (IOException e) {
				this.muestraDialogoError("Error durante la lectura del fichero: " + e.getMessage());
			} catch (ErrorDeSimulacion e) {
			}
			
		}
	}
	
	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map,
			List<Evento> eventos, ErrorDeSimulacion e) {
		this.muestraDialogoError(e.getLocalizedMessage());
	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		panelEditorEventos.setBorde("Eventos");
	}

	public void generaInformes() {
		dialogoInformes.mostrar();
	}

	public int getSteps() {
		return toolbar.getSteps();
	}

	public String getTextoEditorEventos() {
		return panelEditorEventos.getTexto();
	}

	public void setMensaje(String string) {
		panelBarraEstado.setMensaje(string);
	}

	public void inserta(String string) {
		panelEditorEventos.inserta(string);
	}

	public void guardaFichero() {

		int returnVal = this.fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			File fichero = this.fc.getSelectedFile();
			try
			{
				OutputStream newfile = new FileOutputStream(fichero);
				newfile.write(panelInformes.getTexto().getBytes());	
				newfile.close();
				
				//this.panelEditorEventos.setTexto(s);
				this.panelBarraEstado.setMensaje("Guardado el informe en " + fichero.getName());
			}
			catch (FileNotFoundException e) {
				this.muestraDialogoError("No se encuentra el fichero: " + e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				this.muestraDialogoError("Error durante el guardado del fichero: " + e.getMessage());
			}
			
		}
	}
	
	public void limpia() {
		panelEditorEventos.setTexto("");
		this.panelBarraEstado.setMensaje("Eventos limpiados.");
	}
	
	public void cargaEvento()
	{
		try
		{
			byte[] contenido = this.getTextoEditorEventos().getBytes();
			controlador.cargaEventos(new ByteArrayInputStream(contenido));
			//this.setMensaje("Eventos cargados al simulador!");
		}
		catch (ErrorDeSimulacion err)
		{
			this.muestraDialogoError(err.getLocalizedMessage());
		}
	}

	public void closeGUI()
	{
		int input = JOptionPane.showConfirmDialog(null,"¿De verdad deseas salir?","Exit",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
		if (input == 0)
			System.exit(0);
	}
	
	public void limpiaInformes()
	{
		panelInformes.setTexto("");
		this.panelBarraEstado.setMensaje("Informes limpiados.");
	}

	public void setOutput(boolean value)
	{
		panelInformes.setOutput(value);
		//this.panelBarraEstado.setMensaje("Salida automatica " + (value ? "activada" : "desactivada") );
	}
	
	public void setAreaInformes(String str)
	{
		this.panelInformes.setTexto(str);
	}
	
	private int getDelay() //Pasaria a ser privado
	{
		return this.toolbar.getDelay();
	}
	
	public void ejecuta()
	{
		ejecutaThread = new Thread()
		{
			public void run()
			{
				try
				{
					int pasos = toolbar.getSteps();
						setEnable(false);
						int delay = getDelay();
						while(!Thread.currentThread().isInterrupted() && pasos > 0)
						{
							try{
								Thread.sleep(delay);
								controlador.ejecuta(1); 
								pasos--;


							} catch (InterruptedException e) {
								Thread.currentThread().interrupt();
							} 
						}
				}
				catch (Exception e) {}
				finally
				{
					setEnable(true);
				}
			}
		};

		ejecutaThread.start();
	}

	private void setEnable(boolean b) {
		toolbar.setEnable(b);
		menubar.setEnable(b);
	}

	public void interruptThread() {
		if (ejecutaThread != null)
		{
			ejecutaThread.interrupt();
			setEnable(true);
		}

	}
}
