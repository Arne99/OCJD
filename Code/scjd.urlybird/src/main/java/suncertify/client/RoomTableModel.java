package suncertify.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import suncertify.common.Money;
import suncertify.common.RoomOffer;

/**
 * {@link TableModel} for displaying, selecting and sorting {@link RoomOffer}s.
 * The table is only single selectable and not editable.
 * 
 * @author arnelandwehr
 * 
 */
public final class RoomTableModel extends AbstractTableModel {

    /**
     * the SUID.
     */
    private static final long serialVersionUID = 6497967946730643820L;

    /**
     * Contains for every column of this {@link TableModel} the:
     * <ul>
     * <li>class of the objects which could be displayed in it.</li>
     * <li>the position of the column</li>
     * <li>the name of the column</li>
     * <li>the {@link TableCellRenderer} to use.</li>
     * </ul>
     * 
     * @author arnelandwehr
     * 
     */
    private enum COLUMN {

	/** the column id. */
	ID(Integer.class, 0, "ID", new DefaultTableCellRenderer()),

	/** the column hotel. */
	HOTEL(String.class, 1, "Hotel Name", new DefaultTableCellRenderer()),

	/** the column location. */
	LOCATION(String.class, 2, "Location", new DefaultTableCellRenderer()),

	/** the column size. */
	SIZE(Integer.class, 3, "Room Size", new DefaultTableCellRenderer()),

	/** the column smoking. */
	SMOKING(Boolean.class, 4, "Smoking", new BooleanRenderer()),

	/** the column price. */
	PRICE(Money.class, 5, "Price", new MoneyRenderer()),

	/** the column date. */
	DATE(Date.class, 6, "Date", new DateRenderer()),

	/** the column customer. */
	CUSTOMER(String.class, 7, "Customer ID", new DefaultTableCellRenderer());

	/** the class. */
	private final Class<?> clazz;

	/** the position. */
	private final int position;

	/** the name of the column. */
	private final String name;

	/** the renderer. */
	private final DefaultTableCellRenderer renderer;

	/**
	 * Construct a new <code>COLUMN</code>.
	 * 
	 * @param clazz
	 *            the class
	 * @param position
	 *            the position
	 * @param text
	 *            the text
	 * @param renderer
	 *            the renderer
	 */
	private COLUMN(final Class<?> clazz, final int position,
		final String text, final DefaultTableCellRenderer renderer) {
	    this.clazz = clazz;
	    this.position = position;
	    this.name = text;
	    this.renderer = renderer;

	}

    }

    /** all columns this {@link TableModel} contains. */
    private final Set<COLUMN> columns = EnumSet.allOf(COLUMN.class);

    /** all rooms to display. */
    private final ArrayList<RoomOffer> rooms = new ArrayList<RoomOffer>();

    /**
     * Getter for the {@link RoomOffer} at the given index.
     * 
     * @param index
     *            the index of the <code>RoomOffer</code> to get.
     * @return the <code>RoomOffer</code>.
     */
    RoomOffer getRoomAtIndex(final int index) {
	return rooms.get(index);
    }

    /**
     * Getter for the id of the {@link RoomOffer} at the given index.
     * 
     * @param index
     *            the index of the <code>RoomOffer</code> to get.
     * @return the id.
     */
    String getIdAtIndex(final int index) {
	return rooms.get(index).getIndex() + "";
    }

    /**
     * Getter for the name of the {@link RoomOffer} at the given index.
     * 
     * @param index
     *            the name of the <code>RoomOffer</code> to get.
     * @return the id.
     */
    String getHotelNameAtIndex(final int index) {
	return rooms.get(index).getHotel();
    }

    /**
     * Getter for the location of the {@link RoomOffer} at the given index.
     * 
     * @param index
     *            the location of the <code>RoomOffer</code> to get.
     * @return the id.
     */
    String getLocationAtIndex(final int index) {
	return rooms.get(index).getCity();
    }

    /**
     * Getter for the isSmokingAllowed of the {@link RoomOffer} at the given
     * index.
     * 
     * @param index
     *            the isSmokingAllowed of the <code>RoomOffer</code> to get.
     * @return the id.
     */
    String getSmokingAtIndex(final int index) {
	return new BooleanRenderer().convertSmokingBoolean(rooms.get(index)
		.isSmokingAllowed());
    }

    /**
     * Getter for the date of the {@link RoomOffer} at the given index.
     * 
     * @param index
     *            the date of the <code>RoomOffer</code> to get.
     * @return the id.
     */
    String getDateAtIndex(final int index) {
	return new DateRenderer().convertDate(rooms.get(index)
		.getBookableDate());
    }

    /**
     * Getter for the price of the {@link RoomOffer} at the given index.
     * 
     * @param index
     *            the price of the <code>RoomOffer</code> to get.
     * @return the id.
     */
    String getPriceAtIndex(final int index) {
	return new MoneyRenderer().convertMoney(rooms.get(index).getPrice());
    }

    /**
     * Getter for the customerId of the {@link RoomOffer} at the given index.
     * 
     * @param index
     *            the cusomerId of the <code>RoomOffer</code> to get.
     * @return the id.
     */
    String getCustomerIdAtIndex(final int index) {
	return rooms.get(index).getCustomerId();
    }

    /**
     * Getter for the size of the {@link RoomOffer} at the given index.
     * 
     * @param index
     *            the size of the <code>RoomOffer</code> to get.
     * @return the id.
     */
    String getRoomSizeAtIndex(final int index) {
	return rooms.get(index).getRoomSize() + "";
    }

