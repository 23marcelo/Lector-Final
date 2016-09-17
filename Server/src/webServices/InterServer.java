package webServices;

import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;


@WebService
public interface InterServer {
	
	@WebMethod
	public String getDirectorio();
	
	@WebMethod
	public String getMensaje();
	
	@WebMethod
	public ArrayList<String> getListaHuellas();
	
	
	
	@WebMethod
	public int guardarHuella(String nombre, byte[] huella) throws Exception;
	
	@WebMethod
	/**
	 * que es el mètodo
	 * @param huella sobre el paràmeto
	 * @return lo que retorna
	 * @throws Exception la excepcion
	 */
	public String identificarHuella(byte[] huella) throws Exception ;
		
}