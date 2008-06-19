package es.project.actions;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import uk.ac.man.cs.choif.nlp.seg.linear.texttile.TextTiling;

import es.project.algoritmo.configuracion.ConfigAlgoritmo;
import es.project.bd.objetos.Usuario;
import es.project.blindLight.FormateadorTexto;
import es.project.blindLight.ListaNGramas;
import es.project.blindLight.NGrama;
import es.project.blindLight.NGramaException;
import es.project.blindLight.TextoANGrama;
import es.project.ficheros.configuracion.ConfigFicheros;
import es.project.forms.AlgoritmoForm;
import es.project.utilidades.ArchivoATexto;

/**
 * <p>Aplica el algoritmo JTextTiling a los archivos elegidos desde la p�gina jsp.</p>
 * @author Daniel Fern�ndez Aller
 */
public class AplicarAlgoritmoAction extends Action{
	
	private ActionMessages listaMensajes;
	private int n;
	
	/**
	 * <p>Procesa la petici�n y devuelve un objeto ActionForward que determina la direcci�n
	 * a seguir dentro de la aplicaci�n: en el caso de este Action siempre vamos a la p�gina
	 * de mensajes que nos informa de lo ocurrido.</p>
	 * <p>Aplica el algoritmo JTextTiling al archivo elegido desde la p�gina, seg�n los par�metros
	 * introducidos ("window" y "step"), y utilizando una lista dada de "stopwords". El resultado
	 * se recoge en un conjunto de archivoa que se generan autom�ticamente dentro del espacio del 
	 * usuario</p>
	 * <p>Para asegurar el funcionamiento correcto del algoritmo, se comprueba la extensi�n del
	 * al que se quiere aplicar.</p>
	 */
	public ActionForward execute (ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) {
		
		String retorno = "exito";
		listaMensajes = new ActionMessages();
		AlgoritmoForm formulario = (AlgoritmoForm)form;
		String nombre = formulario.getNombreArchivo();
		String window = formulario.getWindow();
		String step = formulario.getStep();
		this.n = formulario.getN();
		
		if (!(Boolean)request.getSession().getAttribute("usuarioActivo")) {
			retorno = "error";
			listaMensajes.add("errores", new ActionMessage("error.NoHayUsuario"));
			request.getSession().setAttribute("botonSalir",false);
			
		}
		if (!this.extensionPermitida(this.obtenerExtension(nombre))) {
			retorno = "error";
			listaMensajes.add("errores", new ActionMessage("error.ExtensionArchivo"));
			request.getSession().setAttribute("botonSalir",false);
		}
		else {
			Usuario user = (Usuario)request.getSession().getAttribute("usuarioActual");
			this.aplicarAlgoritmo(nombre, window, step, user.getNombre());
		}
		
		if (!listaMensajes.isEmpty())
			saveMessages(request, listaMensajes);
		
		return mapping.findForward(retorno);
	}
	
	/**
	 * <p>Obtiene los valores de los par�metros necesarios para la ejecuci�n del algoritmo:
	 * o bien del formulario o del fichero de un propiedades. Una vez obtenidos todos los
	 * datos, realiza la llamada al m�todo main del algoritmo, que se encarga de aplicar el
	 * algoritmo al fichero especificado, guardar el resultado en la ruta indicada e imprimir
	 * las especificaciones de los posibles problemas ocurridos.</p>
	 * @param nombreArchivo Nombre del archivo al que queremos aplicar el algoritmo
	 * @param nombreUsuario Nombre del usuario propietario del archivo al que vamos a aplicar
	 * el algoritmo
	 */
	private void aplicarAlgoritmo(String nombreArchivo, String window, String step, String nombreUsuario) {
		String separador = ConfigFicheros.getSeparador();
		String w = (window .compareTo("") != 0)?(window):(ConfigAlgoritmo.getWindow());
		String s = (step.compareTo("") != 0)?(step):(ConfigAlgoritmo.getStep());
		String stopwords = ConfigAlgoritmo.getStopwordsPath();
		String rutaArchivo = ConfigFicheros.getRutaBase() + nombreUsuario + separador + nombreArchivo;
		String directorioSalida = ConfigFicheros.getRutaBase() + nombreUsuario + separador + nombreArchivo + "_JTT";
		
		String args[] = new String[]{w, s, stopwords, directorioSalida, nombreUsuario};
		TextTiling.setNombreArchivo(nombreArchivo);
		TextTiling.setRutaArchivo(rutaArchivo);
		TextTiling.main(args);
		listaMensajes.add("mensajes", new ActionMessage("mensaje.VacioTextTiling",TextTiling.getMensaje()));
		TextTiling.vaciarMensaje();
	}
	
	/**
	 * <p>Devuelve la extensi�n del archivo que recibe como par�metro mediante el m�todo java.lang.String.split</p>
	 * @param cadena Cadena con el nombre del archivo del que queremos obtener su extensi�n 
	 * @return Cadena con la extensi�n del archivo
	 */
	private String obtenerExtension(String cadena) {
		String extension[] = cadena.split("\\.");
 		return extension[1];
	}
	
	/**
	 * <p>Comprueba si la extensi�n del archivo es una de las extensiones v�lidas para el algoritmo. Para
	 * realizar esta comprobaci�n, se accede a un conjunto, el cual se obtiene del fichero de propiedades.</p>
	 * @param extension Extensi�n a comprobar
	 * @return Verdadero si la extensi�n es v�lida (si est� dentro del conjunto)
	 */
	private boolean extensionPermitida(String extension) {
		Set<String> extensiones = ConfigAlgoritmo.getExtensiones();
		return extensiones.contains(extension);
	}
}
