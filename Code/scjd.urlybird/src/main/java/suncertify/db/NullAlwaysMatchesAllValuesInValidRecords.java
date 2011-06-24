package suncertify.db;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import suncertify.common.Specification;

/**
 * {@link Specification} to search for valid {@link Record}s with a set of
 * criteria values. A valid Record is matched if every criteria value at a
 * position n matches the record value at position n. A criteria matches a
 * {@link Record} value if:
 * <ul>
 * <li>the criteria value is a substring of the record value beginning at index
 * 0. For example criteria "fred" matches "freddy".</li>
 * <li>the criteria is <code>null</code>. A <code>null</code> criteria matches
 * every value.</li>
 * 
 * In addition:
 * <li>the matching is case sensitive</li>
 * <li>the criteria must haven the same number of values as the {@link Record}
 * or they never match</li>
 * </ul>
 * 
 * @author arnelandwehr
 * 
 */
final class NullAlwaysMatchesAllValuesInValidRecords implements
	Specification<Record> {

    /** the criteria for the matches. */
    private final List<String> criteria;

    /**
     * Constructs a new <code>NullAlwaysMatchesAllValuesInValidRecords</code>.
     * 
     * @param criteria
     *            the criteria that defines the matched {@link Record}s, must
     *            not be <code>null</code>.
     */
    NullAlwaysMatchesAllValuesInValidRecords(final List<String> criteria) {
	super();
	this.criteria = new ArrayList<String>(criteria);
    }

    @Override
    public boolean isSatisfiedBy(final Record record) {

	if (!record.isValid()) {
	    return false;
	}

	final List<String> recordValues = record.getAllBusinessValues();

	if (recordValues.size() != criteria.size()) {
	    return false;
	}

	final ListIterator<String> valueIter = recordValues.listIterator();
	while (valueIter.hasNext()) {
	    final String value = valueIter.next();
	    final int previousIndex = valueIter.previousIndex();
	    final String relevantCriteria = criteria.get(previousIndex);

	    if (relevantCriteria != null && !value.startsWith(relevantCriteria)) {
		return false;
	    }
	}

	return true;
    }
}
