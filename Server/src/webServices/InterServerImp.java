package webServices;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import com.neurotec.io.NBuffer;

import local.Mapeador;
import local.Licencias;


@WebService(endpointInterface = "webServices.InterServer")
public class InterServerImp implements InterServer {	

	private static String ipServidor; 		/** IP del servidor */
	private static String puerto;			/** Puerto donde corre el Web Services */
	private static String webServicesName;	/** URL del Web Services */
	
	private String directorio; 		/** Directorio donde se almacenan las huellas */
	private String mensaje; 		/** Mensaje con el resultado de las operacion */
	private Licencias licencia;		/** Clase para las Licencias */
	private Mapeador mapeador;		/** Mapeador de las instrucciones */
	
	private ArrayList<String> listaHuellas;		/** Lista de los nombres de archivos */
	File f;
	
	
	public static String getIpServidor() {
		return ipServidor;
	}

	public static void setIpServidor(String ipServidor) {
		InterServerImp.ipServidor = ipServidor;
	}

	public static String getPuerto() {
		return puerto;
	}

	public static void setPuerto(String puerto) {
		InterServerImp.puerto = puerto;
	}

	public static String getWebServicesName() {
		return webServicesName;
	}

	public static void setWebServicesName(String webServicesName) {
		InterServerImp.webServicesName = webServicesName;
	}
	
	
	public InterServerImp(){
		mapeador = new Mapeador();
		licencia = new Licencias();
		listaHuellas = new ArrayList<String>();
	}
	
	public void publicar(String url){
		//getIpServidor();
			
		System.out.println("publicando: "+url);
		Endpoint.publish(url, this);
		 System.out.println("Server Listo!");
		
	}

	public String getDirectorio() {
		return directorio;
	}

	public String getMensaje() {
		return mensaje;
	}

