package es.ucm.fdi.model.constructorEventos;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.eventos.Evento;

public abstract class ConstructorEventos {
	// cada clase dará los valores correspondientes a estos atributos
	// en la constructora
	protected String etiqueta; // etiqueta de la entrada (“new_road”, etc..)
	protected String[] claves; // campos de la entrada (“time”, “vehicles”, etc.)
	protected String[] valoresPorDefecto;

	ConstructorEventos() {
		this.etiqueta = null;
		this.claves = null;
	}

	public abstract Evento parser(IniSection section);

	protected static String identificadorValido(IniSection seccion, String clave) throws IllegalArgumentException
	{
		String s = seccion.getValue(clave);
		if (!esIdentificadorValido(s))
			throw new IllegalArgumentException("El valor " + s + " para " + clave +
					" no es un ID valido");
		else return s;
	}
	
	// identificadores válidos
	// sólo pueden contener letras, números y subrayados
	private static boolean esIdentificadorValido(String id) 
	{
		return id != null && id.matches("[a-z0-9_]+");
	}
	
	protected static int parseaInt(IniSection seccion, String clave) {
		String v = seccion.getValue(clave);
		if (v == null)
			throw new IllegalArgumentException("Valor inexistente para la clave: " +
					clave);
		else 
			return Integer.parseInt(seccion.getValue(clave));
	}
	
	protected static int parseaInt(IniSection seccion,
		String clave,
		int valorPorDefecto)
	{
			String v = seccion.getValue(clave);
			return (v != null) ? Integer.parseInt(seccion.getValue(clave)) :
				valorPorDefecto;
	}
	
	protected static int parseaIntNoNegativo(IniSection seccion,
		String clave,
		int valorPorDefecto) 
	{
		int i = ConstructorEventos.parseaInt(seccion, clave, valorPorDefecto);
		if (i < 0)
			throw new IllegalArgumentException("El valor " + i + " para " + clave +
					" no es un ID valido");
		else 
			return i;
	}
	
    
	protected static double parseaDoubleNoNegativo(IniSection seccion, String clave) 
	{
		try
		{
			double i = Double.parseDouble(seccion.getValue(clave));
			
			if (i < 0 || i >= 1)
				throw new IllegalArgumentException("El valor " + i + " para " + clave +
						" no es un numero valido");
			return i;
		}
		catch (NumberFormatException e)
		{
			throw new IllegalArgumentException("El valor dado a la clave " + clave +
					" no es un numero valido");
		}
	}          	
	
	protected static long parseaLongNoNegativo(IniSection seccion, String clave) {
		String v = seccion.getValue(clave);
		if (v == null)
			throw new IllegalArgumentException("Valor inexistente para la clave: " +
					clave);
		else 
		{
			long i = Long.parseLong(seccion.getValue(clave));
			if ( i < 0)
			{
				throw new IllegalArgumentException("El valor dado a la clave " + clave +
						" no es un numero valido");
			}
			else return i;
		}
	}
	
	@Override
	public String toString()
	{
		return etiqueta;
	}
	
	public String template()
	{
		String str = "[";
		str += this.etiqueta + "]" + '\n';
		int i = 0;
		for (String s : claves)
		{
			str += s + " = ";
			if (!valoresPorDefecto[i].equals(""))
				str += valoresPorDefecto[i];
			str += '\n';
			i++;
		}
		return str;


	}
	
}
