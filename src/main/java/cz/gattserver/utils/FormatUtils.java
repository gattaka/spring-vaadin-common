package cz.gattserver.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

public class FormatUtils {

	public static final NumberFormat priceFormat = NumberFormat.getCurrencyInstance(new Locale("cs", "CZ"));
	public static final NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("cs", "CZ"));

	static {
		numberFormat.setGroupingUsed(false);	
	}
	
	private FormatUtils() {
	}

	public static String formatMoney(BigDecimal money) {
		if (money == null)
			return null;
		((DecimalFormat) priceFormat).setMaximumFractionDigits(2);
		((DecimalFormat) priceFormat).setMinimumFractionDigits(2);
		return priceFormat.format(money);
	}

	public static String formatDouble(Double number) {
		if (number == null)
			return null;
		return numberFormat.format(number);
	}

	public static String formatDoubleOrEmpty(Double val) {
		if (val == null)
			return "";
		return FormatUtils.formatDouble(val);
	}

	public static String formatInteger(Integer number) {
		if (number == null)
			return null;
		return numberFormat.format(number);
	}

	public static String formatIntegerOrEmpty(Integer val) {
		if (val == null)
			return "";
		return FormatUtils.formatInteger(val);
	}

	public static boolean isNumber(String number) {
		if (StringUtils.isBlank(number))
			return true;
		try {
			parseDouble(number);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	public static boolean isInRange(Double val, Double min, Double max) {
		if (min != null && val < min)
			return false;
		if (max != null && val > max)
			return false;
		return true;
	}

	public static boolean isInRange(Integer val, Integer min, Integer max) {
		if (min != null && val < min)
			return false;
		if (max != null && val > max)
			return false;
		return true;
	}

	public static Double parseDouble(String number) throws ParseException {
		if (StringUtils.isBlank(number))
			return null;
		ParsePosition position = new ParsePosition(0);
		Number parsed = numberFormat.parse(number, position);
		if (position.getIndex() != number.length())
			throw new ParseException("Celý text není číslo", position.getIndex());
		return parsed.doubleValue();
	}

	public static Double parseDoubleOrNull(String number) {
		try {
			return parseDouble(number);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Integer parseInteger(String number) throws ParseException {
		if (StringUtils.isBlank(number))
			return null;
		ParsePosition position = new ParsePosition(0);
		Number parsed = numberFormat.parse(number, position);
		if (position.getIndex() != number.length())
			throw new ParseException("Celý text není číslo", position.getIndex());
		return parsed.intValue();
	}

	public static Integer parseIntegerOrNull(String number) {
		try {
			return parseInteger(number);
		} catch (ParseException e) {
			return null;
		}
	}
}