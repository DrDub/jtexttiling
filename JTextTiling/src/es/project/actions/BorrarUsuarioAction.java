package es.project.actions;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import es.project.bd.objetos.Usuario;
import es.project.borrarDirectorios.BorrarDirectorio;
import es.project.facade.FacadeBD;
import es.project.ficheros.configuracion.ConfigFicheros;
import es.project.forms.ListaNombresUsuarioForm;

/**
 * <p>Borra el/los usuario/s elegidos: los borra de la base de datos y elimina sus archivos.
 * S�lo disponible para el usuario administrador</p>
 * @author Daniel Fern�ndez Aller
 */
public class BorrarUsuarioAction extends Action{
	
	/**
	 * <p>Procesa la petici�n y devuelve un objeto ActionForward que determina la direcci�n
	 * a seguir dentro de la aplicaci�n: en el caso de este Action, siempre avanzamos hacia
	 * una p�gina que muestra un mensaje de error o de informaci�n, seg�n el resultado de la
	 * ejecuci�n.</p>
	 * <p>Borrar un usuario comprende las siguientes tareas: eliminarlo de la base de datos,
	 * eliminar sus archivos de la base de datos y eliminar sus archivos f�sicamente. Estas
	 * tareas se desarrollan en el m�todo privado "borrarUsuarios"</p>
	 */
	public ActionForward execute (
			ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) {
		
		String retorno = "exito";
		
		try {
			ActionMessages listaMensajes = new ActionMessages();
			ListaNombresUsuarioForm formulario = (ListaNombresUsuarioForm)form;
			
			if (borrarUsuarios(formulario.getNombreUsuarios(), listaMensajes))
				listaMensajes.add("mensajes",new ActionMessage("mensaje.BorradoUsuarios"));
			
			if (!listaMensajes.isEmpty())
				saveMessages(request, listaMensajes);
			
		} catch (Exception e) {
			e.printStackTrace();
			retorno = "errorDesc";
		}
		
		return mapping.findForward(retorno);
	}
	
	/**
	 * <p>Recorre el array que contiene los nombres de los usuarios a borrar y, para cada
	 * uno, lo borra de la base de datos, con lo cual tambi�n se eliminan sus archivos 
	 * mediante el borrado en cascada. Si el proceso fue bien, borra sus archivos llamando
	 * al m�todo privado "borrarArchivosUsuario".</p>
	 * @param nombreUsuarios Lista de usuarios a eliminar
	 * @return Verdadero si las operaciones terminaron bien, falso en caso contrario
	 */
	private boolean borrarUsuarios(String[] nombreUsuarios, ActionMessages lista) {
		FacadeBD facadeBD = new FacadeBD();
		boolean procesoCorrecto = true;
		
		for (int i = 0; i < nombreUsuarios.length; i++) {
			boolean borrado = facadeBD.borrarUsuario(new Usuario(nombreUsuarios[i]));
			
			if (!borrado) {
				lista.add("errores", new ActionMessage("error.BorrandoUsuario",nombreUsuarios[i]));
				procesoCorrecto = false;
			} else {
				if (borrarArchivosUsuario(nombreUsuarios[i]))
					lista.add("mensajes", new ActionMessage("mensaje.BorradoFicherosUsuario"));
				else lista.add("errores", new ActionMessage("error.BorradoFicherosUsuario"));
			}
		}
		return procesoCorrecto;
	}
	
	/**
	 * <p>Compone la ruta base donde se encuentran los ficheros a borrar del usuario. Una
	 * vez hecho, llama al m�todo privado "borrarFicheros", que se encarga del borrado
	 * f�sico.</p>
	 * @param nombreUsuario Nombre del usuario del cual se van a borrar sus archivos
	 * @return Verdadero si todo fue bien, falso en caso contrario
	 */
	private boolean borrarArchivosUsuario(String nombreUsuario) {
		String ruta = ConfigFicheros.getRutaBase() + nombreUsuario;
		BorrarDirectorio bd = new BorrarDirectorio();
		return bd.borrarFicheros(ruta);
	}
}
