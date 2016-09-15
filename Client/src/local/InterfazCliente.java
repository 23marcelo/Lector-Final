package local;

import com.neurotec.lang.NCore;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.TimeoutException;
import com.neurotec.biometrics.NSubject;


public class InterfazCliente {
	
	private Lector lector;			/** Clase que trabaja con el lector directamente */
	private Licencias licencia;
	private boolean primeraLectura;
	private boolean isOpen;			/** Indica que se ha ejecutado el metodo abrir() */
	private String mensaje;			/** Muestra los mensajes que se obtienen al realizar operaciones con el lector */

	
    public InterfazCliente(){   
    	
    	/** Aqui se procede a inicializar el dispositivo (carga de librerias) 
    	 * Esto requiere cierto tiempo 8 segundos aproximadamente */
    	lector = new Lector();
    	
    	licencia = new Licencias();
    	primeraLectura = true;
    	isOpen=false;
    }
    
	public String getMensaje() {
		return mensaje;
	}
	
	public NSubject getSubject(){
		return lector.getSubject();
	}
    
	/** Funcion encargada de obtener las licencias */
	public int abrir() {
		try {
			isOpen = true;
			licencia.obtenerLicencias();
			return 0;
		}
		catch(TimeoutException e){
			this.mensaje = "Tiempo de espera agotado";
			return -1;
		}
		catch (Exception e) {
			// TODO: handle exception
			//System.out.println("Problema con licencias");
			this.mensaje = "No se ha ejecutado el activador";
			return -2;
		}
	}

	/** Funcion para establecer comunicacion con el dispositivo */
	public int eco(){
		try{
			// Se actualiza la lista de lectores disponibles
			this.lector.updateScannerList();				
			
			// Se selecciona el primer lector encontrado, se imprime en consola el mismo
			System.out.println(this.lector.getSelectedScanner());
			
			// Si se encuentra algun lector...
			if(this.lector.getSelectedScanner() != null){
				
				// Se guarda en mensaje la descripcion del lector
				this.mensaje = this.lector.getSelectedScanner().toString();
				return 0;
			}
			else{				
				this.mensaje = "No se ha detectado lector";
				return -1;
			}
		}
		catch(Exception e){
			this.mensaje = "Error interno";
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
				this.mensaje = "No existe lector";
				return -2;
			}
		}

		// Se invoca al metodo para leer la huella
		res = lector.startCapturing();
		
		if (!res){
			this.mensaje = "La huella no ha sido leida";
			return -1;
		}
		
		this.mensaje = "La huella ha sido leida correctamente";
		return 0;
			
	}

	/** Metodo para cerrar el lector y finalizar la aplicacion */
	public int cerrar() {
		if (isOpen){			
			NCore.shutdown();
			return 0;
		}
		else{			
			this.mensaje = "No se ha invocado aun el metodo abrir";
			return -1;
		}
		

	}
	
	public boolean ping(String ip){
		InetAddress ping;
		try {
			ping = InetAddress.getByName(ip);
			if(ping.isReachable(7000)){
				System.out.println(ip+" - responde!");
				return true;
			}else {
				System.out.println(ip+" - no responde!");				
			}
			
		}catch (IOException ex) { System.out.println(ex);}
		return false;
	}
}
