package suncertify.admin.service;

import suncertify.db.DB;

public class ServerState {

    private static final ServerState INSTANCE = new ServerState();

    private DB database;

    private ServerConfiguration serverConfig;

    public static ServerState instance() {
	return INSTANCE;
    }

    public DB getDatabase() {
	return database;
    }

    void setDatabase(final DB db) {
	database = db;
    }

    void setConfig(final ServerConfiguration serverConfig) {
	this.serverConfig = serverConfig;
    }

    public ServerConfiguration getConfiguration() {
	return serverConfig;
    }

}
