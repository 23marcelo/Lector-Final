package webServices;

import com.neurotec.lang.NCore;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import com.neurotec.biometrics.NSubject;

import descargados.InterServer;

import local.Lector;
import local.Licencias;


import java.io.IOException;
import java.net.InetAddress;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;

public class ClienteImp {
	
	public void setIpServidor(String ipServidor) {
		this.ipServidor = ipServidor;
	}

	public void setPingTime(String pingTime) {
		this.pingTime = pingTime;
	}

	public void setPuerto(String puerto) {
		this.puerto = puerto;
	}

	public void setWebServicesUrl(String webServicesUrl) {
		this.webServicesUrl = webServicesUrl;
	}

	private static String ipServidor; 		/** IP del servidor */
	private static String pingTime; 		/** Tiempo de duracion de un ping al servidor */
	private static String puerto;			/** Puerto donde corre el Web Services */
	private static String webServicesUrl;	/** URL del Web Services */
	private Lector lector;			/** Clase que trabaja con el lector directamente */
	private Licencias licencia = new Licencias();
	private boolean primeraLectura;
	private boolean isOpen;			/** Indica que se ha ejecutado el metodo abrir() */
	private String mensaje;			/** Muestra los mensajes que se obtienen al realizar operaciones con el lector */

	
    public ClienteImp(){    	
    	// Aqui se procede a inicializar el dispositivo (carga de librerias)
    	lector = new Lector();
    	primeraLectura = true;
    	isOpen=false;
    }
    
	public String getMensaje() {
		return mensaje;
	}
	
	/** Permite obtener la huella leida desde el lector 
	 * @throws Exception */
	public NSubject getSubject() throws Exception{
		
		if(lector.getSubject() == null){
			this.mensaje = this.mensaje + "\n" + "Aun no se ha leido huella desde el lector";
			throw new Exception("Aun no se ha leido huella desde el lector");			
			}
		
		return lector.getSubject();
	}
    
	public int abrir() {
		try {
			isOpen = true;
			if(!licencia.obtenerLicencias()){
				this.mensaje = this.mensaje + "\n" + "No se pudo obtener Licencias";
				return -3;
			}
			else{
				this.mensaje = this.mensaje + "\n" + "Apertura";
			}
			return 0;
		}
		catch(TimeoutException e){
			this.mensaje = this.mensaje + "\n" + "Tiempo de espera agotado";
			return -1;
		}
		catch (Exception e) {
			// TODO: handle exception
			this.mensaje = this.mensaje + "\n" + "No se ha obtenido Licencia";
			return -2;
		}
	}

	/** Funcion para establecer comunicacion con el dispositivo */
	public int eco(){
		try{
			// Se actualiza la lista de lectores disponibles
			this.lector.updateScannerList();				
			
			// Se selecciona el primer lector encontrado, se imprime en consola el mismo
			this.lector.getSelectedScanner();
			
			// Si se encuentra algun lector...
			if(this.lector.getSelectedScanner() != null){
				//System.out.println("Lector: "+lector.getSelectedScanner());
				this.mensaje = this.mensaje + "\n" + this.lector.getSelectedScanner().toString();
				return 0;
			}
			else{				
				this.mensaje = this.mensaje + "\n" + "No se ha detectado lector";
				return -1;
			}
		}
		catch(Exception e){
			this.mensaje = this.mensaje + "\n" + "Error interno";
			return -2;
		}	
		
	}

	/** Funcion utilizada para leer la huella de un usuario */
	public int leerHuella() {
		boolean res;
		
		if (this.primeraLectura = true) {
			
			// La bandera se cambia para que no vuelva a entrar en este bloque
			this.primeraLectura = false;
			
			// Se actualiza la lista de lectores disponibles
			this.lector.updateScannerList();

			// Se selecciona el primer lector encontrado
			this.lector.getSelectedScanner();						

			// Si no se encuentra ningun lector...
			if(this.lector.getSelectedScanner() == null){
				this.mensaje = this.mensaje + "\n" + "No existe lector";
				return -2;
			}
		}

		// Se invoca al metodo para leer la huella
		res = lector.startCapturing();
		
		if(lector.isBad_object()){
			this.mensaje = this.mensaje + "\n" + "La huella es de baja calidad";
			return -1;
		}
		
		if (!res){
			this.mensaje = this.mensaje + "\n" + "La huella no ha sido leida";
			return -1;
		}
		
		this.mensaje = this.mensaje + "\n" + "La huella ha sido leida correctamente";
		return 0;
			
	}

	/** Funcion para cerrar el lector y finalizar la aplicacion */
	public int cerrar() {
		if (isOpen){			
			NCore.shutdown();
			this.mensaje = this.mensaje + "\n" + "Cierre";
			return 0;
		}
		else{			
			this.mensaje = this.mensaje + "\n" + "No se ha invocado aun el metodo abrir";
			return -1;
		}		
	}		
	
	public boolean ping(String ip){
		InetAddress ping;
		try {
			ping = InetAddress.getByName(ip);
			if(ping.isReachable(Integer.parseInt(pingTime))){
				this.mensaje = this.mensaje + "\n" +ip+" - responde!";
				return true;
			}else {
				this.mensaje = this.mensaje + "\n" +ip+" - no responde!";
			}
			
		}catch (IOException ex) { System.out.println(ex);}
		return false;
	}
	
	
	public boolean servicioActivo(URL url) {			   		

        // Qualified name of the service:
        //   1st arg is the service URI
        //   2nd is the service name published in the WSDL
        QName qname = new QName("http://webServices/", "InterServerImpService");

        // Create, in effect, a factory for the service.
        Service service=null;
        try {
        	 service = Service.create(url, qname);
        	 this.mensaje = this.mensaje + "\n" +"WService Activo!";
        	 return true;
		} catch (WebServiceException e) {
			this.mensaje = this.mensaje + "\n" +"WService no disponible";
			return false;
		}
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
	        
	        String buffer = propiedades.getProperty("servidor");
	        ipServidor = buffer;
	       // System.out.println(buffer);
	        
	        buffer = propiedades.getProperty("ping_time");
	        setPingTime(buffer);
	        //System.out.println(buffer);
	        
	        buffer = propiedades.getProperty("puerto");
	        setPuerto(buffer);
	        //System.out.println(buffer);
	        
	        buffer = propiedades.getProperty("ws_url");
	        setWebServicesUrl(buffer);
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

	public String getIpServidor() {
		return ipServidor;
	}

	public String getPingTime() {
		return pingTime;
	}

	public String getPuerto() {
		return puerto;
	}

	public String getWebServicesUrl() {
		return webServicesUrl;
	}

}
