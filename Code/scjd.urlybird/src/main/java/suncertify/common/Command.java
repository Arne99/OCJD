package suncertify.common;

import java.io.Serializable;

/**
 * 
 * A <code>Command</code> is an order from the client to the server. It
 * encapsulates the needed information for the server to execute it.
 * 
 * The encapsulation of orders in <code>Commands</code> allows an fixed
 * interface on the server side.
 * 
 * Every implementation of this interface must be an immutable value object.
 * 
 * @author arnelandwehr
 * 
 */
public interface Command extends Serializable {

}
