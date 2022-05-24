package entities;

import java.io.Serializable;

public class Report implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idReport;
	private String type;
	private String date;
	private int idBranch;
	
	public Report(int idReport, String type, String date, int idBranch) {
		this.idReport = idReport;
		this.type = type;
		this.date = date;
		this.idBranch = idBranch;
	}

	public Report(String type, String date, int idBranch) {
		super();
		this.type = type;
		this.date = date;
		this.idBranch = idBranch;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getIdBranch() {
		return idBranch;
	}

	public void setIdBranch(int idBranch) {
		this.idBranch = idBranch;
	}
}
