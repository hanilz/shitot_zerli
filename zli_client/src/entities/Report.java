package entities;

import java.io.Serializable;
import java.time.Month;
import java.util.Date;

/**
 * This class saves the data of the report after receiving the information from the database.
 * Provides setters and getters.
 */
public class Report implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idReport;
	/**
	 * Saves the report type.
	 */
	private String type;
	private Date date;
	/**
	 * Convert the date to string.
	 */
	private String dateToString;
	private int idBranch;
	private int quarter;
	private String year;
	/**
	 * Saves the dateRange for the query.
	 */
	private String dateRange;
	/**
	 * Saving all the information of the branch (and not only the FK of the branch).
	 */
	private Branch branch;
	/**
	 * Saving all the information of the user to check the user type to init the relevant reports.
	 */
	private User user;
	/**
	 * Saving the name of the given month.
	 */
	private String month;
	
	public Report(String dateRange, String type, int idBranch, Date date) {
		this.dateRange = dateRange;
		this.type = type;
		this.idBranch = idBranch;
		this.user = User.getUserInstance();
		this.dateToString = date.toString();
		int currentMonth = Integer.parseInt(dateToString.substring(5,7));
		this.month = Month.of(currentMonth).name();
	}
	
	public Report(String dateRange, String type, int idBranch) {
		this.dateRange = dateRange;
		this.type = type;
		this.idBranch = idBranch;
		this.user = User.getUserInstance();
	}

	public Report(int idReport, String type, Date date, int idBranch, int quarter) {
		this.idReport = idReport;
		this.type = type;
		this.date = date;
		this.dateToString = date.toString();
		this.year = dateToString.substring(0, 4);
		this.idBranch = idBranch;
		this.quarter = quarter;
		this.user = User.getUserInstance();
		int currentMonth = Integer.parseInt(dateToString.substring(5,7));
		this.month = Month.of(currentMonth).name();
	}

	public Report(int idBranch, String year, int quarter) {
		this.idBranch = idBranch;
		this.year = year;
		this.quarter = quarter;
		this.user = User.getUserInstance();
	}

	public Report(String type, Date date, int idBranch) {
		this.type = type;
		this.date = date;
		this.idBranch = idBranch;
		this.user = User.getUserInstance();
		int currentMonth = Integer.parseInt(dateToString.substring(5,7));
		this.month = Month.of(currentMonth).name();
	}

	/**
	 * Converts the integer of the current month to String.
	 */
	public void setMonth() {
		int currentMonth = Integer.parseInt(dateToString.substring(5,7));
		this.month = Month.of(currentMonth).name();
	}

	public String getMonth() {
		return month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getQuarter() {
		return quarter;
	}

	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}

	public int getIdReport() {
		return idReport;
	}

	public void setIdReport(int idReport) {
		this.idReport = idReport;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getIdBranch() {
		return idBranch;
	}

	public String getDateToString() {
		return dateToString;
	}

	public void setIdBranch(int idBranch) {
		this.idBranch = idBranch;
	}

	public String getDateRange() {
		return dateRange;
	}

	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public User getUser() {
		return user;
	}

	/**
	 * Checking by a given report, if it's the same idBranch, year and quarter.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Report))
			return false;

		Report checkReport = (Report) obj;
		return checkReport.idBranch == idBranch && checkReport.year.equals(year) && checkReport.quarter == quarter;
	}

}
