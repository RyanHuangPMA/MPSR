package com.kohmiho.util;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

public class NumberFormatter {

	private static final NumberFormat formatter1 = new DecimalFormat("#,##0.00");
	private static final NumberFormat formatter2 = new DecimalFormat("0");
	private static final NumberFormat formatter3 = new DecimalFormat("0.00");

	public static Number parse1(String numberStr) throws ParseException {
		return formatter1.parse(numberStr);
	}

	public static Number parse2(String numberStr) throws ParseException {
		return formatter2.parse(numberStr);
	}

	public static Number parse3(String numberStr) throws ParseException {
		return formatter3.parse(numberStr);
	}

	public static String format1(Number num) {
		return formatter1.format(num);
	}

	public static String format2(Number num) {
		return formatter2.format(num);
	}

	public static String format3(Number num) {
		return formatter3.format(num);
	}

	public static void main(String[] args) throws ParseException {
		System.out.println(NumberFormatter.format1(32333344200.496));
		System.out.println(NumberFormatter.parse1("32,333,344,200.00"));
		System.out.println(NumberFormatter.format2(32333344200.0001));

	}
}
