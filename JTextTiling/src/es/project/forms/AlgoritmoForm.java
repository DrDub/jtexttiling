package es.project.forms;

import org.apache.struts.action.ActionForm;

/**
 * <p>Clase Bean utilizada cuando se necesitan pasar los par�metros al algoritmo JTextTiling</p>
 * @author Daniel Fern�ndez Aller
 */
public class AlgoritmoForm extends ActionForm{
	private static final long serialVersionUID = -1;
	
	/**
	 * <p>Nombre del archivo al que se va a aplicar el algoritmo, y valor de los par�metros
	 * window y step</p>
	 */
	private String nombreArchivo, window, step;
	private int n;
	
	/**
	 * <p>Accede al atributo</p>
	 * @return Devuelve el nombre del archivo
	 */
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	
	/**
	 * <p>Asigna un valor al nombre del archivo</p>
	 * @param nombreArchivo Nombre a asignar
	 */
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	
	/**
	 * <p>Accede al atributo</p>
	 * @return Devuelve el valor del par�metro window
	 */
	public String getWindow() {
		return window;
	}
	
	/**
	 * <p>Asigna un valor al par�metro window</p>
	 * @param window Par�metro a asignar
	 */
	public void setWindow(String window) {
		this.window = window;
	}
	
	/**
	 * <p>Accede al atributo</p>
	 * @return Devuelve el valor del par�metro step
	 */
	public String getStep() {
		return step;
	}
	
	/**
	 * <p>Asigna un valor al par�metro step</p>
	 * @param step Par�metro a asignar
	 */
	public void setStep(String step) {
		this.step = step;
	}

	/**
	 * <p>Accede al atributo</p>
	 * @return Devuelve el valor del atributo n, que determina la longitud de los n-gramas
	 */
	public int getN() {
		return n;
	}

	/**
	 * <p>Asigna un valor al atributo n, el cual determina la longitud de los n-gramas</p>
	 * @param n Valor a asignar
	 */
	public void setN(int n) {
		this.n = n;
	}

}
