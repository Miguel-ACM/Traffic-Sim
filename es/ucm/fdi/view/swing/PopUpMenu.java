package es.ucm.fdi.view.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import es.ucm.fdi.model.constructorEventos.ConstructorEventos;
import es.ucm.fdi.model.eventos.ParserEventos;


public class PopUpMenu extends JPopupMenu {

	public PopUpMenu(VentanaPrincipal mainWindow) {
		JMenu plantillas = new JMenu("Nueva plantilla");
		this.setVisible(true);
		this.add(plantillas);
		//aï¿½adir las opciones con sus listeners
		for(ConstructorEventos ce: ParserEventos.getConstructoresEventos()) { 
			JMenuItem mi = new JMenuItem(ce.toString());
			mi.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					mainWindow.inserta(ce.template());
				}
			});
			plantillas.add(mi);
		}

		this.addSeparator();


		JMenuItem bcargar = new JMenuItem("Cargar");
		bcargar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.cargaFichero();
			}
		});


		this.add(bcargar);  

		JMenuItem bguardar = new JMenuItem("Guardar");
		bguardar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.guardaFichero();
			}
		});


		this.add(bguardar); 

		JMenuItem blimpiar = new JMenuItem("Limpiar");
		blimpiar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.limpia();
			}
		});
		
		this.add(blimpiar); 
	}

}