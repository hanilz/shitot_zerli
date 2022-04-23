package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InputChecker {
	public static boolean checkDateFormat(String date) {
		if (!date.contains(":")) // if the date not contains the time
			return false;
		if (!date.contains("-")) // if the date not contains the date
			return false;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if (isValidDate(format, date))
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	private static boolean isValidDate(SimpleDateFormat format, String input) {
		try {
			format.parse(input);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
}
