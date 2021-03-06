package local;

import java.io.IOException;
import java.util.ArrayList;

public class Mapeador{

	private Lector lector;
	private String pathRead;
	private String pathSave;
	private IdentifyFinger identifyFinger;
	private String path;	
	public ArrayList<String> fingers;	
	String subjectIdentify;
	
	
	public IdentifyFinger getIdentifyFinger() {
		return identifyFinger;
	}

	public void setIdentifyFinger(IdentifyFinger identifyFinger) {
		this.identifyFinger = identifyFinger;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	/** Parametros que puede recibir la funcion manejador() */
	public String operaciones[] = {"guardar","leerArchivo","identificar"};
	
	
	public Lector getLector() {
		return lector;
	}

	public void setLector(Lector lector) {
		this.lector = lector;
	}

	public String getPathSave() {
		return this.pathSave;
	}

	public void setPathSave(String pathSave) {
		this.pathSave = pathSave;
	}

	public String getPathRead() {
		return pathRead;
	}

	public void setPathRead(String pathRead) {
		this.pathRead = pathRead;
	}
	

	
	public Mapeador(){			
		lector = new Lector();
		identifyFinger = new IdentifyFinger();
	}



	/** Dependiendo del parametro realiza una u otra operacion */
	
	public boolean manejador(String p) {
		
		boolean res = false;
		
		// Si el parametro es igual a: "guardar"
		if ( p.equals(operaciones[0]) ) {
			try {
				this.lector.saveTemplate(pathSave);
				res = true;
			} catch (IOException e) {
				//e.printStackTrace();
				res=false;
			}
		}

		// Si el parametro es igual a: "leerArchivo"
		if ( p.equals(operaciones[1]) ) {
			//System.out.println("path: "+pathRead);
			try {
				this.lector.readFromFile(pathRead);
				res= true;
			} catch (IOException e) {
				//e.printStackTrace();
				//System.out.println("Error File, metodo leerArchivo");
				res = false;
			}
			
		}

		// Si el parametro es igual a: "identificar"
		if ( p.equals(operaciones[2]) ) {
			try {
				identifyFinger.setNames(fingers);
				//System.out.println(fingers.get(0));
				identifyFinger.setPath(this.path);
				identifyFinger.setSubjectIdentify(subjectIdentify);
				
				if( this.lector.getSubject() != null ) {
					//System.out.println("No es null el sujeto...");
					identifyFinger.setSubject(this.lector.getSubject());
					identifyFinger.identify();
				}
				else{
					res = false;
				}
			} catch (Throwable e) {
				//e.printStackTrace();
			}			
			// Setea el valor, no se leyo aun desde el lector
			//this.enrollFromScanner.subject = null;
		}
		return res;
	}
}