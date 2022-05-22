package util;

import notifications.NotificationManager;

public class ServerNotificationManager extends NotificationManager{
	
	public static boolean sendNotification(int idUser,NotificationType nt, int param) {
		String notification = getNotification(nt,param);
		return AnalayzeCommand.sendNotification(idUser, notification);
	}
}
