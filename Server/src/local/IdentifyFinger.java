
package local;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;

import com.neurotec.biometrics.NBiometricOperation;
import com.neurotec.biometrics.NBiometricStatus;
import com.neurotec.biometrics.NBiometricTask;
import com.neurotec.biometrics.NBiometricType;
import com.neurotec.biometrics.NFMatchingDetails;
import com.neurotec.biometrics.NMatchingDetails;
import com.neurotec.biometrics.NMatchingResult;
import com.neurotec.biometrics.NSubject;
import com.neurotec.biometrics.client.NBiometricClient;
import com.neurotec.io.NFile;


public final class IdentifyFinger {
		
	private ArrayList<String> names;
	private String subjectIdentify;
	private String path;
	private NSubject subject;
	private int posicion=-1;

	
	public String getSubjectIdentify() {
		return subjectIdentify;
	}

	public void setSubjectIdentify(String subjectIdentify) {
		this.subjectIdentify = subjectIdentify;
	}

	public NSubject getSubject() {
		return subject;
	}

	public void setSubject(NSubject subject) {
		this.subject = subject;
	}

	public ArrayList<String> getNames() {
		return names;
	}

	public void setNames(ArrayList<String> names) {
		this.names = names;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	public void identify() throws Throwable{
		
		NBiometricClient biometricClient = null;
		NBiometricTask enrollTask = null;
		
		biometricClient = new NBiometricClient();

		enrollTask = biometricClient.createTask(EnumSet.of(NBiometricOperation.ENROLL), null);

		for (int i = 0; i < names.size(); i++) {
			enrollTask.getSubjects().add(createSubject(path.concat(names.get(i)), String.format("%d", i)));
		}

		biometricClient.performTask(enrollTask);
		NBiometricStatus status = enrollTask.getStatus();

		if (status != NBiometricStatus.OK) {
			System.out.format("Enrollment was unsuccessful. Status: %s.\n", status);
			if (enrollTask.getError() != null) throw enrollTask.getError();
			System.exit(-1);
		}

		biometricClient.setMatchingThreshold(40);

		biometricClient.setMatchingWithDetails(true);

		
		try {
			status = biometricClient.identify(subject);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (status == NBiometricStatus.OK) {
			for (NMatchingResult matchingResult : subject.getMatchingResults()) {
				
				matchingResult.getId().toString().length();
				//System.out.println("\nPosicion del elemento Match: "+matchingResult.getId());
				posicion = Integer.parseInt(matchingResult.getId());
				//System.out.println("El elemento match es: "+path.get(posicion));
				
				
				//System.out.format("Matched with ID: '%s' with score %d\n", matchingResult.getId(), matchingResult.getScore());
				if (matchingResult.getMatchingDetails() != null) {
					//System.out.format("%s", getMatchingDetailsToString(matchingResult.getMatchingDetails()));
				}
			}
		} else if (status == NBiometricStatus.MATCH_NOT_FOUND) {
			//System.out.format("\nMatch not found\n");
			posicion = -2;
		} else {
			//System.out.format("Identification failed. Status: %s.\n", status);
			System.exit(-1);
		}
		
	}

	public static void main(String[] args) throws Throwable {
		IdentifyFinger instancia = new IdentifyFinger();
		instancia.identify();
		
	}

	private static NSubject createSubject(String fileName, String subjectId) throws IOException {
		NSubject subject = new NSubject();
		subject.setTemplateBuffer(NFile.readAllBytes(fileName));
		subject.setId(subjectId);

		return subject;
	}

	private static String getMatchingDetailsToString(NMatchingDetails details) {
		StringBuffer sb = new StringBuffer();
		if (details.getBiometricType().contains(NBiometricType.FINGER)) {
			sb.append("    Fingerprint match details: ");
			sb.append(String.format(" score = %d%n", details.getFingersScore()));
			for (NFMatchingDetails fngrDetails : details.getFingers()) {
				sb.append(String.format("    fingerprint index: %d; score: %d;%n", fngrDetails.getMatchedIndex(), fngrDetails.getScore()));
			}
		}

		return sb.toString();
	}
}
