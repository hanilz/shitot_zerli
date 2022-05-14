package customerComplaint;


public class Complaint {
	private int complaintID;
	private String status;
	private String complainerName;
	private String cmplaintReason;
	private int remainingTime;
	//private String complaint;
	public Complaint(int complaintID, String status, String complainerName, String cmplaintReason, int remainingTime) {

		this.complaintID = complaintID;
		this.status = status;
		this.complainerName = complainerName;
		this.cmplaintReason = cmplaintReason;
		this.remainingTime = remainingTime;
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
	 * @return the complainerName
	 */
	public String getComplainerName() {
		return complainerName;
	}
	/**
	 * @param complainerName the complainerName to set
	 */
	public void setComplainerName(String complainerName) {
		this.complainerName = complainerName;
	}
	/**
	 * @return the cmplaintReason
	 */
	public String getCmplaintReason() {
		return cmplaintReason;
	}
	/**
	 * @param cmplaintReason the cmplaintReason to set
	 */
	public void setCmplaintReason(String cmplaintReason) {
		this.cmplaintReason = cmplaintReason;
	}
	/**
	 * @return the remainingTime
	 */
	public int getRemainingTime() {
		return remainingTime;
	}
	/**
	 * @param remainingTime the remainingTime to set
	 */
	public void setRemainingTime(int remainingTime) {
		this.remainingTime = remainingTime;
	}
	
	
//	public static void main(String[] args) {
//		String str = "sadf : hfdfg : dfhghf : dfg";
//		String[] strs = str.split(" : ");
//		String str2 = str.substring(str.indexOf(':', 3));
//		System.out.println(strs[3]);
//	}
	
}
