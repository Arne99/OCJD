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
import javax.swing.table.TableColumn;

import suncertify.common.Money;
import suncertify.domain.RoomOffer;

public final class RoomTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 6497967946730643820L;

    private enum COLUMN {

	ID(Integer.class, 0, "ID", new DefaultTableCellRenderer()),
	HOTEL(String.class, 1, "Hotel Name", new DefaultTableCellRenderer()),
	LOCATION(String.class, 2, "Location", new DefaultTableCellRenderer()),
	SIZE(Integer.class, 3, "Room Size", new DefaultTableCellRenderer()),
	SMOKING(Boolean.class, 4, "Smoking", new BooleanRenderer()),
	PRICE(Money.class, 5, "Price", new MoneyRenderer()),
	DATE(Date.class, 6, "Date", new DateRenderer()),
	CUSTOMER(String.class, 7, "Customer ID", new DefaultTableCellRenderer());

	private final Class<?> clazz;
	private final int position;
	private final String text;
	private final DefaultTableCellRenderer renderer;

	private COLUMN(final Class<?> clazz, final int position,
		final String text, final DefaultTableCellRenderer renderer) {
	    this.clazz = clazz;
	    this.position = position;
	    this.text = text;
	    this.renderer = renderer;

	}

    }

    private final Set<COLUMN> columns = EnumSet.allOf(COLUMN.class);
    private final ArrayList<RoomOffer> rooms = new ArrayList<RoomOffer>();

    RoomOffer getRoomAtIndex(final int index) {
	return rooms.get(index);
    }

    String getIdAtIndex(final int index) {
	return rooms.get(index).getIndex() + "";
    }

    String getHotelNameAtIndex(final int index) {
	return rooms.get(index).getHotel();
    }

    String getLocationAtIndex(final int index) {
	return rooms.get(index).getCity();
    }

    String getSmokingAtIndex(final int index) {
	return new BooleanRenderer().convertSmokingBoolean(rooms.get(index)
		.isSmokingAllowed());
    }

    String getDateAtIndex(final int index) {
	return new DateRenderer().convertDate(rooms.get(index)
		.getBookableDate());
    }

    String getPriceAtIndex(final int index) {
	return new MoneyRenderer().convertMoney(rooms.get(index).getPrice());
    }

    String getCustomerIdAtIndex(final int index) {
	return rooms.get(index).getCustomerId();
    }

    String getRoomSizeAtIndex(final int index) {
	return rooms.get(index).getRoomSize() + "";
    }

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

    public static RoomTableModel initTable(final JTable table) {

	final RoomTableModel model = new RoomTableModel();

	table.setModel(model);
	final List<TableColumn> columns = Collections.list(table
		.getColumnModel().getColumns());
	final EnumSet<COLUMN> columnData = EnumSet.allOf(COLUMN.class);
	for (final COLUMN data : columnData) {
	    final TableColumn columnForData = columns.get(data.position);
	    columnForData.setHeaderValue(data.text);
	    columnForData.setCellRenderer(data.renderer);
	    columnForData.setIdentifier(data.text);
	}

	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	table.setAutoCreateRowSorter(true);

	return model;
    }

    @Override
    public Object getValueAt(final int row, final int column) {
	final RoomOffer roomOffer = rooms.get(row);

	switch (column) {
	case 0: {
	    return roomOffer.getIndex();
	}
	case 1: {
	    return roomOffer.getHotel();
	}
	case 2: {
	    return roomOffer.getCity();
	}
	case 3: {
	    return roomOffer.getRoomSize();
	}
	case 4: {
	    return (roomOffer.isSmokingAllowed());
	}
	case 5: {
	    return roomOffer.getPrice();
	}
	case 6: {
	    return roomOffer.getBookableDate();
	}
	case 7: {
	    return roomOffer.getCustomerId();
	}
	default: {
	    assert false : "" + column + " is not spezified!";
	    return null;
	}
	}
    }

    private final static class DateRenderer extends DefaultTableCellRenderer {

	@Override
	protected void setValue(final Object value) {
	    final Date date = (Date) value;
	    setText(convertDate(date));
	}

	private final static String convertDate(final Date date) {
	    return new SimpleDateFormat("yyyy/MM/dd", Locale.US).format(date);
	}
    }

    private final static class MoneyRenderer extends DefaultTableCellRenderer {

	@Override
	protected void setValue(final Object value) {

	    final Money money = (Money) value;
	    setText(convertMoney(money));
	}

	private final String convertMoney(final Money money) {
	    return money.getCurreny().getSymbol(Locale.US) + " "
		    + money.getAmount();
	}
    }

    private final static class BooleanRenderer extends DefaultTableCellRenderer {

	@Override
	protected void setValue(final Object value) {
	    final Boolean bool = (Boolean) value;
	    setText(convertSmokingBoolean(bool));
	}

	private final String convertSmokingBoolean(final boolean smoking) {
	    return (smoking) ? "Y" : "N";
	}
    }

}