	public ArrayList<String> getListaHuellas() {
		return listaHuellas;
	}

	
	public int obtenerLicencias() {
		
		licencia = new Licencias();		
		try {						
			licencia.parsearLicenses();
			boolean b = licencia.getLicenseState("Biometrics.FingerMatching");
			if(b == true){
				mensaje = mensaje + "\n" + "Las licencias se han obtenido correctamente";							
			}
			return 0;
			
		} catch (IOException e) {
			//e.printStackTrace();
			mensaje = mensaje + "\n" + "No se ha obtenido la licencia";
			mensaje = mensaje + "\n" + "Se ha ejecutado el activador?";
			return -1;
		} catch (TimeoutException e) {
			//e.printStackTrace();
			mensaje = mensaje + "\n" + "No hay conexion para la obtencion de licencias";
			return -1;
		}
	}


	
	public int guardarHuella(String nombre, byte[] huellaBytes) throws Exception {		
		System.out.println("Entra peticion de Guardado...");
		
		// Se carga la huella en la variable de la clase Lector
		this.mapeador.getLector().getSubject().setTemplateBuffer(new NBuffer(huellaBytes));
		
		// Se comprueba calidad de la huella recibida
        if(this.mapeador.getLector().getSubject().getFingers().isEmpty()){
        	mensaje = mensaje + "\n" + "La huella enviada al servidor esta vacia o es de baja calidad";
			throw new Exception(mensaje);
        }
		
		// Se obtiene un tiempo del sistema
		long time = System.nanoTime();		
		
		// Se concatena con el formato: time-nombre
		String nomFinal = Long.toString(time).concat("-").concat(nombre);
		
//		System.out.println(time);
//		System.out.println("Nom final: "+nomFinal);		
		
		// Se define una ruta que incluya el nombre final del archivo
		String ubicacion = directorio.concat(nomFinal);
		
		// Se define la ruta donde se guardara la huella
		this.mapeador.setPathSave(ubicacion);
		
		
		// Se inicia el proceso de guardado
		if(this.mapeador.manejador("guardar")){			
			mensaje = mensaje + "\n" + "Huella creada en: "+this.getDirectorio()
					+ " con el nombre "+ nomFinal;
			return 0;	
		}
		else{
			mensaje = mensaje + "\n" + "Problema con el nombre con "
					+ "que se guarda o la ruta donde se guarda";
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
	
	/** Una vez cargados los nombres de archivos en "listaHuellas" se invoca a este
	 * metodo para comenzar el proceso de identificacion */
	public int identify(ArrayList<String> huellas) {
		int aux;
		mapeador.fingers = huellas;
		mapeador.setPath(directorio);

		mapeador.manejador("identificar");
		
		aux = mapeador.getIdentifyFinger().getPosicion();
		
		// Setea el valor, no se leyo aun desde el lector
		//this.instancia.identifyFinger.posicion = -1;
		return aux;

	}
	
	public int leerConfiguracion(){
		int res;
	    Properties propiedades = new Properties();
	    InputStream entrada = null;
	    try {

	        entrada = new FileInputStream("./configuracion.properties");

	        // cargamos el archivo de propiedades
	        propiedades.load(entrada);

	        // obtenemos las propiedades        
	        String buffer = propiedades.getProperty("directorio");
	        directorio = buffer;
	        //System.out.println(buffer);
	        
	        buffer = propiedades.getProperty("servidor");
	        ipServidor = buffer;
	       // System.out.println(buffer);
	        	        
	        buffer = propiedades.getProperty("puerto");
	        setPuerto(buffer);
	        //System.out.println(buffer);
	        
	        buffer = propiedades.getProperty("ws_name");
	        setWebServicesName(buffer);
	        //System.out.println(buffer);
 	        
	        res = 0;
	        mensaje += "\nLas configuraciones han sido leidas correctamente";

	    } catch (IOException ex) {
	        ex.printStackTrace();
	        mensaje += "\nProblema con la lectura del archivo de configuracion";
	        res = -1;
	    } finally {
	        if (entrada != null) {
	            try {
	                entrada.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	                mensaje += "\nProblema con la lectura del archivo de configuracion. "
	                		+ "Tampoco puede cerrarse";
	                res = -2;
	            }
	        }
	    }
		return res;
	}
	
	public String identificarHuella(byte [] huella) throws Exception {
		System.out.println("Entra peticion de Identificacion...");
		int resultado;
		
		/*  Se carga en la variable subject de la clase Lector, lo recibido
		   	como parametro  */
		this.mapeador.getLector().getSubject().setTemplateBuffer(new NBuffer(huella));
			
		// Se comprueba calidad de la huella recibida
        if(this.mapeador.getLector().getSubject().getFingers().isEmpty()){
        	mensaje = mensaje + "\n" + "La huella enviada al servidor esta vacia o es de baja calidad";
			throw new Exception(mensaje);
        }
		
        
		// Se proceden a cargar los nombres de archivos en la lista
		cargarArchivos();
		
		// Se inicia el proceso de identificacion
		resultado = identify(listaHuellas);
		
		
		/* Se evalua el resultado obtenido */
		if (resultado == -1){
			mensaje = mensaje + "\n" + "Aun no se ha leido huella desde el lector";
			throw new Exception(mensaje);
		}
		if (resultado == -2){			
			if(!f.exists()){
				mensaje = mensaje + "\n" + "Directorio no existe";
				throw new Exception(mensaje);
				//return -2;				
			}
			else{
				mensaje = mensaje + "\n" + "Ninguna huella hace match";
				throw new Exception(mensaje);				
			}
		}		
		
		// Se obtiene el nombre del archivo (huella) que hace match				
		String nomHuellaFile = this.getListaHuellas().get(resultado);
		
		
		//Separar, tomar todo lo que esta delante de '-'
		try {
			int posCaracter = nomHuellaFile.indexOf('-')+1;		
			mensaje = mensaje + "\n" + "Usted es: " + nomHuellaFile.substring(posCaracter);
			return nomHuellaFile.substring(posCaracter);
		} catch (Exception e) {
			// TODO: handle exception
			mensaje = mensaje + "\n" + "Problemas con formato del nombre de la huella";
			throw new Exception(mensaje);
		}		
	}
	
	 public static void main(String[] args) throws Exception {	

		 System.out.println("Iniciando WS Server");
		 InterServerImp servidor = new InterServerImp();
		
		 // Se lee el archivo de configuracion
		 int lc = servidor.leerConfiguracion();			
		 System.out.println("leerConfiguracion:"+lc);
		 
		 // Se define la URL del servicio a publicar
		 String url = "http://"+servidor.getIpServidor()+":"+servidor.getPuerto()+"/"+servidor.getWebServicesName();	
		 //System.out.println("http://"+servidor.getIpServidor()+":"+servidor.getPuerto()+"/"+servidor.getWebServicesName());
		 
		 
		 if(lc ==0){	 
			 int ol = servidor.obtenerLicencias();	 
			 System.out.println("obtenerLicencias:"+ol);
			 
			 if (ol != 0){
				 //System.out.println("\nProblema con Licencias");
			 }
			 else{
				 servidor.publicar(url);
			 }
		 }
		 
		 //System.out.println("\nRegistro en variable Mensaje: \n"+servidor.getMensaje());
		 
	}
}