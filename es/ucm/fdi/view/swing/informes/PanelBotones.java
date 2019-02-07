package es.ucm.fdi.view.swing.informes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelBotones extends JPanel{

	private JButton cancelar;
	private JButton generar;
	
	public PanelBotones(DialogoInformes dialogoInformes) {
		cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				dialogoInformes.cancelar();
			}
			
		});
		generar = new JButton("Generar");
		generar.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				dialogoInformes.generar();
			}
			
		});
		this.add(cancelar);
		this.add(generar);
		dialogoInformes.add(this);
	}
	
}
