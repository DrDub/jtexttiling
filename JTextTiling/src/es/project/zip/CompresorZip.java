package es.project.zip;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import es.project.ficheros.configuracion.ConfigFicheros;

/**
 * <p>Esta clase crea un zip a partir de un archivo cuya ruta se pasa como par�metro. S�lo
 * funciona para directorios que contengan �nicamente ficheros simples (no pueden contener
 * otros directorios)</p>
 * @author Daniel Fern�ndez Aller
 */
public class CompresorZip {
	
	private static FileInputStream entrada;
	private static ZipOutputStream zos;
	
	/**
	 * <p>Recibe la ruta del archivo a comprimir y obtiene la lista de sus "hijos".
	 * Recorre esta lista y, para cada uno de los ficheros simples, realiza el 
	 * proceso de compresi�n mediante el m�todo privado "realizarCompresion"</p>
	 * @param rutaFuente Ruta del directorio a comprimir
	 * @param rutaCompresion Ruta donde se va a colocar el archivo comprimido
	 * @throws IOException java.io.IOException
	 */
	public static void comprimirArchivo(String rutaFuente, String rutaCompresion) 
		throws IOException {
		
		File inicial = new File(rutaFuente);
		String lista[] = inicial.list();
		
		if (lista != null) {
			zos = new ZipOutputStream(new FileOutputStream(new File(rutaCompresion)));
		
			for (int i = 0; i < lista.length; i++) {
				String rutaNueva = rutaFuente + ConfigFicheros.getSeparador() + lista[i];
				File aux = new File(rutaNueva);
				realizarCompresion(aux.getName(), rutaNueva, rutaCompresion);
			}
			cerrarConexion();
		}
	}
	
	/**
	 * <p>M�todo que realiza la compresi�n del fichero que recibe como par�metro, lo a�ade al fichero
	 * zip y lo escribe mediante el ZipOutputStream en la direcci�n</p>
	 * @param nombre Nombre del fichero a comprimir
	 * @param rutaFuente Ruta donde se encuentra el archivo a comprimir
	 * @param rutaCompresion Ruta donde se va a colocar el archivo comprimido
	 * @throws IOException java.io.IOException
	 */
	private static void realizarCompresion(String nombre, String rutaFuente, String rutaCompresion) 
		throws IOException {
		
		entrada = new FileInputStream(new File(rutaFuente));
		ZipEntry entry = new ZipEntry(nombre);
		zos.putNextEntry(entry);
			
		BufferedReader br = new BufferedReader(new InputStreamReader(entrada));
			
		while (br.ready()) 
			zos.write(br.read());
			
		br.close();
		entrada.close();
	}
	
	private static void cerrarConexion() throws IOException {
		zos.close();
	}
}
