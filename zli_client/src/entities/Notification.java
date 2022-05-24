package entities;

import java.io.Serializable;

public class Notification implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1265799512492593120L;
	
	private int idNotification;
	private int idUser;
	private boolean isRead;
	private String title;
	
	public Notification(int idNotification, int idUser, boolean isRead, String title) {
		this.idNotification = idNotification;
		this.idUser = idUser;
		this.isRead = isRead;
		this.title = title;
	}

	/**
	 * @return the idNotification
	 */
	public int getIdNotification() {
		return idNotification;
	}

	/**
	 * @param idNotification the idNotification to set
	 */
	public void setIdNotification(int idNotification) {
		this.idNotification = idNotification;
	}

	/**
	 * @return the idUser
	 */
	public int getIdUser() {
		return idUser;
	}

	/**
	 * @param idUser the idUser to set
	 */
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	/**
	 * @return the isRead
	 */
	public boolean isRead() {
		return isRead;
	}

	/**
	 * @param isRead the isRead to set
	 */
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
}
