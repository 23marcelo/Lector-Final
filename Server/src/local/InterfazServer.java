package local;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeoutException;
import com.neurotec.biometrics.NSubject;


public class InterfazServer{

	private static String directorio;	/** Directorio donde se almacenan las huellas */
	private static String mensaje; 		/** Mensaje con el resultado de las operacion */
	private Licencias licencia = null;	/** Clase para las Licencias */
	private Mapeador mapeador;			/** Mapeador de las instrucciones */
	
	private ArrayList<String> listaHuellas;		// Lista de los nombres de archivos
	File f;
	
	public Mapeador getMapeador() {
		return mapeador;
	}

	public void setMapeador(Mapeador mapeador) {
		this.mapeador = mapeador;
	}

	public Licencias getLicencia() {
		return licencia;
	}

	public void setLicencia(Licencias licencia) {
		this.licencia = licencia;
	}

	public String getMensaje() {
		return mensaje;
	}

	public String getDirectorio() {
		return directorio;
	}
	
	public ArrayList<String> getListaHuellas() {
		return listaHuellas;
	}

	
	public int obtenerLicencias() {
		
		licencia = new Licencias();		
		try {			
			
			licencia.parsearLicenses();
			boolean b = licencia.getLicenseState("Biometrics.FingerMatching");
			if(b == true)
				return 0;
			else
				return -1;
			
		} catch (IOException e) {
			//e.printStackTrace();
			mensaje = "No se ha ejecutado el activador";
			return -1;
		} catch (TimeoutException e) {
			//e.printStackTrace();
			mensaje = "No hay conexion para la obtencion de licencias";
			return -1;
		}
	}

	/** Carga los nombres de los archivos (huellas) disponibles del 'directorio' */
	public void cargarArchivos() {
		
		f = new File(directorio);
		
		// Si el directorio existe...
		if (f.exists()) {
			
			// System.out.println("Huellas disponibles:\n");
			File[] ficheros = f.listFiles();
			
			// Se cargan los nombres en la lista "listaHuellas"
			for (int x = 0; x < ficheros.length; x++) {									
				//System.out.println(ficheros[x].getName());
				listaHuellas.add(ficheros[x].getName());				
			}
		}
	}

	public int guardarHuella(String nombre, NSubject huella) {
		
		// Se carga la huella en la variable de la clase Lector
		this.mapeador.getLector().setSubject(huella);
		
		// Se obtiene un tiempo del sistema
		long time = System.nanoTime();
		
		// Se concatena con el formato: time-nombre
		String nomFinal = Long.toString(time).concat("-").concat(nombre);
		
		
		//System.out.println(time);
		//System.out.println("Nom final: "+nomFinal);
		
		
		// Se define una ruta que incluya el nombre final del archivo
		String ubicacion = directorio.concat(nomFinal);
		
		// Se define la ruta donde se guardara la huella
		this.mapeador.setPathSave(ubicacion);
	
		// Se inicia el proceso de guardado
		if(this.mapeador.manejador("guardar")){			
			mensaje = "Huella creada en: "+this.getDirectorio()
					+ " con el nombre "+ nomFinal;
			return 0;	
		}
		else{
			mensaje = "Problema con el nombre con "
					+ "que se guarda o la ruta donde se guarda";
			return -1;
		}		
	}

	public int leerArchivo(String path){
		
		// Se define la ruta de donde se leera la huella
		mapeador = new Mapeador();
		mapeador.setPathRead(path);
		
		if (mapeador.manejador("leerArchivo")){
			mensaje = "La huella se leyo correctamente";
			return 0;
		}
		mensaje = "Problema con la lectura de archivo. "
				+ "La ruta o el nombre de la huella de entrada es invalido";
		return -1;
	}
	

	public String identificarHuella(NSubject huella) throws Exception {
		int aux2;
		this.mapeador.getLector().setSubject(huella);
		listaHuellas = new ArrayList<String>();
		// Cuando envia caso 5, debe hacer algo adicional
		cargarArchivos();
		aux2 = identify(listaHuellas);
		if (aux2 == -1){
			mensaje = "Aun no se ha leido huella desde el lector";
			throw new Exception(mensaje);
		}
		if (aux2 == -2){			
			if(!f.exists()){
				mensaje = "Directorio no existe";
				throw new Exception(mensaje);
				//return -2;				
			}
			else{
				mensaje = "Ninguna huella hace match";
				throw new Exception(mensaje);				
			}
		}		
		
		//Separar, tomar todo lo que esta delante de '-'
				
		String nomHuellaFile = this.getListaHuellas().get(aux2);
		
		try {
			int posCaracter = nomHuellaFile.indexOf('-')+1;		
			mensaje = "Usted es: " + nomHuellaFile.substring(posCaracter);
			return nomHuellaFile.substring(posCaracter);
		} catch (Exception e) {
			// TODO: handle exception
			mensaje = "Problemas con formato del nombre de la huella";
			throw new Exception(mensaje);
		}		
	}
	
	

	public int identify(ArrayList<String> huellas) {
		int aux;
		mapeador.fingers = huellas;
		mapeador.setPath(directorio);;

		mapeador.manejador("identificar");
		aux = mapeador.getIdentifyFinger().getPosicion();
		// Setea el valor, no se leyo aun desde el lector
		//this.instancia.identifyFinger.posicion = -1;
		return aux;

	}
	
	public static int leerConfiguracion(){
		int res;
	    Properties propiedades = new Properties();
	    InputStream entrada = null;
	    try {
	        entrada = new FileInputStream("configuracion.properties");

	        // cargamos el archivo de propiedades
	        propiedades.load(entrada);

	        // obtenemos las propiedades y las imprimimos	        
	        String buffer = propiedades.getProperty("directorio");
	        directorio = buffer;
	        System.out.println(buffer);
 	        
	        res = 0;
	        mensaje = "Las configuraciones han sido leidas correctamente";

	    } catch (IOException ex) {
	        ex.printStackTrace();
	        mensaje = "Problema con la lectura del archivo de configuracion";
	        res = -1;
	    } finally {
	        if (entrada != null) {
	            try {
	                entrada.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	                mensaje = "Problema con la lectura del archivo de configuracion. "
	                		+ "Tampoco puede cerrarse";
	                res = -2;
	            }
	        }
	    }
		return res;
	}
}
