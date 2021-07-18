package cz.gattserver.utils;

import java.text.NumberFormat;
import java.util.Locale;

import com.vaadin.data.util.converter.StringToDoubleConverter;

public class CZStringToDoubleConverter extends StringToDoubleConverter {

	private static final long serialVersionUID = 7856125792023813252L;

	@Override
	protected NumberFormat getFormat(Locale locale) {
		return FormatUtils.numberFormat;
	}

}
