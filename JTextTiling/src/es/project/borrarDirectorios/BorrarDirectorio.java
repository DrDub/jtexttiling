package es.project.borrarDirectorios;

import java.io.File;

import es.project.ficheros.configuracion.ConfigFicheros;

/**
 * <p>Clase que borra el contenido del fichero o directorio que se recibe como par�metro,
 * borrando recursivamente si es necesario, los ficheros y directorios contenidos dentro
 * del directorio base</p>
 * @author Daniel Fern�ndez Aller
 */
public class BorrarDirectorio {
	
	/**
	 * <p>Borra f�sicamente los archivos del usuario (si es que tiene): a partir de la ruta 
	 * base, obtiene la lista de ficheros y directorios y, para cada uno de ellos realiza 
	 * la siguiente operaci�n:
	 * <ul>
	 * 	<li>si es un fichero, lo borra</li>
	 * 	<li>si es un directorio, hace una llamada recursiva en la cual se tomar� como ruta
	 * la ruta de este directorio</li>
	 * </ul>
	 * </p>
	 * @param ruta Ruta base a partir de la cual se encuentran los archivos del usuario
	 * @return Verdadero si los borrados se realizaron con �xito, falso en caso contrario
	 */
	private boolean borrar(String ruta) {
		File inicial = new File(ruta);
		String[] lista = inicial.list();
		
		if (lista != null) {
		
			for(int i = 0; i < lista.length; i++) {
				String rutaNueva = ruta + ConfigFicheros.getSeparador() + lista[i];
				File aux = new File(rutaNueva);
				
				/* si el objeto File es un directorio, tenemos que actuar recursivamente */
				if (aux.isDirectory()) 
					borrar(rutaNueva);
				/* si es un archivo, se borra directamente */ 
				else {
					//TODO deja sin borrar el �ltimo fichero
					System.out.println("nombre: " + aux.getName());
					aux.delete();
				}
			}
		}
		/* finalmente, borramos el directorio que ya est�ra vac�o */
		return inicial.delete();
	}
	
	public boolean borrarFicheros(String ruta) {
		return this.borrar(ruta);
	}
	
	public boolean borrarFicheros(File file) {
		System.out.println("path: " + file.getAbsolutePath());
		return this.borrarFicheros(file.getAbsolutePath());
	}
}
