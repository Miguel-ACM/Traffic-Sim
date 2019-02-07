package es.ucm.fdi.view.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import es.ucm.fdi.control.Controlador;

public class BarraMenu extends JMenuBar {
	
	private JMenu menuFicheros;
	private JMenu menuReport;
	
	private JMenuItem ejecuta;
	private JMenuItem pausa;
	private JMenuItem reinicia;
	
	
	
	public BarraMenu(VentanaPrincipal mainWindow, Controlador controlador)
	{
		super();
		//Ficheros
		menuFicheros = new JMenu("Ficheros");
		this.add(menuFicheros);
		this.creaMenuFicheros(menuFicheros, mainWindow);
		
		//Simulador
		JMenu menuSimulador = new JMenu("Simulador");
		this.add(menuSimulador);
		this.creaMenuSimulador(menuSimulador, controlador, mainWindow);
		
		//Informes
		menuReport = new JMenu("Informes");
		this.add(menuReport);
		this.creaMenuInformes(menuReport, mainWindow);
		
	}
	
	private void creaMenuFicheros(JMenu menu, VentanaPrincipal mainWindow)
	{
		JMenuItem cargar = new JMenuItem("Carga Fichero");
		cargar.setMnemonic(KeyEvent.VK_C);
		cargar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.ALT_MASK));
		
		cargar.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {
						mainWindow.cargaFichero();
					}
				});
		
		JMenuItem salvar = new JMenuItem("Guardar Fichero");
		salvar.setMnemonic(KeyEvent.VK_G);
		salvar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,ActionEvent.ALT_MASK));
		salvar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.guardaFichero();
			}
		});
		
		JMenuItem cargarEventos = new JMenuItem("Carga Eventos");
		cargarEventos.setMnemonic(KeyEvent.VK_E);
		cargarEventos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,ActionEvent.ALT_MASK));
		cargarEventos.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.cargaEvento();
			}
		});
		JMenuItem salir = new JMenuItem("Salir");
		salir.setMnemonic(KeyEvent.VK_S);
		salir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.ALT_MASK));
		salir.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.closeGUI();
			}
		});
		
		
		menu.add(cargar);
		menu.add(salvar);
		menu.addSeparator();
		menu.add(cargarEventos);
		menu.addSeparator();
		menu.add(salir);
	}
	
	private void creaMenuSimulador(JMenu menuSimulador, Controlador controlador, VentanaPrincipal mainWindow)
	{
		ejecuta = new JMenuItem("Ejecuta");
		ejecuta.setMnemonic(KeyEvent.VK_J); 
		ejecuta.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J,ActionEvent.ALT_MASK)); //?¿
		ejecuta.addActionListener(new ActionListener() 
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						mainWindow.ejecuta();
					}
			
				});
		pausa = new JMenuItem("Pausa");
		pausa.setMnemonic(KeyEvent.VK_P); 
		pausa.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.ALT_MASK)); //?¿
		pausa.addActionListener(new ActionListener() 
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						mainWindow.interruptThread();
					}
			
				});
		

		reinicia = new JMenuItem("Reinicia");
		reinicia.setMnemonic(KeyEvent.VK_R);
		reinicia.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,ActionEvent.ALT_MASK));
		reinicia.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.reinicia();
			}
	
		});
		menuSimulador.add(ejecuta);
		menuSimulador.add(pausa);
		menuSimulador.addSeparator();
		menuSimulador.add(reinicia);
		
		
	}
	
	private void creaMenuInformes(JMenu menuReport, VentanaPrincipal mainWindow)
	{
		JMenuItem generaInformes = new JMenuItem("Generar");
		generaInformes.setMnemonic(KeyEvent.VK_N); 
		generaInformes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.ALT_MASK)); 
		generaInformes.addActionListener(new ActionListener() 
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						mainWindow.generaInformes();//Opcional
					}
			
				});

		JMenuItem limpiaAreaInformes = new JMenuItem("Limpiar");
		limpiaAreaInformes.setMnemonic(KeyEvent.VK_L); 
		limpiaAreaInformes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,ActionEvent.ALT_MASK)); 
		limpiaAreaInformes.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.limpiaInformes();
			}
	
		});

		
		menuReport.add(generaInformes);
		menuReport.add(limpiaAreaInformes);

	}
	
	public void setEnable(boolean isEnabled)
	{
		menuFicheros.setEnabled(isEnabled);
		menuReport.setEnabled(isEnabled);
		ejecuta.setEnabled(isEnabled);
		reinicia.setEnabled(isEnabled);
	}

}
