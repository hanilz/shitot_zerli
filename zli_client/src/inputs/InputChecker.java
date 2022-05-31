package inputs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import entities.Branch;

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

	public static boolean isNull(Object object) {
		return object == null;
	}

	private static boolean isValidDate(SimpleDateFormat format, String input) {
		try {
			format.parse(input);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	public static String price(double amount) {// setting price of num+NIS
		int a = 0x20AA;
		String price = amount + " " + Character.toString((char) a);
		return price;
	}

	public static boolean isFieldsAreEmptyChecker(String field1, String field2, String field3) {
		if (field1.isEmpty() || field2.isEmpty() || field3.isEmpty())
			return true;
		return false;
	}

	public static boolean isDeliveryComboBoxChanged(String hour, String minutes, Branch branch) {
		if (hour == null || minutes == null || branch == null)
			return false;
		return true;
	}

	public static boolean isFieldsEmpty(String field) {
		if (field.isEmpty())
			return true;
		return false;
	}

	// for delivery screen
	public static boolean isBranchNotNull(Branch branch) {
		if (branch == null)
			return false;
		return true;
	}

	// for delivery screen
	public static boolean isStringNone(String str) {
		return str.contains("None");
	}

	public static boolean isPaymentFieldsEmpty(String firstName, String lastName, String cardNumber, String month,
			String year, String vcc, String id) {
		if (firstName.isEmpty() || lastName.isEmpty() || cardNumber.isEmpty() || month.isEmpty() || year.isEmpty()
				|| vcc.isEmpty() || id.isEmpty())
			return true;
		return false;
	}

	public static boolean checkPaymentInput(String firstName, String lastName, String cardNumber, String month,
			String year, String vcc, String id) {
		if (!cardNumber.matches("[0-9]+") || !month.matches("[0-9]+") || !vcc.matches("[0-9]+")
				|| !id.matches("[0-9]+"))
			return false;
		else if (cardNumber.length() != 16 || month.length() != 2 || year.length() != 2 || vcc.length() != 3
				|| id.length() != 9)
			return false;
		else if (!firstName.matches("^[ A-Za-z]+$") || !lastName.matches("^[ A-Za-z]+$"))
			return false;
		if (Integer.parseInt(month) <= 0 || Integer.parseInt(month) > 12)
			return false;
		return true;
	}

	public static boolean isGreetingCardInputVaild(String from, String to) {
		if (!from.matches("^[ A-Za-z]+$") || !to.matches("^[ A-Za-z]+$"))
			return false;
		return true;
	}

	public static boolean isDeliveryInputValid(String phoneNumber, String recieverName) {
		if (!recieverName.matches("^[ A-Za-z]+$"))
			return false;
		else if (!phoneNumber.matches("[0-9]+"))
			return false;
		return true;
	}

	public static boolean isPhoneNumberVaild(String phoneNumber) {
		if (phoneNumber.length() == 10 && phoneNumber.matches("[0-9]+")) // if mobile
			return true;
		else if (phoneNumber.length() == 9 && phoneNumber.matches("[0-9]+")) // if regular phone number
			return true;
		return false;
	}

	public static boolean checkMonthCardDate(String month) {
		if (month.matches("^(1[0-2]|[1-9])$"))
			return true;
		return false;
	}

	public static boolean checkID(String id) {
		if (id.matches("[0-9]+") && id.length() == 9)
			return true;
		return false;

	}


	public static boolean isDateBeforeNow(String dateTime) {
		boolean response = false;
		try {
			response = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime).before(new Date());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return response;
	}

	public static boolean areDateTimeFieldsNotSelected(LocalDate date, String hour, String minutes) {
		return (isNull(date) || isNull(hour) || isNull(minutes));
	}

	public static boolean areGreetingCardFieldsEmptyChecker(boolean selected, String text, String text2, String text3,
			String text4) {
		return selected ? (text.isEmpty() || text2.isEmpty() || text3.isEmpty() || text4.isEmpty()) : false;
	}

	public static boolean isInteger(String check) {
		try {
			Integer.parseInt(check);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isDouble(String check) {
		try {
			Double.parseDouble(check);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isContainsLetters(String check) {
		if (check.matches("^[ A-Za-z]+$"))
			return true;
		return false;
	}
}
