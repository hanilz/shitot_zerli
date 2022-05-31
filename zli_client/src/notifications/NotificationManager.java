package notifications;

import java.util.HashMap;

import client.ClientFormController;
import entities.Notification;
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
			return "We are deeply sorry for the delivery delay on your order with order number "+(int)param+" you will be issued a full refund for your order.";
		case DELIVERY_CONFIRMATION:
			return "Your order with order number "+(int)param+" has been delivered!\nThank you for shopping with us, we hope to see you again soon!";
		case ORDER_ACCEPTED:
			return "Congratulations! Your order with order number "+ (int)param +" has been accepted and is now getting ready for delivery.";
		case ORDER_CANCELED_REFUND:
			return "We are deeply sorry for the delivery delay you will be issued a " +InputChecker.price((Double)param) + " refund.";
		case REGISTRATION_DISCOUNT:
			return "Congratulations on completing your registration!\nyou will be eligible for a 20% discount on your first order.";
		case CANCEL_REQUEST_DENIED:
			return "Unfortunately your request to cancel for order number "+ (int)param+" was denied, don't wory you will still get the order anyway.";
		case CANCEL_REQUEST_APPROVED:
			return "We are deeply sorry you didn't like your experience with us\nYou will be issued a "+InputChecker.price((Double)param)+ " store credit for your next order.";
		case ORDER_NOT_ACCEPTED:
			return "We are deeply sorry to inform you that your order with order number "+(int)param+" wasn't approved,\nyou will be issued a full refund in a form of a store credit for your next purchase ";
		default:
			return "Test Notifications.";
		}
	}


}
