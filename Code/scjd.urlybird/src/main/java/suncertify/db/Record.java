package suncertify.db;

import java.util.List;

public interface Record {

    List<String> getAllBusinessValues();

    int getIndex();

    boolean isValid();
}
