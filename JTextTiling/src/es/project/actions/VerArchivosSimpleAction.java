package es.project.actions;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import es.project.bd.objetos.Usuario;
import es.project.ficheros.configuracion.ConfigFicheros;
import es.project.forms.ArchivoForm;

/**
 * <p>Abre el editor que venga indicado en el fichero de propiedades</p>
 * @author Daniel Fern�ndez Aller
 */
public class VerArchivosSimpleAction extends Action{
	
	/**
	 * <p>Procesa la petici�n y devuelve un objeto ActionForward que determina la direcci�n
	 * a seguir dentro de la aplicaci�n: si todo fue bien, nos deja en la p�gina que est�bamos. Si
	 * ocurri� alg�n error, muestra una p�gina con el mensaje de error.</p>
	 * <p>Abre el editor especificado en el fichero de propiedades. Utiliza el m�todo "exec" de la
	 * clase "Runtime". El segundo par�metro es la ruta del fichero a abrir.</p>
	 */
	public ActionForward execute(
			ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) {
		
		ActionMessages listaMensajes = new ActionMessages();
		String retorno = "exito";
		String separador = ConfigFicheros.getSeparador();
		
		if ((Boolean)request.getSession().getAttribute("usuarioActivo")) {
			ArchivoForm formulario = (ArchivoForm)form;
			Runtime rt = Runtime.getRuntime();
			Usuario usuario = (Usuario)request.getSession().getAttribute("usuarioActual");
		
			String cmd[] = new String[2];
			cmd[0] = ConfigFicheros.getEjecutablePrograma();
			cmd[1] = ConfigFicheros.getRutaBase() + usuario.getNombre() + separador + formulario.getNombreArchivo();

			try {
				rt.exec(cmd);
			} catch (IOException ioe) {
				retorno = "error";
				listaMensajes.add("errores", new ActionMessage("error.visualizandoFichero",
					ioe.getMessage()));
			}
			
		} else {
			listaMensajes.add("errores", new ActionMessage("error.NoHayUsuario"));
			request.getSession().setAttribute("botonSalir",false);
			retorno = "error";
		}
		
		if (!listaMensajes.isEmpty())
			saveMessages(request, listaMensajes);
		
		return mapping.findForward(retorno);
	}
}
