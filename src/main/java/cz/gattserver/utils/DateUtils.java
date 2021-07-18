package cz.gattserver.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public final class DateUtils {

	public static final String DATE_FORMAT_PATTERN = "d.M.yyyy";
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
	
	private DateUtils() {
	}

	public static Date resetTime(Date date) {
		return org.apache.commons.lang3.time.DateUtils.truncate(date, Calendar.DATE);
	}

	public static Date resetTimeToMidnight(Date date) {
		return org.apache.commons.lang3.time.DateUtils.addSeconds(org.apache.commons.lang3.time.DateUtils
				.addMinutes(org.apache.commons.lang3.time.DateUtils.addHours(resetTime(date), 23), 59), 59);
	}

	public static LocalDate toLocalDate(Date date) {
		return date == null ? null : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static java.sql.Date toDate(LocalDate locDate) {
		return locDate == null ? null : java.sql.Date.valueOf(locDate);
	}
}
