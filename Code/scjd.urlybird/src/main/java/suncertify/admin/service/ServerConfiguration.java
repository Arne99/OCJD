package suncertify.admin.service;

public class ServerConfiguration {

    // TODO
    private static final String CLIENT_SERVICE = "CLIENT_SERVICE";

    private final int port;

    private final String hostName;

    private final String clientServiceName;

    public ServerConfiguration(final int port, final String hostName) {
	super();
	this.port = port;
	this.hostName = hostName;
	this.clientServiceName = CLIENT_SERVICE;
    }

    public ServerConfiguration() {
	this(1099, "localhost");
    }

    public int getPort() {
	return port;
    }

    public String getClientServiceName() {
	return clientServiceName;
    }

    public String getHostNameWithPort() {
	// //host:port/name
	return "//" + hostName + ":" + port;
    }

}
