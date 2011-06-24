package suncertify.admin;

import suncertify.db.DB;

/**
 * State object for the server, it contains every information that belongs to
 * one server instance.
 * 
 * Singleton.
 * 
 * @author arnelandwehr
 * 
 */
public final class ServerState {

    /**
     * the singleton instance.
     */
    private static final ServerState INSTANCE = new ServerState();

    /**
     * the database the server should connect to.
     */
    private DB database;

    /**
     * the server configuration parameters.
     */
    private ServerConfiguration serverConfig;

    /**
     * Returns the singleton instance.
     * 
     * @return the singleton instance.
     */
    public static ServerState instance() {
	return INSTANCE;
    }

    /**
     * Getter for the database.
     * 
     * @return the database.
     */
    public DB getDatabase() {
	return database;
    }

    /**
     * Setter for the database.
     * 
     * @param db
     *            the database to set.
     */
    void setDatabase(final DB db) {
	database = db;
    }

    /**
     * Setter for the {@link ServerConfiguration}.
     * 
     * @param serverConfig
     *            the server configuration to set.
     */
    void setConfig(final ServerConfiguration serverConfig) {
	this.serverConfig = serverConfig;
    }

    /**
     * Getter for the {@link ServerConfiguration}.
     * 
     * @return the server configuration.
     */
    public ServerConfiguration getConfiguration() {
	return serverConfig;
    }

}
