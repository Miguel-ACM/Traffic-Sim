package es.ucm.fdi.view.swing;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;


public class JTextAreaOutputStream extends OutputStream{

	private JTextArea textArea;
	
	public JTextAreaOutputStream(JTextArea textArea)
	{
		this.textArea = textArea;
		textArea.setEditable(false);
	}

	@Override
	public void write(int b) throws IOException {
		textArea.append(String.valueOf((char) b));
	}

}
