package com.pma.vaadin.component;

import java.util.Date;

import com.pma.util.DateFormatter;
import com.vaadin.data.Property;
import com.vaadin.ui.Table;

@SuppressWarnings("serial")
public class MPSRTable extends Table {

	@Override
	protected String formatPropertyValue(Object rowId, Object colId, Property property) {
		Object v = property.getValue();
		if (v instanceof Date) {
			Date dateValue = (Date) v;
			return DateFormatter.format2(dateValue);
		}
		return super.formatPropertyValue(rowId, colId, property);
	};

}
