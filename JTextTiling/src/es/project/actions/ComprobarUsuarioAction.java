package es.project.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import es.project.bd.objetos.Archivo;
import es.project.bd.objetos.Usuario;
import es.project.facade.FacadeBD;
import es.project.forms.UsuarioForm;

/**
 * <p>Comprueba si el usuario que intenta entrar en el servicio ya est� dado de alta</p>
 * @author Daniel Fern�ndez Aller
 *
 */
public class ComprobarUsuarioAction extends Action{

	/**
	 * <p>Procesa la petici�n y devuelve un objeto ActionForward que determina la direcci�n
	 * a seguir dentro de la aplicaci�n: si el resultado de las operaciones es de �xito, avanzamos
	 * hacia una p�gina que muestra las estad�sticas del usuario; si no, iremos a la p�gina
	 * de mensajes, donde se nos avisar� de que el usuario no existe. </p>
	 * <p>Comprueba si el nombre de usuario y el password est�n almacenados en la base de datos, e 
	 * inicializa los atributos de sesi�n necesarios: "usuarioActivo", "usuarioActual" y "listaArchivos".
	 * Si la comprobaci�n da como resultado que el usuario no existe, muestra un mensaje avisando de
	 * ello. </p>
	 */
	public ActionForward execute (
			ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) {
		
		String retorno = "exito";
		
		try {
			ActionMessages listaMensajes = new ActionMessages();
			UsuarioForm formulario = (UsuarioForm)form;
			String nombre = formulario.getNombreUsuario();
			String password = formulario.getPassword();
			Usuario user = new Usuario(nombre, password);
			FacadeBD facadeBD = new FacadeBD();
			
			if (facadeBD.esRoot(user)) {
				request.getSession().setAttribute("usuarioActivo", true);
				request.getSession().setAttribute("usuarioActual", user);
				List<Archivo> listaArchivos = null;
				request.getSession().setAttribute("listaAarchivos", listaArchivos);
				retorno = "root";
			}
			else {
				if (!facadeBD.comprobarUsuario(user)) {
					retorno = "error";
					listaMensajes.add("errores", new ActionMessage("error.nombre.password",nombre));
					listaMensajes.add("errores", new ActionMessage("error.CompletarAlta"));
					request.getSession().setAttribute("usuarioActivo", false);
				}
				
				else {
					request.getSession().setAttribute("usuarioActivo", true);
					request.getSession().setAttribute("usuarioActual", user);
					List<Archivo> listaArchivos = facadeBD.getArchivosPorUsuario(user);
					request.getSession().setAttribute("listaArchivos", listaArchivos);
				}
			}
			
			if (!listaMensajes.isEmpty()) 
				saveMessages(request, listaMensajes);
			
		} catch (Exception e) {
			retorno = "errorDesc";
		}
		
		return mapping.findForward(retorno);
	}
	
}
