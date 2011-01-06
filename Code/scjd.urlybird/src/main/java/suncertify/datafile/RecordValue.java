package suncertify.datafile;

class RecordValue {

    private final DataFileColumn column;
    private final String value;

    RecordValue(final DataFileColumn dataFileColumn, final String value) {

	this.column = dataFileColumn;
	this.value = expandValueToColumnSize(value);
    }

    private String expandValueToColumnSize(final String value) {

	if (value.length() == column.getSize()) {
	    return value;
	}

	final StringBuilder sb = new StringBuilder(value);
	for (int i = value.length(); i < column.getSize(); i++) {
	    sb.append(" ");
	}
	return sb.toString();
    }

    String getValue() {
	return value;
    }

    boolean isBuisnessValue() {
	return column.containsBuissnessValues();
    }

    @Override
    public boolean equals(final Object object) {

	if (this == object) {
	    return true;
	}
	if (!(object instanceof RecordValue)) {
	    return false;
	}
	final RecordValue value = (RecordValue) object;

	return this.column.equals(value.column)
		&& this.value.equals(value.value);
    }

    @Override
    public int hashCode() {
	int result = 17;
	result += 31 * result + column.hashCode();
	result += 31 * result + value.hashCode();
	return result;
    }

    @Override
    public String toString() {
	return getClass().getSimpleName() + " [ " + "column = " + column
		+ "; value = " + value + " ] ";
    }

}