    /**
     * Replaces the {@link RoomOffer} in the table with the same id with the
     * given <code>RoomOffer</code> and refreshes the display.
     * 
     * @param changedRoom
     *            the <code>RoomOffer</code> that should replace the
     *            <code>RoomOffer</code> with the same id in the table.
     */
    public void replaceRoom(final RoomOffer changedRoom) {

	for (final RoomOffer room : rooms) {
	    if (room.getIndex() == changedRoom.getIndex()) {
		final int index = rooms.indexOf(room);
		rooms.remove(index);
		rooms.add(changedRoom);
		fireTableRowsUpdated(index, index);
		return;
	    }
	}
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
	for (final COLUMN column : columns) {
	    if (column.position == columnIndex) {
		return column.clazz;
	    }
	}

	throw new IllegalStateException(
		"no column found for the given column index: " + columnIndex);
    }

    /**
     * Replaces all {@link RoomOffer}s in the table with the given rooms and
     * displays the refreshed view.
     * 
     * @param newRooms
     *            the <code>RoomOffer</code> to display.
     */
    public void replaceAll(final List<RoomOffer> newRooms) {

	rooms.clear();
	rooms.addAll(newRooms);
	fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(final int arg0, final int arg1) {
	return false;
    }

    @Override
    public int getColumnCount() {
	return columns.size();
    }

    @Override
    public int getRowCount() {
	return rooms.size();
    }

    /**
     * Initializes the given {@link JTable} by replacing the actual
     * {@link TableModel} with a new {@link RoomTableModel}, fills it and
     * displays the data.
     * 
     * @param table
     *            the <code>JTable</code> to initialize.
     * @return the {@link RoomTableModel} that is now the {@link TableModel} of
     *         the given {@link JTable}.
     */
    public static RoomTableModel initTable(final JTable table) {

	final RoomTableModel model = new RoomTableModel();

	table.setModel(model);
	final List<TableColumn> columns = Collections.list(table
		.getColumnModel().getColumns());
	final EnumSet<COLUMN> columnData = EnumSet.allOf(COLUMN.class);
	for (final COLUMN data : columnData) {
	    final TableColumn columnForData = columns.get(data.position);
	    columnForData.setHeaderValue(data.name);
	    columnForData.setCellRenderer(data.renderer);
	    columnForData.setIdentifier(data.name);
	}

	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	table.setAutoCreateRowSorter(true);

	return model;
    }

    @Override
    public Object getValueAt(final int row, final int column) {
	final RoomOffer roomOffer = rooms.get(row);

	switch (column) {
	case 0:
	    return roomOffer.getIndex();
	case 1:
	    return roomOffer.getHotel();
	case 2:
	    return roomOffer.getCity();
	case 3:
	    return roomOffer.getRoomSize();
	case 4:
	    return (roomOffer.isSmokingAllowed());
	case 5:
	    return roomOffer.getPrice();
	case 6:
	    return roomOffer.getBookableDate();
	case 7:
	    return roomOffer.getCustomerId();
	default:
	    assert false : "" + column + " is not spezified!";
	    return null;
	}
    }

    /**
     * A renderer for {@link Date}s.
     * 
     * @author arnelandwehr
     * 
     */
    private static final class DateRenderer extends DefaultTableCellRenderer {

	/**
	 * the SUID.
	 */
	private static final long serialVersionUID = 8121111799629882230L;

	@Override
	protected void setValue(final Object value) {
	    final Date date = (Date) value;
	    setText(convertDate(date));
	}

	/**
	 * Converts an date to an String of the form "yyyy/MM/dd".
	 * 
	 * @param date
	 *            the date to convert.
	 * @return the String.
	 */
	private String convertDate(final Date date) {
	    return new SimpleDateFormat("yyyy/MM/dd", Locale.US).format(date);
	}
    }

    /**
     * Renderer for {@link Money} objects.
     * 
     * @author arnelandwehr
     * 
     */
    private static final class MoneyRenderer extends DefaultTableCellRenderer {

	/**
	 * the SUID.
	 */
	private static final long serialVersionUID = -2657601358336673684L;

	@Override
	protected void setValue(final Object value) {

	    final Money money = (Money) value;
	    setText(convertMoney(money));
	}

	/**
	 * Converts a {@link Money} object to an String of the form $ XX.XX.
	 * 
	 * @param money
	 *            the money to convert.
	 * @return the String.
	 */
	private String convertMoney(final Money money) {
	    return money.getCurreny().getSymbol(Locale.US) + " "
		    + money.getAmount();
	}
    }

    /**
     * A renderer for booleans.
     * 
     * @author arnelandwehr
     * 
     */
    private static final class BooleanRenderer extends DefaultTableCellRenderer {

	/**
	 * the SUID.
	 */
	private static final long serialVersionUID = -1356597765798988250L;

	@Override
	protected void setValue(final Object value) {
	    final Boolean bool = (Boolean) value;
	    setText(convertSmokingBoolean(bool));
	}

	/**
	 * Converts a boolean value to a String of the form "Y" or "N".
	 * 
	 * @param smoking
	 *            the boolean value to convert.
	 * @return the String.
	 */
	private String convertSmokingBoolean(final boolean smoking) {
	    return (smoking) ? "Y" : "N";
	}
    }

}
