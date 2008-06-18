package es.project.blindLight;

/**
 * <p>Recibe un texto y lo formatea seg�n las siguientes premisas:
 * <ul>
 * 	<li>Los espacios tales como espacios en blanco, tabuladores, saltos de l�nea, etc, se
 * sustituyen por espacios en blanco. Esto es: el documento ser� una �nica l�nea cuyas palabras
 * estar�n separadas por un espacio en blanco.
 *  </li>
 *  <li>Los separadores (s�mbolos de puntuaci�n) se sustituyen por el car�cter '>'
 *  </li>
 * </ul>
 * </p>
 * @author Daniel Fern�ndez Aller, basado en c�digo de Eugenia P�rez, Sa�l Gonz�lez y Miguel Mart�nez
 */
public class FormateadorTexto {
	
	private String texto;
	
	public FormateadorTexto(String texto) {
		this.texto = texto;
	}
	
	public String formatearTexto() {
		String retorno = "";
		
		for (int i = 0; i < texto.length(); i++) {
			retorno += texto.charAt(i);
		}
		
		return retorno;
	}

	/**
	 * <p>Indica si el car�cter recibido es espaciador</p>
	 * @param c Car�cter a comprobar
	 * @return Verdadero si el car�cter es espaciador
	 */
	private boolean esEspacio(int c) {
		return ((c>=7)&&(c<=13)||(c==32));
    }
	
	/**
	 * <p>Indica si el car�cter recibido es separador</p>
	 * @param c Car�cter a comprobar
	 * @return Verdadero si el car�cter es separador
	 */
	private boolean esSeparador(int c) {
		if(esEspacio(c))
            return false;
        else{
           return ((c<=31)||((c>=33)&&(c<=38))||
           ((c>=40)&&(c<=44))||((c>=46)&&(c<=47))||
           ((c>=58)&&(c<=63))||((c>=91)&&(c<=96))||
           ((c>=123)&&(c<=191))||((c==215)||(c==247)));
        }
	}
}
