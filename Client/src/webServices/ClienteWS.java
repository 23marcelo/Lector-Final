package webServices;

import javax.xml.ws.Service;
import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceException;

import java.net.URL;
import java.net.MalformedURLException;
import com.neurotec.biometrics.NSubject;

import descargados.InterServer;


public class ClienteWS {
	
	public static void main(String[] args) {			
		
		// Instancia de interfaz implementada, cliente
        ClienteImp clienteLector = new ClienteImp();
        
        
        // Se lee del archivo de configuracion
        System.out.println("leerConfiguracion(): "+clienteLector.leerConfiguracion());
        
        
        // Se prueba conexion con el servidor        
        System.out.println("ping(): "+clienteLector.ping(clienteLector.getIpServidor()));
                
        
		System.out.println("Iniciando WS Client...");		
		
        URL url=null;
		try {
			url = new URL("http://"+clienteLector.getIpServidor()+":"+clienteLector.getPuerto()+"/"+clienteLector.getWebServicesUrl());
		} catch (MalformedURLException e) {
			//e.printStackTrace();
			System.out.println("http://"+clienteLector.getIpServidor()+":"+clienteLector.getPuerto()+"/"+clienteLector.getWebServicesUrl());
			System.out.println("Problemas con la URL");
		}		        
        
		
		// Se prueba si hay comunicacion con el lector        
        System.out.println("echo(): "+clienteLector.eco());
               
        // Se verifica si el web services esta activo                        
        System.out.println("servicioActivo(): "+clienteLector.servicioActivo(url));
        
                	
        // Qualified name of the service:
        //   1st arg is the service URI
        //   2nd is the service name published in the WSDL
        QName qname = new QName("http://webServices/", "InterServerImpService");

        
        // Create, in effect, a factory for the service.
        Service service=null;
        service = Service.create(url, qname);
        
		
        // Extract the endpoint interface, the service "port".
        InterServer h = service.getPort(InterServer.class);   
        
        
        // Se abre el dispositivo para su uso (comprobacion licencias)
        System.out.println("abrir(): "+clienteLector.abrir());
        
        // Se procede a la lectura de la huella
        System.out.println("Ingrese su huella");
        System.out.println(clienteLector.leerHuella());        
       

        
        // Se obtiene la huella leida desde el lector
        NSubject huella = null;
		try {
			huella = clienteLector.getSubject();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}   
        
         
        // Codigo para guardar una huella
//        try {
//			h.guardarHuella("fulano", huella.getTemplateBuffer().toByteArray());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
                        
		
       // Codigo para realizar una identificacion
      try{
			System.out.println(h.identificarHuella(huella.getTemplateBuffer().toByteArray()));
      } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
      }
                    
        System.out.println(clienteLector.cerrar());
        
        System.out.println("\nResgistro variable Mensaje: \n"+clienteLector.getMensaje());

	}
}
