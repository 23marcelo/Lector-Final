package local;


import com.neurotec.biometrics.NSubject;

public class Prueba {
	
	InterfazServer lector = new InterfazServer();
	String huellaEntrada; 	/** Ruta desde la cual se lee la huella de entrada */ 
	String nombreHuella;	/** Nombre con que se guarda la huella */
	
	public Prueba(){			
	}
	
	public boolean testLicencia(){
		if(lector.obtenerLicencias() == -1){
			System.out.println(lector.getMensaje());
			return false;
		}
		return true;
	}
	public boolean testGuardar(){
		InterfazServer.leerConfiguracion();
										
		if(lector.obtenerLicencias() == -1){
			System.out.println("No se puede obtener las licencias, "
					+ "no obstante se procedera con el test...");
			
		}
		
		
		if(lector.leerArchivo(huellaEntrada) == -1){			
			System.out.println(lector.getMensaje());
			return false;
		}
		
		NSubject huella = new NSubject();
		huella = lector.getMapeador().getLector().getSubject();
		
		
		if (lector.guardarHuella(nombreHuella, huella) == 0){
			System.out.println(lector.getMensaje());
		}
		else{
			System.out.println(lector.getMensaje());
			return false;
		}
		return true;
	}

	
	public boolean testIdentificar(){
		String res;
		InterfazServer.leerConfiguracion();
		
		if(lector.obtenerLicencias() == -1){
			System.out.println(lector.getMensaje());
			return false;
		}
		
		NSubject huella = new NSubject();
		byte [] huellaBytes;
		

		if(lector.leerArchivo(huellaEntrada) == -1){		
			System.out.println(lector.getMensaje());
			return false;
		}
		
		huella = lector.getMapeador().getLector().getSubject();
		//huellaBytes = huella.getTemplateBuffer().toByteArray();
		
		try {
			res = lector.identificarHuella(huella);
			//res = lector.identificarHuella2(huellaBytes);
			System.out.println(lector.getMensaje());
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static void main(String[] args) {		
		Prueba p = new Prueba();		
		
		System.out.println("************** TEST **************");
		
		// Obtener Licencias
//		if(p.testLicencia())
//			System.out.println("TEST DE OBTENCION DE LICENCIA APROBADO");
//		else
//			System.out.println("TEST DE OBTENCION DE LICENCIA NO APROBADO");
		
		
		// Operacion Guardar
//		p.huellaEntrada = "/home/usuario/Escritorio/huellas/aniz_marcelo";
//		p.nombreHuella = "fulano";
//
//		if(p.testGuardar())
//			System.out.println("TEST GUARDAR APROBADO");
//		else
//			System.out.println("TEST GUARDAR NO APROBADO");
	
		
		// Operacion Identificar
//		p.huellaEntrada = "/home/usuario/Escritorio/huellas/aniz_marcelo";
//		
//		if(p.testIdentificar())
//			System.out.println("TEST IDENTIFICAR APROBADO");
//		else
//			System.out.println("TEST IDENTIFICAR NO APROBADO");
	
	
		System.out.println("**********************************");
	}
}