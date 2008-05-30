package es.project.algoritmo.configuracion;

import java.util.ResourceBundle;

/**
 * <p>Clase que accede al fichero de propiedades que contiene la informaci�n referente a los
 * par�metros de funcionamiento del algoritmo</p>
 * @author Daniel Fern�ndez Aller
 */
public class ConfigAlgoritmo {

	private static ResourceBundle propiedades = ResourceBundle.getBundle("es.project.utilidades.algoritmo");
	
	/**
	 * <p>Accede al atributo</p>
	 * @return Devuelve una cadena con el par�metro "window"
	 */
	public static String getWindow() {
		return propiedades.getString("window");
	}
	
	/**
	 * <p>Accede al atributo</p>
	 * @return Devuelve una cadena con el par�metro "step"
	 */
	public static String getStep() {
		return propiedades.getString("step");
	}
	
	/**
	 * <p>Accede al atributo</p>
	 * @return Devuelve una cadena con la ruta de la lista "stopwords"
	 */
	public static String getStopwordsPath() {
		return propiedades.getString("stopwordsPath");
	}
}
