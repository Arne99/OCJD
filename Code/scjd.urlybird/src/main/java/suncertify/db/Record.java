package suncertify.db;

import java.util.List;

/**
 * A <code>Record</code> represents a set of values stored under an identifier
 * called the <i>index</i>. It could be valid or invalid. Invalid {@link Record}
 * have no more value for the business and must be ignored. In addition a
 * <code>Record</code> could store business and technical values. Technical
 * values are needed to maintain the data structure an should be ignored by the
 * business.
 * 
 * 
 * @author arnelandwehr
 * 
 */
public interface Record {

    /**
     * Getter for all business relevant values that are stored in this
     * {@link Record}.
     * 
     * @return all business values, never <code>null</code>.
     */
    List<String> getAllBusinessValues();

    /**
     * Getter for the index (identifier) of this {@link Record}.
     * 
     * @return the index, must be positive.
     */
    int getIndex();

    /**
     * Indicates if the {@link Record} is valid or not. Invalid
     * <code>Records</code> must be ignored by business queries.
     * 
     * @return <code>true</code> if the {@link Record} is valid.
     */
    boolean isValid();
}
