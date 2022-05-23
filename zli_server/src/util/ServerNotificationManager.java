package util;

import notifications.NotificationManager;

public class ServerNotificationManager extends NotificationManager{
	
	public static boolean sendNotification(int idUser,NotificationType nt, Object param) {
		String notification = getNotification(nt,param);
		return AnalayzeCommand.sendNotification(idUser, notification);
	}
}
