package suncertify.admin.service;

public class ServerConfiguration {

    private final int port;

    private final String hostName;

    private final String clientServiceName;

    public ServerConfiguration(final int port, final String hostName,
	    final String clientServiceName) {
	super();
	this.port = port;
	this.hostName = hostName;
	this.clientServiceName = clientServiceName;
    }

    public ServerConfiguration() {
	this(1099, "localhost", "CLIENT_SERVICE");
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
