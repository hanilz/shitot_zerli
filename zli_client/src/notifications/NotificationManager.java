package notifications;

import java.util.HashMap;

import client.ClientFormController;
import inputs.InputChecker;
import util.Commands;
import util.NotificationType;

//TODO in this class will be a method to send notification to from a user/system to a user
public class NotificationManager {
	private static HashMap<String, Object> message = new HashMap<>();
	
	
	/**
	 * gets the idUser of the user you want to send the notification to
	 * @param idUser
	 * @param text
	 * @return
	 */
	public static boolean sendNotification(int idUser,NotificationType notificationType, Object param) {
		message.clear();
		message.put("command", Commands.SEND_NOTIFICATION);
		message.put("idUser", idUser);
		String notification = getNotification(notificationType,param);
		message.put("notification", notification);
		Object response = ClientFormController.client.accept(message);
		return (boolean)response;
	}

	public static void markAsRead(Notification notifiction) {
		message.clear();
		message.put("command", Commands.MARK_READ_NOTIFICATION);
		message.put("idNotification", notifiction.getIdNotification());
		ClientFormController.client.accept(message);
	}
	
	public static void deleteNotification(Notification notifiction) {
		message.clear();
		message.put("command", Commands.DELETE_NOTIFICATION);
		message.put("idNotification", notifiction.getIdNotification());
		ClientFormController.client.accept(message);
	}
	
	protected static String getNotification(NotificationType notificationType, Object param) {
		switch(notificationType) {
		case COMPLAINT_DUE:
			return "Complaint Number: " + (int)param + " is Due, Please attend it as soon as possible.";
		case DELIVERY_LATE_REFUND:
			return "We are deeply sorry for the delivery delay you will be issued a " +InputChecker.price((Double)param) + " refund.";
		case ORDER_ACCEPTED:
			return "Congrats! Your order with order number "+ (int)param +" has been accepted and is now getting ready for delivery.";
		case ORDER_CANCELED_REFUND:
			return "We are deeply sorry for the delivery delay you will be issued a " +InputChecker.price((Double)param) + " refund.";
		case REGISTRATION_DISCOUNT:
			return "Congrats on completing your registration, you will be eligible for a 20% discount on your first order.";
		default:
			return "No such notification.";
		}
	}


}
