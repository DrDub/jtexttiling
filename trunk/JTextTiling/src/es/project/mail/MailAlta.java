package es.project.mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;

import es.project.bd.objetos.Usuario;
import es.project.mail.configuracion.ConfigMail;

/**
 * <p>Env�a el mail de confirmaci�n de alta a los usuarios</p>
 * @author Daniel Fern�ndez Aller
 */
public class MailAlta extends Mail{
	
	/**
	 * <p>Crea el mensaje con el texto apropiado y lo env�a al mail del usuario</p>
	 * @param usuario Objeto que representa al usuario que va a recibir el mail
	 * @throws MessagingException Posibles errores en el env�o del mensaje
	 */
	public void enviarMail(Usuario usuario) throws MessagingException{
		this.pasosInicialesMail(usuario);
		this.crearTextoMail(usuario, mensaje, mp);
		Transport.send(mensaje);
	}
	
	/**
	 * <p>Compone el texto del mensaje. El texto contiene el nombre y el password del
	 * usuario y la ruta del enlace que tiene que seguir para completar el alta en
	 * la aplicaci�n</p>
	 * @param usuario Objeto que representa al usuario que va a recibir el mail
	 * @throws MessagingException Posibles errores en el env�o del mensaje
	 */
	protected void crearTextoMail(Usuario usuario, Message mensaje, Multipart mp)
		throws MessagingException {
		
		String cuerpo = ConfigMail.getEncabezado();
		cuerpo += "<p>Nombre de usuario: " + usuario.getNombre() + "</p><p>Password: " + usuario.getPassword() + "</p>";
		cuerpo += "\n" + ConfigMail.getCuerpo() + usuario.getUuid() + ConfigMail.getFinalCuerpo();
		this.crearCuerpo(mensaje, mp, cuerpo);
	}
}
