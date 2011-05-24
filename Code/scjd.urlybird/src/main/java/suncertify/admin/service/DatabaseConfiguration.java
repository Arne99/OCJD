package suncertify.admin.service;

import java.io.File;

/**
 * Contains the configuration parameters for the connection to an database. This
 * class is an immutable value object.
 * 
 * @author arnelandwehr
 * 
 */
public final class DatabaseConfiguration {

    /** the location of the database. */
    private final File location;

    /**
     * Constructs a new <code>DatabaseConfiguration</code>.
     * 
     * @param location
     *            the location of the database.
     */
    public DatabaseConfiguration(final File location) {
	super();
	this.location = location;
    }

    /**
     * Getter for the database path.
     * 
     * @return the database path
     */
    public File getDatabaseLocation() {
	return location;
    }

}
