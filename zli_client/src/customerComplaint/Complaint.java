package customerComplaint;

import java.io.Serializable;

public class Complaint implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4951050968131626674L;
	private int complaintID;
	private int orderID;
	private int idUser;
	private String date;
	private String status;
	private String complaintReason;
	private String complaintContent;
	private int remainingMinutes;
	

	
	public Complaint(int complaintID,int idUser, int orderID, int date, String status, String complaintReason,
			String complaintContent) {
		this.complaintID = complaintID;
		this.orderID = orderID;
		this.date = getRemainingTime(date);
		this.remainingMinutes= 24*60 - date;
		this.status = status;
		this.complaintReason = complaintReason;
		this.complaintContent = complaintContent;
		this.idUser=idUser;
	}
	
	private String getRemainingTime(int date) {
		int minutes = date%60;
		int hours = date/60;
		if(hours<24) {
			return String.format("%02d:%02d",23-hours,59-minutes);
		}
		return "DUE";
	}

	/**
	 * @return the complaintID
	 */
	public int getComplaintID() {
		return complaintID;
	}

	/**
	 * @param complaintID the complaintID to set
	 */
	public void setComplaintID(int complaintID) {
		this.complaintID = complaintID;
	}

	/**
	 * @return the orderID
	 */
	public int getOrderID() {
		return orderID;
	}

	/**
	 * @param orderID the orderID to set
	 */
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the complaintReason
	 */
	public String getComplaintReason() {
		return complaintReason;
	}

	/**
	 * @param complaintReason the complaintReason to set
	 */
	public void setComplaintReason(String complaintReason) {
		this.complaintReason = complaintReason;
	}

	/**
	 * @return the complaintContent
	 */
	public String getComplaintContent() {
		return complaintContent;
	}

	/**
	 * @param complaintContent the complaintContent to set
	 */
	public void setComplaintContent(String complaintContent) {
		this.complaintContent = complaintContent;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the remainingMinutes
	 */
	public int getRemainingMinutes() {
		return remainingMinutes;
	}

	/**
	 * @param remainingMinutes the remainingMinutes to set
	 */
	public void setRemainingMinutes(int remainingMinutes) {
		this.remainingMinutes = remainingMinutes;
	}

	public void decreaseRemainingTime() {
		remainingMinutes-=1;
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

	
	
	
	
	
//	public static void main(String[] args) {
//		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
//	    Date firstDate = null;
//	    Date secondDate = new Date(System.currentTimeMillis());
//		try {
//			firstDate = sdf.parse("05/17/2022 12:00:00");
//			secondDate = sdf.parse("06/30/2017 14:12:12");
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		System.out.println(getRemainingTime(firstDate));
//	}
	
//	LocalDateTime firstDate = date;
//	//Date secondDate = new Date(System.currentTimeMillis());
//	LocalDateTime secondDate = LocalDateTime.now();
//    System.out.println(firstDate);
//    System.out.println(secondDate);
//    long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getLong(null));
//    long hourDiff = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
//    long MinuteDiff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
//    System.out.println(String.format("%02d:%02d",24-hourDiff,60-MinuteDiff%60));
//    
//    if(hourDiff<24)
//    	return String.format("%02d:%02d",hourDiff,MinuteDiff%60);
//	return "DUE";
}
