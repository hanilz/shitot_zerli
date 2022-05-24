package entities;

import java.io.Serializable;
import java.util.Date;

public class Report implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idReport;
	private String type;
	private Date date;
	private String dateToString;
	private int idBranch;
	private int quarter;
	private String year;

	public Report(int idReport, String type, Date date, int idBranch, int quarter) {
		this.idReport = idReport;
		this.type = type;
		this.date = date;
		this.dateToString = date.toString();
		this.year = dateToString.substring(0, 4);
		this.idBranch = idBranch;
		this.quarter = quarter;
	}

	public Report(int idBranch, String year, int quarter) {
		this.idBranch = idBranch;
		this.year = year;
		this.quarter = quarter;
	}

	public Report(String type, Date date, int idBranch) {
		this.type = type;
		this.date = date;
		this.idBranch = idBranch;
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

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Report))
			return false;

		Report checkReport = (Report) obj;
		return checkReport.idBranch == idBranch && checkReport.year.equals(year) && checkReport.quarter == quarter;
	}

}
