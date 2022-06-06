package util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Using this class, the application can send emails (for exmaple: if the manager accepted the order, and etc).
 * It's saves the details of the host (using gmail), user of the email and password.
 */
public class MailService {
	private static String host = "smtp.gmail.com";
	private static String user = "zlimessager@gmail.com";
	private static String password = "shitotbraude123@";

	/**
	 * Using sendEmailToUser, it uses the activation and mail API to send the email to the customer using zli email.
	 * @param userEmail
	 * @param emailSubject
	 * @param message
	 */
	public static void sendEmailToUser(String userEmail, String emailSubject, String message) {
		try {
			boolean sessionDebug = false;
			Properties props = configureEmailAPI();

			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, password);
				}
			});
			session = Session.getInstance(props);
			session.setDebug(sessionDebug);
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(user));
			InternetAddress[] address = { new InternetAddress(userEmail) };
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(emailSubject);
			msg.setText(message);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, user, password);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * To configure activation and mail API to use sendEmailToUser method.
	 * @return props of the current system.
	 */
	private static Properties configureEmailAPI() {
		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "host");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.required", "true");

		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		return props;
	}

}
