package suncertify.common.roomoffer;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

public final class Money {

    private final BigDecimal amount;

    private final Currency curreny;

    public static Money create(final String amount, final Currency currency) {

	return new Money(new BigDecimal(amount), currency);
    }

    public static Money create(final String amount) {
	return create(amount, Currency.getInstance(Locale.US));
    }

    private Money(final BigDecimal amount, final Currency curreny) {
	super();
	this.amount = amount;
	this.curreny = curreny;
    }

    public BigDecimal getAmount() {
	return amount;
    }

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

}
