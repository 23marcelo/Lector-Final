package local;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.neurotec.licensing.NLicense;

public class Licencias {
	
	protected List<String> requiredLicenses;
	private static final String ADDRESS = "/local";
	
	private static final String PORT = "5000";
	private final Map<String, Boolean> licenses;	
	
	
	public Licencias(){
		licenses = new HashMap<String, Boolean>();	
		requiredLicenses = new ArrayList<String>();
	}
	
	
	public void parsearLicenses() throws IOException, TimeoutException{
				

		/* Se especifican los componentes que requieren licencia y se  
		 * agregan a la lista */
	    /********************** LICENCIAS **************************/
			requiredLicenses.add("Biometrics.FingerMatching");
		/***********************************************************/	
		
		
		
		// Se itera el array para obtener todas las licencias 
		for (String license : requiredLicenses) {
			//System.out.println("Cuando no hay internet se tranca aqui");
			boolean state = NLicense.obtainComponents(ADDRESS, PORT, license);
			
			// Se agrega a la lista; licencia y su estado
			licenses.put(license, state);
			/*
			if (state) {
				System.out.println(license + ": obtained");
			}else {
				System.out.println(license + ": not obtained");
			}
			*/
		}	
	}
	
	public boolean getLicenseState(String license){
		return licenses.get(license);
	}
}
