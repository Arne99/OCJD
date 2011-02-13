package suncertify.admin.gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class UrlyBirdProperties {

    private static final String PROPERTY_NAME = "suncertify.properties";
    private static final String PROPERTY_DIR = "user.dir";

    public enum PropertyName {
	HOST, PORT, DB_PATH
    }

    public UrlyBirdProperties() throws IOException {

	final File propertyFile = getPropertyFile();
	if (!propertyFile.exists()) {
	    propertyFile.createNewFile();
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
	try {
	    propertyReader = new BufferedReader(new FileReader(propertyFile));
	    final Properties properties = new Properties();
	    properties.load(propertyReader);
	    properties.setProperty(name.name(), value);
	    propertyWriter = new BufferedWriter(new FileWriter(propertyFile));
	    properties.store(propertyWriter, "");
	} finally {
	    if (propertyReader != null) {
		propertyReader.close();
	    }
	    if (propertyWriter != null) {
		propertyWriter.close();
	    }
	}
    }

    private File getPropertyFile() {
	final File currentDirPath = new File(System.getProperties()
		.getProperty(PROPERTY_DIR));
	final File propertyFile = new File(currentDirPath, PROPERTY_NAME);
	return propertyFile;
    }
}
