package suncertify.admin.service;

import java.io.File;

public class DatabaseConfiguration {

    private final File location;

    DatabaseConfiguration(final File location) {
	super();
	this.location = location;
    }

    public File getDatabaseLocation() {
	return location;
    }

}
