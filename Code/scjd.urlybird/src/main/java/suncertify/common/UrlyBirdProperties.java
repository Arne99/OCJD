package suncertify.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * Provides methods to store and retrieve properties in the UrlyBird
 * application.
 * 
 * Singleton, because there should be only one gateway to the properties.
 * 
 * This class is thread safe.
 * 
 * @author arnelandwehr
 * 
 */
public final class UrlyBirdProperties {

    /** the singleton instance. */
    private static final UrlyBirdProperties INSTANCE = new UrlyBirdProperties();

    /**
     * Getter for the singleton instance.
     * 
     * @return one UrlyBirdProperties instance, never <code>null</code>.
     */
    public static UrlyBirdProperties getInstance() {
	return INSTANCE;
    }

    /** constant of the name of the property file. */
    private static final String PROPERTY_NAME = "suncertify.properties";

    /** constant of the path of the property file. */
    private static final String PROPERTY_DIR = "user.dir";

    /**
     * All supported property names.
     * 
     * @author arnelandwehr
     * 
     */
    public enum PropertyName {

	/**
	 * Property for the last input in the host address field of the server
	 * admin gui.
	 */
	ADMIN_GUI_HOST,

	/**
	 * Property for the last input in the port field of the server admin
	 * gui.
	 */
	ADMIN_GUI_PORT,

	/**
	 * Property for the last input in the database path field of the server
	 * admin gui.
	 */
	ADMIN_GUI_DB_PATH,

	/**
	 * Property for the last input in the host address field of the client
	 * server connection gui.
	 */
	CLIENT_CONNECTION_GUI_HOST,

	/**
	 * Property for the last input in the port field of the client server
	 * connection gui.
	 */
	CLIENT_CONNECTION_GUI_PORT,

	/**
	 * Property for the last input in the database path field of the client
	 * server connection gui.
	 */
	CLIENT_DATABASE_GUI_PATH
    }

    /**
     * Private constructor, never use.
     * 
     */
    private UrlyBirdProperties() {

	final File propertyFile = getPropertyFile();
	if (!propertyFile.exists()) {
	    try {
		propertyFile.createNewFile();
	    } catch (final IOException e) {
		e.printStackTrace();
	    }
	}
    }

    /**
     * Retrieves the value that is stored under the given property name.
     * 
     * @param name
     *            the name of the property which value should be loaded.
     * @return the under the name stored value or an empty string if nothing is
     *         stored.
     * @throws IOException
     *             if an io proplem occurs during the storing.
     */
    public String getProperty(final PropertyName name) throws IOException {
	BufferedReader propertyReader = null;

	final File currentDirPath = new File(System.getProperties()
		.getProperty(PROPERTY_DIR));
	final File propertyFile = new File(currentDirPath, PROPERTY_NAME);
	try {
	    propertyReader = new BufferedReader(new FileReader(propertyFile));
	    final Properties properties = new Properties();
	    properties.load(propertyReader);
	    return properties.getProperty(name.name());
	} finally {
	    if (propertyReader != null) {
		propertyReader.close();
	    }
	}
    }

    /**
     * Stores the given value under the given property name.
     * 
     * @param name
     *            the name of the propertie where the given value should be
     *            stored under.
     * @param value
     *            the value that should be stored.
     * @throws IOException
     *             if an io proplem occurs during the storing.
     */
    public void setProperty(final PropertyName name, final String value)
	    throws IOException {

	final File propertyFile = getPropertyFile();

	BufferedReader propertyReader = null;
	BufferedWriter propertyWriter = null;
	final Properties properties = new Properties();
	try {
	    propertyReader = new BufferedReader(new FileReader(propertyFile));
	    properties.load(propertyReader);
	    properties.setProperty(name.name(), value);
	} finally {
	    propertyReader.close();
	}
	try {
	    propertyWriter = new BufferedWriter(new FileWriter(propertyFile));
	    properties.store(propertyWriter, "");
	} finally {
	    propertyWriter.flush();
	    propertyWriter.close();
	}
    }

    /**
     * Loads the property file.
     * 
     * @return the property file.
     */
    private File getPropertyFile() {
	final File currentDirPath = new File(System.getProperties()
		.getProperty(PROPERTY_DIR));
	final File propertyFile = new File(currentDirPath, PROPERTY_NAME);
	return propertyFile;
    }
}
