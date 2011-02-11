package suncertify.admin.service;

import suncertify.db.DB;

public class ServerState {

    private static final ServerState INSTANCE = new ServerState();

    private DB database;

    public static ServerState instance() {
	return INSTANCE;
    }

    public DB getDatabase() {
	return database;
    }

    void setDatabase(final DB db) {
	database = db;
    }

}
