package suncertify.admin;

import java.rmi.Naming;

/**
 * Contains the configuration parameters for the server, like the address under
 * which it is reachable and the port.
 * 
 */
public final class ServerConfiguration {

    /**
     * The constant name under which the server services are bind in the Naming
     * service. Could be configurable in an further release.
     */
    private static final String CLIENT_SERVICE = "CLIENT_SERVICE";

    /**
     * the port.
     */
    private final int port;

    /**
     * the host name.
     */
    private final String hostName;

    /**
     * the name under which the server services are bound in the {@link Naming}
     * service.
     */
    private final String clientServiceName;

    /**
     * Constructs a new <code>ServerConfiguration</code>.
     * 
     * @param port
     *            the port
     * @param hostName
     *            the host name
     */
    public ServerConfiguration(final int port, final String hostName) {
	super();
	this.port = port;
	this.hostName = hostName;
	this.clientServiceName = CLIENT_SERVICE;
    }

    /**
     * Constructs a new default configuration with "localhost" and port "1099".
     */
    public ServerConfiguration() {
	this(1099, "localhost");
    }

    /**
     * Getter for the port.
     * 
     * @return the port
     */
    public int getPort() {
	return port;
    }

    /**
     * Getter for the name under which the server services are bound in the
     * {@link Naming} service.
     * 
     * @return the bound name.
     */
    public String getClientServiceName() {
	return clientServiceName;
    }

    /**
     * Returns the address under which the server is reachable in the form
     * //hostname:port.
     * 
     * @return the complete host address.
     */
    public String getHostNameWithPort() {
	return "//" + hostName + ":" + port;
    }

}
