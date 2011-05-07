package suncertify.admin.gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class UrlyBirdProperties {

    private static UrlyBirdProperties INSTANCE = new UrlyBirdProperties();

    public static UrlyBirdProperties getInstance() {
	return INSTANCE;
    }

    private static final String PROPERTY_NAME = "suncertify.properties";
    private static final String PROPERTY_DIR = "user.dir";

    public enum PropertyName {
	ADMIN_GUI_HOST,
	ADMIN_GUI_PORT,
	ADMIN_GUI_DB_PATH,
	CLIENT_CONNECTION_GUI_HOST,
	CLIENT_CONNECTION_GUI_PORT,
	CLIENT_DATABASE_GUI_PATH
    }

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
	    propertyWriter.close();
	}
    }

    private File getPropertyFile() {
	final File currentDirPath = new File(System.getProperties()
		.getProperty(PROPERTY_DIR));
	final File propertyFile = new File(currentDirPath, PROPERTY_NAME);
	return propertyFile;
    }
}
