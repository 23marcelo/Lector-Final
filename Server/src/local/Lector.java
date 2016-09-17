 
package local;

import java.io.IOException;
import com.neurotec.io.NFile;
import com.neurotec.io.NBuffer;
import com.neurotec.biometrics.NSubject;


public class Lector{

	private NSubject subject;		/**  Individuo a identificar, si es null significa 
									    que no hay una huella cargada  */
	
	private NBuffer buffer;  		/** Utilizada para la lectura desde archivo */
	
	
	
	public NSubject getSubject() {
		return subject;
	}

	
	public void setSubject(NSubject subject) {
		this.subject = subject;
	}
	
	
	public Lector() {
		subject = new NSubject();
	}

	
	/** Lee la huella desde un archivo, utilizado para las pruebas */
	public void readFromFile(String path) throws IOException {
		subject = new NSubject();		
		buffer = NFile.readAllBytes(path);
		subject.setTemplateBuffer(buffer);
		//System.out.println("Contenido de Subject: "+this.subject.toString());
	}
	
	
	/** Guarda una huella con la ruta recibida por parametro */
	public void saveTemplate(String path) throws IOException {
		if(this.subject != null){
//			System.out.println("Contenido de Subject: "+this.subject.toString());
			NFile.writeAllBytes(path, subject.getTemplateBuffer());
		}
		else
			System.out.println("No hay ninguna huella leida");
	}
}
