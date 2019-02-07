package es.ucm.fdi.view.swing.informes;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class InformationPanel extends JPanel{
	
	JLabel label;
	
	public InformationPanel()
	{
		label = new JLabel("<html><center>Selecciona objetos para generar informes.<br/>Usa 'c' para deseleccionar todos.<br/>Usa Ctrl+A para seleccionar todos.<center></html>"
				,SwingConstants.LEFT);
		this.add(label);
	}

	
}
