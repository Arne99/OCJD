package suncertify.datafile;

public interface DataFileColumn {

    String getName();

    int getSize();

    boolean containsBuissnessValues();

    int getStartIndex();

    RecordValue createValue(String string);

    int getEndIndex();

    RecordValue createDefaultValue();

}
