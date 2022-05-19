package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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

	public static boolean isFieldsAreEmptyChecker(boolean isSelected, String field1, String field2, String field3,
			String field4) {
		if (isSelected) {
			if (field3 == null) // for the delivery option
				return true;
			if (field1.isEmpty() || field2.isEmpty() || field3.isEmpty() || field4.isEmpty())
				return true;
		}
		return false;
	}

	public static boolean isDeliveryComboBoxChanged(String hour, String minutes, String region) {
		if (hour == null || minutes == null || region == null)
			return false;
		return true;
	}

	public static boolean isPickUpComboBoxChanged(Branch branch) {
		if (branch == null)
			return false;
		return true;
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
		if(Integer.parseInt(month)<=0 || Integer.parseInt(month)>12)
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
}
