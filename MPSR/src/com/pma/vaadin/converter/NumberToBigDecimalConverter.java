package com.pma.vaadin.converter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

@SuppressWarnings("serial")
public class NumberToBigDecimalConverter implements Converter<String, BigDecimal> {

	public static NumberFormat nf = NumberFormat.getNumberInstance();
	static {
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(0);
	}

	@Override
	public BigDecimal convertToModel(String value, Class<? extends BigDecimal> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		if (null == value)
			return null;

		Number parse = 0;
		try {
			parse = nf.parse(value);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return new BigDecimal(parse.doubleValue());
	}

	@Override
	public String convertToPresentation(BigDecimal value, Class<? extends String> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		if (null == value)
			return null;

		return nf.format(value);
	}

	@Override
	public Class<BigDecimal> getModelType() {
		return BigDecimal.class;
	}

	@Override
	public Class<String> getPresentationType() {
		return String.class;
	}

}
