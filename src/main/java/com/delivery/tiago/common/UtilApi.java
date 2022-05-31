package com.delivery.tiago.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UtilApi {
	
	
	public boolean isValidEmailAddressRegex(String email) {
		boolean isEmailIdValid = false;
		if (email != null && email.length() > 0) {
			String expressao = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			Pattern pattern = Pattern.compile(expressao, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(email);
			if (matcher.matches()) {
				isEmailIdValid = true;
			}
		}
		return isEmailIdValid;
	}

	
	public static DateTimeFormatter getDateFormater() {
		return DateTimeFormatter.ofPattern("yyyy-MM-dd");
	}
	

	public static DateTimeFormatter getDateTimeFormater() {
		return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	}
	

	public static LocalDateTime getLocalDateTimeFromString(String dateAsString) throws ParseException{
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date dateISO8601 = inputFormat.parse(dateAsString);
        return dateISO8601.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
	
	public static LocalDateTime convertLocalDateToLocalDateTime(LocalDate date) {
		return date.atTime(0, 0, 0);
	}
}
