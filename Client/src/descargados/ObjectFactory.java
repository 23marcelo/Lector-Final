
package descargados;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the descargados package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GuardarHuellaArg1_QNAME = new QName("", "arg1");
    private final static QName _GetMensajeResponse_QNAME = new QName("http://webServices/", "getMensajeResponse");
    private final static QName _Exception_QNAME = new QName("http://webServices/", "Exception");
    private final static QName _GetDirectorio_QNAME = new QName("http://webServices/", "getDirectorio");
    private final static QName _GetMensaje_QNAME = new QName("http://webServices/", "getMensaje");
    private final static QName _IdentificarHuella_QNAME = new QName("http://webServices/", "identificarHuella");
    private final static QName _GetListaHuellas_QNAME = new QName("http://webServices/", "getListaHuellas");
    private final static QName _GetDirectorioResponse_QNAME = new QName("http://webServices/", "getDirectorioResponse");
    private final static QName _GuardarHuella_QNAME = new QName("http://webServices/", "guardarHuella");
    private final static QName _GuardarHuellaResponse_QNAME = new QName("http://webServices/", "guardarHuellaResponse");
    private final static QName _GetListaHuellasResponse_QNAME = new QName("http://webServices/", "getListaHuellasResponse");
    private final static QName _IdentificarHuellaResponse_QNAME = new QName("http://webServices/", "identificarHuellaResponse");
    private final static QName _IdentificarHuellaArg0_QNAME = new QName("", "arg0");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: descargados
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetDirectorio }
     * 
     */
    public GetDirectorio createGetDirectorio() {
        return new GetDirectorio();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link IdentificarHuella }
     * 
     */
    public IdentificarHuella createIdentificarHuella() {
        return new IdentificarHuella();
    }

    /**
     * Create an instance of {@link GetMensaje }
     * 
     */
    public GetMensaje createGetMensaje() {
        return new GetMensaje();
    }

    /**
     * Create an instance of {@link GetMensajeResponse }
     * 
     */
    public GetMensajeResponse createGetMensajeResponse() {
        return new GetMensajeResponse();
    }

    /**
     * Create an instance of {@link IdentificarHuellaResponse }
     * 
     */
    public IdentificarHuellaResponse createIdentificarHuellaResponse() {
        return new IdentificarHuellaResponse();
    }

    /**
     * Create an instance of {@link GetListaHuellasResponse }
     * 
     */
    public GetListaHuellasResponse createGetListaHuellasResponse() {
        return new GetListaHuellasResponse();
    }

    /**
     * Create an instance of {@link GetListaHuellas }
     * 
     */
    public GetListaHuellas createGetListaHuellas() {
        return new GetListaHuellas();
    }

    /**
     * Create an instance of {@link GuardarHuellaResponse }
     * 
     */
    public GuardarHuellaResponse createGuardarHuellaResponse() {
        return new GuardarHuellaResponse();
    }

    /**
     * Create an instance of {@link GuardarHuella }
     * 
     */
    public GuardarHuella createGuardarHuella() {
        return new GuardarHuella();
    }

    /**
     * Create an instance of {@link GetDirectorioResponse }
     * 
     */
    public GetDirectorioResponse createGetDirectorioResponse() {
        return new GetDirectorioResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "arg1", scope = GuardarHuella.class)
    public JAXBElement<byte[]> createGuardarHuellaArg1(byte[] value) {
        return new JAXBElement<byte[]>(_GuardarHuellaArg1_QNAME, byte[].class, GuardarHuella.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMensajeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webServices/", name = "getMensajeResponse")
    public JAXBElement<GetMensajeResponse> createGetMensajeResponse(GetMensajeResponse value) {
        return new JAXBElement<GetMensajeResponse>(_GetMensajeResponse_QNAME, GetMensajeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webServices/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDirectorio }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webServices/", name = "getDirectorio")
    public JAXBElement<GetDirectorio> createGetDirectorio(GetDirectorio value) {
        return new JAXBElement<GetDirectorio>(_GetDirectorio_QNAME, GetDirectorio.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMensaje }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webServices/", name = "getMensaje")
    public JAXBElement<GetMensaje> createGetMensaje(GetMensaje value) {
        return new JAXBElement<GetMensaje>(_GetMensaje_QNAME, GetMensaje.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentificarHuella }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webServices/", name = "identificarHuella")
    public JAXBElement<IdentificarHuella> createIdentificarHuella(IdentificarHuella value) {
        return new JAXBElement<IdentificarHuella>(_IdentificarHuella_QNAME, IdentificarHuella.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListaHuellas }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webServices/", name = "getListaHuellas")
    public JAXBElement<GetListaHuellas> createGetListaHuellas(GetListaHuellas value) {
        return new JAXBElement<GetListaHuellas>(_GetListaHuellas_QNAME, GetListaHuellas.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDirectorioResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webServices/", name = "getDirectorioResponse")
    public JAXBElement<GetDirectorioResponse> createGetDirectorioResponse(GetDirectorioResponse value) {
        return new JAXBElement<GetDirectorioResponse>(_GetDirectorioResponse_QNAME, GetDirectorioResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GuardarHuella }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webServices/", name = "guardarHuella")
    public JAXBElement<GuardarHuella> createGuardarHuella(GuardarHuella value) {
        return new JAXBElement<GuardarHuella>(_GuardarHuella_QNAME, GuardarHuella.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GuardarHuellaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webServices/", name = "guardarHuellaResponse")
    public JAXBElement<GuardarHuellaResponse> createGuardarHuellaResponse(GuardarHuellaResponse value) {
        return new JAXBElement<GuardarHuellaResponse>(_GuardarHuellaResponse_QNAME, GuardarHuellaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListaHuellasResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webServices/", name = "getListaHuellasResponse")
    public JAXBElement<GetListaHuellasResponse> createGetListaHuellasResponse(GetListaHuellasResponse value) {
        return new JAXBElement<GetListaHuellasResponse>(_GetListaHuellasResponse_QNAME, GetListaHuellasResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentificarHuellaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webServices/", name = "identificarHuellaResponse")
    public JAXBElement<IdentificarHuellaResponse> createIdentificarHuellaResponse(IdentificarHuellaResponse value) {
        return new JAXBElement<IdentificarHuellaResponse>(_IdentificarHuellaResponse_QNAME, IdentificarHuellaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "arg0", scope = IdentificarHuella.class)
    public JAXBElement<byte[]> createIdentificarHuellaArg0(byte[] value) {
        return new JAXBElement<byte[]>(_IdentificarHuellaArg0_QNAME, byte[].class, IdentificarHuella.class, ((byte[]) value));
    }

}
