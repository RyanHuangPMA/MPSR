package com.kohmiho.vaadin.converter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import com.vaadin.data.util.converter.AbstractStringToNumberConverter;

/**
 * This version will be added into Vaadin 7.2. Delete this when Vaadin 7.2 is
 * released. This is for Oracle database.
 * 
 * @author TDD13
 * 
 */

@SuppressWarnings("serial")
public class StringToBigDecimalConverter extends AbstractStringToNumberConverter<BigDecimal> {
	@Override
	protected NumberFormat getFormat(Locale locale) {
		NumberFormat numberFormat = super.getFormat(locale);
		if (numberFormat instanceof DecimalFormat) {
			((DecimalFormat) numberFormat).setParseBigDecimal(true);
		}

		return numberFormat;
	}

	@Override
	public BigDecimal convertToModel(String value, Class<? extends BigDecimal> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		return (BigDecimal) convertToNumber(value, BigDecimal.class, locale);
	}
	

	@Override
	public Class<BigDecimal> getModelType() {
		return BigDecimal.class;
	}
}
