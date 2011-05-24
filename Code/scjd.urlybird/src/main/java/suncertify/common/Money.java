package suncertify.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

/**
 * <code>Money</code> represents an amount money ;) in a specified currency. The
 * default currency is the US dollar. All instances of <code>Money</code> are
 * immutable value objects.
 * 
 * @author arnelandwehr
 * 
 */
public final class Money implements Serializable, Comparable<Money> {

    /**
     * the SUID.
     */
    private static final long serialVersionUID = -579919526422533252L;

    /**
     * the amount of money.
     */
    private final BigDecimal amount;

    /**
     * the currency of the money.
     * 
     */
    private final Currency curreny;

    /**
     * Creates a new {@link Money} object with the given amount and the given
     * currency.
     * 
     * @param amount
     *            the amount of <code>Money</code> to create, must not be
     *            <code>null</code>.
     * @param currency
     *            the currency of the money to create.
     * 
     * @return the specified <code>Money</code>.
     */
    public static Money create(final String amount, final Currency currency) {

	return new Money(new BigDecimal(amount), currency);
    }

    /**
     * Creates an amount of dollar.
     * 
     * @param amount
     *            the amount of dollar to create.
     * @return a Money object with the given amount and in the currency US
     *         dollar ($).
     */
    public static Money createDollar(final String amount) {
	return create(amount, Currency.getInstance(Locale.US));
    }

    /**
     * Private constructor, never user.
     * 
     * @param amount
     *            the amount of money.
     * @param curreny
     *            the currency.
     */
    private Money(final BigDecimal amount, final Currency curreny) {
	super();
	this.amount = amount;
	this.curreny = curreny;
    }

    /**
     * Getter for the amount.
     * 
     * @return the amount.
     */
    public BigDecimal getAmount() {
	return amount;
    }

    /**
     * Getter for the currency.
     * 
     * @return the currency.
     */
    public Currency getCurreny() {
	return curreny;
    }

    @Override
    public boolean equals(final Object object) {
	if (object == this) {
	    return true;
	}
	if (!(object instanceof Money)) {
	    return false;
	}
	final Money money = (Money) object;
	return this.curreny.getCurrencyCode().equals(
		money.curreny.getCurrencyCode())
		&& this.amount.equals(money.amount);
    }

    @Override
    public int hashCode() {
	int result = 17;
	result = 31 * result + curreny.getCurrencyCode().hashCode();
	result = 31 * result + amount.hashCode();
	return result;
    }

    @Override
    public String toString() {

	return getClass().getSimpleName() + " [ " + "amount = " + amount
		+ "; currency = " + curreny + " ] ";
    }

    @Override
    public int compareTo(final Money money) {
	return this.amount.subtract(money.amount).intValue();
    }

}
