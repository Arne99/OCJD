package suncertify.db;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import suncertify.util.Specification;

/**
 * 
 * 
 * @author arnelandwehr
 * 
 */
final class NullAlwaysMatches implements Specification<Record> {

    private final List<String> criteria;

    NullAlwaysMatches(final List<String> criteria) {
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
