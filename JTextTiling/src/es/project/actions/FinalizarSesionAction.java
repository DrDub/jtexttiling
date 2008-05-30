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
import es.project.facade.FacadeBD;

/**
 * <p>Lleva a cabo las operaciones necesarias para cerrar la sesi�n de un usuario</p>
 * @author Daniel Fern�ndez Aller
 */
public class FinalizarSesionAction extends Action{
	
	/**
	 * <p>Procesa la petici�n y devuelve un objeto ActionForward que determina la direcci�n
	 * a seguir dentro de la aplicaci�n: en el caso de este Action, siempre avanzamos hacia
	 * una p�gina que muestra un mensaje de error o de informaci�n, seg�n el resultado de la
	 * ejecuci�n.</p>
	 * <p>Reinicia los atributos de sesi�n a su estado inicial: "usuarioActual" y "listaArchivos"
	 * a null y "usuarioActivo" a falso. Si todo va bien, nos lleva de vuelta al �ndice. Si hubo
	 * alg�n problema, nos muestra un mensaje por pantalla.</p>
	 */
	public ActionForward execute(
			ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) {
		
		String retorno = "exito";
		
		try {
			ActionMessages listaMensajes = new ActionMessages();
			
			if ((Boolean)request.getSession().getAttribute("usuarioActivo")) {
				FacadeBD facadeBD = new FacadeBD();
				Usuario user = (Usuario)request.getSession().getAttribute("usuarioActual");
			
				facadeBD.actualizarUltimoLogin(user);
			
				try {
					request.getSession().setAttribute("usuarioActual", null);
					request.getSession().setAttribute("usuarioActivo", false);
					request.getSession().setAttribute("listaArchivos", null);
				} catch (Exception e) {
					listaMensajes.add("errores", new ActionMessage("error.finalizarSesion",
						e.getMessage()));
					retorno = "error";
				}
			} else {
				listaMensajes.add("errores", new ActionMessage("error.NoHayUsuario"));
				request.getSession().setAttribute("botonSalir",false);
				retorno = "error";
			}
			
			if (!listaMensajes.isEmpty())
				saveMessages(request, listaMensajes);
			
		} catch (NullPointerException e) {
			retorno = "errorDesc";
			/* para cubrirse las espaldas */
			request.getSession().setAttribute("usuarioActual", null);
			request.getSession().setAttribute("usuarioActivo", false);
			request.getSession().setAttribute("listaArchivos", null);
		}
		
		return mapping.findForward(retorno);
	}
}
