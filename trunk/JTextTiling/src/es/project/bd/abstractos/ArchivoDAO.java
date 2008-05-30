package es.project.bd.abstractos;

import es.project.bd.objetos.Archivo;

/**
 * <p>Clase abstracta con las declaraciones de los m�todos referidos a las operaciones
 * que se realizan con archivos en la base de datos</p>
 * @author Daniel Fern�ndez Aller
 */
public abstract class ArchivoDAO {
	
	/**
	 * <p>Visualizaci�n por pantalla de todos los archivos de la base de datos.
	 * PARA LAS PRUEBAS</p>
	 */
	public abstract void verArchivos();
	
	/**
	 * <p>N�mero total de archivos contenidos en la base de datos. PARA PRUEBAS</p>
	 * @return N�mero entero con el recuento
	 */
	public abstract int numeroArchivos();
	
	/**
	 * <p>Elimina toda la informaci�n de los archivos de la base de datos</p>
	 * @return Verdadero si la operaci�n fue bien
	 */
	public abstract boolean borrarArchivos();
	
	/**
	 * <p>Elimina un archivo de la base de datos</p>
	 * @param archivo Archivo a eliminar
	 * @return Verdadero si la operaci�n fue bien, falso en caso contrario
	 */
	public abstract boolean borrarArchivo(Archivo archivo);
	
	/**
	 * <p>Inserta un archivo en la base de datos</p>
	 * @param archivo Archivo a insertar
	 * @return Verdadero si la operaci�n fue bien, falso en caso contrario
	 */
	public abstract boolean insertarArchivo(Archivo archivo);
	
	/**
	 * <p>Busca un archivo en la base de datos</p>
	 * @param archivo Archivo a buscar
	 * @return Verdadero si y s�lo si el archivo est� dentro de la base de datos
	 */
	public abstract boolean buscarArchivo(Archivo archivo);
	
	/**
	 * <p>Devuelve el archivo correspondiente a los par�metros especificados</p>
	 * @param nombreArchivo Nombre del archivo 
	 * @param nombrePropietario Nombre del propietario del archivo
 	 * @return Objeto de tipo Archivo
	 */
	public abstract Archivo getArchivo(String nombreArchivo, String nombrePropietario);
	
	/**
	 * <p>Actualiza el valor de la ruta del archivo</p>
	 * @param archivo Archivo del que se va a modificar la ruta
	 * @param valor Nueva ruta
	 * @return Verdadero si se consigui� actualizar la ruta, falso en caso contrario
	 */
	public abstract boolean actualizarRutaArchivo(Archivo archivo, String valor);
	
	/**
	 * <p>Actualiza el valor del nombre del archivo</p>
	 * @param archivo Archivo del que se va a modificar el nombre
	 * @param valor Nuevo nombre
	 * @return Verdadero si se consigui� actualizar el nombre, falso en caso contrario
	 */
	public abstract boolean actualizarNombreArchivo(Archivo archivo, String valor);

}
