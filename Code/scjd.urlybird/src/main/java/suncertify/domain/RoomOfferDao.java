package suncertify.domain;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import suncertify.common.Money;
import suncertify.db.DB;
import suncertify.db.DuplicateKeyException;
import suncertify.db.RecordNotFoundException;
import suncertify.db.SecurityException;

/**
 * The <code>RoomOfferDao</code> is the bridge between the stored
 * <code>RoomOffer</code> objects in the database and the <code>RoomOffer</code>
 * domain objects. It offers the needed CRUD operations on
 * <code>RoomOffers</code> and abstracts the persistence layer from the domain
 * layer.
 * 
 * 
 * @author arnelandwehr
 * 
 */
final class RoomOfferDao implements DataAccessObject<UrlyBirdRoomOffer> {

    /** the database where the <code>RoomOffers</code> are stored. */
    private final DB database;

    /** the factory for <code>RoomOffer</code>. */
    private final RoomOfferFactory factory;

    /**
     * Construct a new <code>RoomOfferDao</code>.
     * 
     * @param database
     *            the database, must not be <code>null</code>.
     * @param roomOfferFactory
     *            the factory, must not be <code>null</code>.
     */
    RoomOfferDao(final DB database, final RoomOfferFactory roomOfferFactory) {
	this.database = database;
	this.factory = roomOfferFactory;
    }

    @Override
    public UrlyBirdRoomOffer create(final List<String> values)
	    throws DuplicateKeyException, ConstraintViolationException {

	factory.checkValues(values);
	final int index = database.create(values.toArray(new String[values
		.size()]));
	return factory.createRoomOffer(values, index);
    }

    @Override
    public void delete(final int index, final long lock)
	    throws RecordNotFoundException, SecurityException {
	database.delete(index, lock);
    }

    @Override
    public List<UrlyBirdRoomOffer> find(final List<String> criteria)
	    throws ConstraintViolationException {
	final int[] indices = database.find(criteria
		.toArray(new String[criteria.size()]));

	// this could give you stale data.
	final List<UrlyBirdRoomOffer> matchingRooms = new ArrayList<UrlyBirdRoomOffer>(
		indices.length);
	for (final int index : indices) {
	    UrlyBirdRoomOffer roomOffer = null;
	    try {
		roomOffer = read(index);
		matchingRooms.add(roomOffer);
	    } catch (final RecordNotFoundException e) {
		e.printStackTrace();
	    }
	}

	return matchingRooms;
    }

    @Override
    public long lock(final int index) throws RecordNotFoundException {
	return database.lock(index);
    }

    @Override
    public UrlyBirdRoomOffer read(final int index)
	    throws RecordNotFoundException, ConstraintViolationException {
	final String[] values = database.read(index);
	final UrlyBirdRoomOffer roomOffer = factory.createRoomOffer(
		Arrays.asList(values), index);

	return roomOffer;
    }

    @Override
    public void unlock(final int index, final long lock)
	    throws RecordNotFoundException, SecurityException {
	database.unlock(index, lock);
    }

    @Override
    public void update(final UrlyBirdRoomOffer roomOffer, final long lock)
	    throws RecordNotFoundException, SecurityException {

	database.update(roomOffer.getIndex(),
		transformToPersistableForm(roomOffer), lock);
    }

    /**
     * Transforms the given <code>RoomOffer</code> domain object to a pesistable
     * String array.
     * 
     * @param roomOffer
     *            the <code>RoomOffer</code> to transform.
     * @return the persisable form of the given domain object as an
     *         <code>String</code> array.
     */
    private String[] transformToPersistableForm(
	    final UrlyBirdRoomOffer roomOffer) {
	final String roomSize = convertIntToPersistableString(roomOffer
		.getRoomSize());
	final String smokingAllowed = convertBooleanToPersistableString(roomOffer
		.isSmokingAllowed());
	final String price = convertMoneyToPersistableString(roomOffer
		.getPrice());
	final String date = convertDateToPersistableString(roomOffer
		.getBookableDate());

	return new String[] { roomOffer.getHotel(), roomOffer.getCity(),
		roomSize, smokingAllowed, price, date,
		roomOffer.getCustomerId() };
    }

    /**
     * Converts the given boolean to a persistable <code>String</code> form.
     * 
     * @param aBool
     *            the boolean value
     * @return "Y" if aBool is true, else "N".
     */
    private String convertBooleanToPersistableString(final boolean aBool) {
	return (aBool) ? "Y" : "N";
    }

    /**
     * Converts the given {@link Date} to an persistable <code>String</code>
     * form.
     * 
     * @param aDate
     *            the date to transform.
     * @return the String representation of the form "yyyy/MM/dd".
     */
    private String convertDateToPersistableString(final Date aDate) {

	return new SimpleDateFormat("yyyy/MM/dd", Locale.US).format(aDate);
    }

    /**
     * Converts the given <code>int</code> to an <code>String</code>.
     * 
     * @param anInt
     *            the <code>int</code>.
     * @return the <code>int</code> as a <code>String</code>.
     */
    private String convertIntToPersistableString(final int anInt) {
	return "" + anInt;
    }

    /**
     * Converts the given {@link Money} to an persistable String form.
     * 
     * @param someMoney
     *            the <code>Money</code> to transform.
     * @return the persiatable String representation.
     */
    private String convertMoneyToPersistableString(final Money someMoney) {
	final String symbol = someMoney.getCurreny().getSymbol(Locale.US);
	final BigDecimal amount = someMoney.getAmount();
	final String value = amount.setScale(2).toPlainString();
	return symbol + value;
    }

}
