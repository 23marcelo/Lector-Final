package local;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neurotec.licensing.NLicense;


public final class Licencias{

	public String pathFinger1Verify;
	String subjectVerify;	
	
	ArrayList<String> fingers;	
	String subjectIdentify;
	
	
	protected List<String> requiredLicenses;	
	private static final String ADDRESS = "/local";
	private static final String PORT = "5000";
	private final Map<String, Boolean> licenses;
	
	protected boolean obtained;
	
	public Licencias(){	
		licenses = new HashMap<String, Boolean>();
	}


	public boolean obtenerLicencias() throws IOException, TimeoutException{		
		
		// Se crea un Array para las licencias requeridas
		requiredLicenses = new ArrayList<String>();		

		// Se especifican los componentes que requieren licencia en el array
	    /********************** LICENCIAS **************************/
			requiredLicenses.add("Biometrics.FingerExtraction");
			//requiredLicenses.add("Devices.FingerScanners");
		/***********************************************************/	
		

		//System.out.println("Cuando no hay internet se tranca aqui");
		//boolean state = NLicense.obtainComponents(ADDRESS, PORT, requiredLicenses.get(0));
		
		for (String license : requiredLicenses) {
			//System.out.println("Cuando no hay internet se tranca aqui");
			boolean state = NLicense.obtainComponents(ADDRESS, PORT, license);
			
			// Se agrega a la lista; licencia y su estado
			licenses.put(license, state);
			
			if (state) {
				//System.out.println(license + ": obtained");				
			}else {
				//System.out.println(license + ": not obtained");
				return false;
			}			
		}
		return true;		
	}
	
}
