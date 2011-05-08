package suncertify.admin.service;

import java.io.File;

public final class DatabaseConfiguration {

    private final File location;

    public DatabaseConfiguration(final File location) {
	super();
	this.location = location;
    }

    public File getDatabaseLocation() {
	return location;
    }

}
