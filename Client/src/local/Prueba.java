package local;


public class Prueba {
	
	public Prueba(){
			
	}
	
	public boolean testInicializar(){		
		InterfazCliente interfaz = new InterfazCliente();				

		if(interfaz.abrir() == 0){
			if(interfaz.cerrar() == -1){
				System.out.println(interfaz.getMensaje());
				return false;
			}
			else{
				return true;
			}
		}
		
		if(interfaz.cerrar() == -1)
			System.out.println(interfaz.getMensaje());
		
		System.out.println(interfaz.getMensaje());		
		return false;
				
	}
	
	public boolean testComunicacion(){		
		InterfazCliente interfaz = new InterfazCliente();	
		int abrirRes;
		
		abrirRes = interfaz.abrir();
		
		System.out.println(abrirRes);
		
		// Si retorna -1 de lanzo una Excepcion Timeout
		if(abrirRes == -1){
			System.out.println(interfaz.getMensaje());
			System.out.println("No se podra aprobar este Test hasta que no ejecute el activador");
			
			if(interfaz.cerrar() == -1)
				System.out.println(interfaz.getMensaje());			
			
			return false;					
		}
		
		int res = interfaz.eco();
		
		if (res == 0){
			if(interfaz.cerrar() == -1)
				System.out.println(interfaz.getMensaje());
			return true;
		}
		else{
			if(res == -1)
				System.out.println(interfaz.getMensaje());
			else
				//res == -2
				System.out.println(interfaz.getMensaje());
		}
		if(interfaz.cerrar() == -1)
			System.out.println(interfaz.getMensaje());	
		return false;						
		
	}
	
	public boolean testLeer(){
		int leerRes= -1;
		int abrirRes;
		InterfazCliente interfaz = new InterfazCliente();						
		
		abrirRes = interfaz.abrir();
		
		// Si retorna -2 se lanzo una Excepcion general
		if(abrirRes == -2){
			System.out.println(interfaz.getMensaje());
			
			if(interfaz.cerrar() == -1)
				System.out.println(interfaz.getMensaje());
			
			return false;
		}
		
		System.out.println("Posicione su dedo en el lector");
			
		// Se puede utilizar un contador para limitar la cantidad de intentos
		while (leerRes != 0){			
			leerRes = interfaz.leerHuella();
			if(leerRes == -1)
				System.out.println(interfaz.getMensaje()+", vuelva a intentar");
			if(leerRes == -2)
				break;
		}
		
		System.out.println(interfaz.getMensaje());
		
		if(leerRes==0){
			
			if(interfaz.cerrar() == -1)
				System.out.println(interfaz.getMensaje());
			
			return true;
		}
		
		if(interfaz.cerrar() == -1)
			System.out.println(interfaz.getMensaje());
		
		return false;
	}	
	
	
	public static void main(String[] args) {		
		Prueba p = new Prueba();
		
		System.out.println("Recuerde siempre correr el Activador");
		
		
		System.out.println("************** TEST **************");
		
		// Inicializar
//		if(p.testInicializar())
//			System.out.println("TEST APROBADO");
//		else
//			System.out.println("TEST NO APROBADO");
					
		// Comunicacion 
//		if(p.testComunicacion())
//			System.out.println("TEST APROBADO");
//		else
//			System.out.println("TEST NO APROBADO");

		// Lectura
//		if(p.testLeer())
//			System.out.println("TEST APROBADO");
//		else
//			System.out.println("TEST NO APROBADO");
		
				
		System.out.println("**********************************");
	}
}
