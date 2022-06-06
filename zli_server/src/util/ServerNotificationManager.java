package util;

import notifications.NotificationManager;

/**
 * This a subclass of the NotificationManager (so the ServerNotificationManager
 * will have access to all the methods and properties of the superclass). This
 * class handles the insert of the notification into the database.
 *
 */
public class ServerNotificationManager extends NotificationManager {

	/**
	 * The function will get the string of the notification based on the type and
	 * the parameter that given and it will insert it into the database.
	 * 
	 * @param idUser
	 * @param notificationType
	 * @param param
	 * @return true or false depeding if SQLException occurred.
	 */
	public static boolean sendNotification(int idUser, NotificationType notificationType, Object param) {
		String notification = getNotification(notificationType, param);
		return AnalayzeCommand.sendNotification(idUser, notification);
	}
}
