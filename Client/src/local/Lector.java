 package local;

import java.util.EnumSet;
import javax.swing.JList;
import com.neurotec.lang.NCore;
import javax.swing.SwingUtilities;
import javax.swing.DefaultListModel;
import com.neurotec.devices.NDevice;
import com.neurotec.biometrics.NFinger;
import com.neurotec.biometrics.NSubject;
import com.neurotec.devices.NDeviceType;
import com.neurotec.devices.NDeviceManager;
import com.neurotec.devices.NFingerScanner;
import com.neurotec.biometrics.NBiometricTask;
import com.neurotec.biometrics.NBiometricStatus;
import com.neurotec.biometrics.NBiometricOperation;
import com.neurotec.util.concurrent.CompletionHandler;
import com.neurotec.biometrics.client.NBiometricClient;


public final class Lector{

	private NSubject subject;			/** variable que permite acceder a la huella */
	private boolean scanning;
	private JList scannerList;			/** lista de lectores disponibles */
	private NBiometricClient client;	/** Cliente que interactua con el dispositivo */
	private NDeviceManager deviceManager;
	private final CaptureCompletionHandler captureCompletionHandler;
	private boolean bad_object;
	
	public NSubject getSubject() {
		return subject;
	}

	public void setSubject(NSubject subject) {
		this.subject = subject;
	}

	// ===========================================================
	// Public constructor
	// ===========================================================
	
	/** Proceso de inicializacion, demora un tiempo importante */
	public Lector() {					
		// Se inicializan componentes necesarios para la lectura de huellas		
		captureCompletionHandler = new CaptureCompletionHandler();		
		client = new NBiometricClient();
		
		client.setUseDeviceManager(true);
		deviceManager = client.getDeviceManager();				
		deviceManager.setDeviceTypes(EnumSet.of(NDeviceType.FINGER_SCANNER));		
		deviceManager.initialize();
		
		// Aqui se esperan unos segundos para cargar las librerias del lector
		try {
			System.out.println("Inicializando dispositivo...");
			Thread.sleep(12000);			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** Metodo para iniciar la captura de la huella */
	public boolean startCapturing() {

		bad_object = false;
		
		// Si no se detecta lector retornar false
		if (this.getSelectedScanner() == null) {
			return false;			
		}
		
		// Se determina el lector a utilizar
		client.setFingerScanner(this.getSelectedScanner());

		// Create a finger.
		NFinger finger = new NFinger();

		// Add finger to subject and finger view.
		subject = new NSubject();
		subject.getFingers().add(finger);


		// Begin capturing.		
		NBiometricTask task = client.createTask(EnumSet.of(NBiometricOperation.CAPTURE, NBiometricOperation.CREATE_TEMPLATE), subject);				
		client.performTask(task, null, captureCompletionHandler);
		
		scanning = true;
		while (task.getStatus() != NBiometricStatus.OK) {			
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(task.getStatus() == NBiometricStatus.BAD_OBJECT) {
				bad_object =true;
				return false;
			}
		}
		return true;
	}


	public boolean isBad_object() {
		return bad_object;
	}

	public void setBad_object(boolean bad_object) {
		this.bad_object = bad_object;
	}

	public NFingerScanner getSelectedScanner() {
		return (NFingerScanner) scannerList.getSelectedValue();
	}
	
	/** Actualiza la lista de lectores disponibles */
	public void updateScannerList() {
		scannerList = new JList();
		scannerList.setModel(new DefaultListModel());		
		DefaultListModel model = (DefaultListModel) scannerList.getModel();
				
		model.clear();
		for (NDevice device : deviceManager.getDevices()) {
			model.addElement(device);
		}		
		
		NFingerScanner scanner = (NFingerScanner) client.getFingerScanner();
		if ((scanner == null) && (model.getSize() > 0)) {
			scannerList.setSelectedIndex(0);
		} else if (scanner != null) {
			scannerList.setSelectedValue(scanner, true);
		}
	}

	// ===========================================================
	// Inner classes
	// ===========================================================

	/** VITAL, encargado de la lectura, imprime los resultados de la lectura */
	public class CaptureCompletionHandler implements
			CompletionHandler<NBiometricTask, Object> {				
		public void completed(final NBiometricTask result,
				final Object attachment) {	
			
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					scanning = false;
					System.out.println("---------");					
					System.out.println(result.getStatus().toString());
					System.out.println("---------");
					if (result.getStatus() == NBiometricStatus.OK) {
						// Permite ver la calidad de la huella leida
//						System.out.println("Quality: "
//								+ subject.getFingers().get(0).getObjects()
//										.get(0).getQuality());
					}						
					NCore.shutdownThread();											
				}

			});
		}


		// Necesario para CaptureCompletionHandler
		public void failed(final Throwable th, final Object attachment) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					scanning = false;
				}
			});
		}
	}
}
