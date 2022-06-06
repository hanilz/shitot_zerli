package inputs;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import entities.Branch;

/**
 * This class checking the input that given by the user in the screens.
 * Most of the functions are boolean. returns true or false based on the user input.
 */
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

	/**
	 * Checking if the object is null.
	 * @param object
	 * @return true or false based on the object that given.
	 */
	public static boolean isNull(Object object) {
		return object == null;
	}
	
	/**
	 * Checking if the payment input is valid based on the parameters that given to the method.
	 * It's checks if the cardNumber, month, year and id contains only numbers with a relevant length.
	 * And it's checks if the first and last name contains only letters and spaces.
	 * @param firstName
	 * @param lastName
	 * @param cardNumber
	 * @param month
	 * @param year
	 * @param vcc
	 * @param id
	 * @return true or false based on the parameters.
	 */
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

	/**
	 * Checking if the date is valid by the date foramt and the input that given.
	 * @param format
	 * @param input
	 * @return true or false based on the parameters.
	 */
	private static boolean isValidDate(SimpleDateFormat format, String input) {
		try {
			format.parse(input);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * By the double that given, it returns a price with NIS symbol.
	 * @param amount
	 * @return price in format of num+NIS symbol
	 */
	public static String price(double amount) {// setting price of num+NIS
		int a = 0x20AA;
		DecimalFormat df = new DecimalFormat("0.##");
		String price = df.format(amount) + " " + Character.toString((char) a);
		return price;
	}

	/**
	 * Checking if the strings that given are empty.
	 * @param field1
	 * @param field2
	 * @param field3
	 * @return true or false based on the parameters.
	 */
	public static boolean isFieldsAreEmptyChecker(String field1, String field2, String field3) {
		if (field1.isEmpty() || field2.isEmpty() || field3.isEmpty())
			return true;
		return false;
	}

	/**
	 * Checking of the delivery ComboBox in the delivery options is changed.
	 * @param hour
	 * @param minutes
	 * @param branch
	 * @return true or false based on the parameters.
	 */
	public static boolean isDeliveryComboBoxChanged(String hour, String minutes, Branch branch) {
		if (hour == null || minutes == null || branch == null)
			return false;
		return true;
	}

	/**
	 * Checking if the string is empty.
	 * @param field
	 * @return true or false based on field.
	 */
	public static boolean isFieldsEmpty(String field) {
		if (field.isEmpty())
			return true;
		return false;
	}

	/**
	 * For delivery options screen, it checking if the user selected a branch.
	 * @param branch
	 * @return true or false based on the branch that given.
	 */
	public static boolean isBranchNotNull(Branch branch) {
		if (branch == null)
			return false;
		return true;
	}

	/**
	 * For delivery options screen, checking if the string is contains none.
	 * @param str
	 * @return true or false based on the String.
	 */
	public static boolean isStringNone(String str) {
		return str.contains("None");
	}

	/**
	 * Checking if the input in the greeting card contains only letters and spaces.
	 * @param from
	 * @param to
	 * @return true or false based on the parameters.
	 */
	public static boolean isGreetingCardInputVaild(String from, String to) {
		if (!from.matches("^[ A-Za-z]+$") || !to.matches("^[ A-Za-z]+$"))
			return false;
		return true;
	}
	
	/**
	 * Checking the length of the greeting card input.
	 * @param from
	 * @param to
	 * @param greetingCard
	 * @return true or false based on the parameters.
	 */
	public static boolean isGreetingCardInputNotLong(String from, String to, String greetingCard) {
		if (!(from.length() < 40) || !(to.length() < 40) || !(greetingCard.length() < 150))
			return false;
		return true;
	}

	/**
	 * @param phoneNumber
	 * @param recieverName
	 * @return true or false based on the parameters.
	 */
	public static boolean isDeliveryInputValid(String phoneNumber, String recieverName) {
		if (!recieverName.matches("^[ A-Za-z]+$"))
			return false;
		else if (!phoneNumber.matches("[0-9]+"))
			return false;
		return true;
	}

	/**
	 * Checking if the phoneNumber string is a valid phone number. (contains numbers and 10 or 9 numbers).
	 * @param phoneNumber
	 * @return true or false based on the phoneNumber String.
	 */
	public static boolean isPhoneNumberVaild(String phoneNumber) {
		if (phoneNumber.length() == 10 && phoneNumber.matches("[0-9]+")) // if mobile
			return true;
		else if (phoneNumber.length() == 9 && phoneNumber.matches("[0-9]+")) // if regular phone number
			return true;
		return false;
	}

	/**
	 * Checking if the month of the card is a valid input (contains only numbers).
	 * @param month
	 * @return true or false based on the month String.
	 */
	public static boolean checkMonthCardDate(String month) {
		if (month.matches("^(1[0-2]|[1-9])$"))
			return true;
		return false;
	}

	/**
	 * Checking if the string is a valid ID (contains only number and only 9 numbers).
	 * @param id
	 * @return true or false based on the id.
	 */
	public static boolean checkID(String id) {
		if (id.matches("[0-9]+") && id.length() == 9)
			return true;
		return false;
	}

	/**
	 * Checking if the String that given, is a valid number (Statring from 0).
	 * @param number
	 * @return true or false based on the number.
	 */
	public static boolean isValidNubmer(String number) {
		if (number.matches("[0-9]+"))
			return true;
		return false;
	}

	/**
	 * Checking if the date of the delivery that selected by the user is it revelant to the current date.
	 * @param dateTime
	 * @return true or false based on the dateTime
	 */
	public static boolean isDateBeforeNow3Hours(String dateTime) {
		boolean response = false;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR_OF_DAY, 3);
		try {
			response = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime).before(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * Checking if the fields of date, hour and minutes are selected or not.
	 * @param date
	 * @param hour
	 * @param minutes
	 * @return true or false based on the parameters.
	 */
	public static boolean areDateTimeFieldsNotSelected(LocalDate date, String hour, String minutes) {
		return (isNull(date) || isNull(hour) || isNull(minutes));
	}

	/**
	 * Checking if the fields of the greeting card are empty.
	 * @param selected
	 * @param text
	 * @param text2
	 * @return true or false based on the parameters.
	 */
	public static boolean areGreetingCardFieldsEmptyChecker(boolean selected, String text, String text2) {
		return selected ? (text.isEmpty() || text2.isEmpty()) : false;
	}

	/**
	 * Checking if the String check is Integer value.
	 * @param check
	 * @return true or false based on the check.
	 */
	public static boolean isInteger(String check) {
		try {
			Integer.parseInt(check);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Checking if the String check is double value.
	 * @param check
	 * @return true or false based on the check.
	 */
	public static boolean isDouble(String check) {
		try {
			Double.parseDouble(check);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Checking if the String check contains latters and spaces, returns true if it's contains.
	 * @param check
	 * @return true or false based on the check.
	 */
	public static boolean isContainsLetters(String check) {
		if (check.matches("^[ A-Za-z]+$"))
			return true;
		return false;
	}
}
