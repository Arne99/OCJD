package suncertify.datafile;

import java.util.List;

import suncertify.db.Record;

interface DataFileRecord extends Record {

    byte[] getValuesAsBytes();

    List<String> getAllValues();

}