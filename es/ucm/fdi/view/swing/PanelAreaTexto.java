package es.ucm.fdi.view.swing;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PanelAreaTexto extends JPanel{
	protected JTextArea areatexto;
	
	public PanelAreaTexto(String titulo,boolean editable){
		this.setLayout(new GridLayout(1,1));
		this.areatexto = new JTextArea(40,30);
		this.areatexto.setEditable(editable);
		this.add(new JScrollPane(areatexto,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		this.setBorde(titulo);
	}
	public void setBorde(String titulo){
		this.setBorder(BorderFactory.createTitledBorder(titulo));
	}
	
	public String getTexto(){
		return areatexto.getText();
	}
	
	public void setTexto(String mensaje){
		areatexto.setText(mensaje);
	}
	
	public void inserta(String valor){
		this.areatexto.insert(valor, this.areatexto.getCaretPosition());
	}
}
