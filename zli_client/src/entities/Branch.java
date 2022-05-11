package entities;

import java.io.Serializable;

public class Branch implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idBranch;
	private String city;
	private String address;
	private String region;
	
	public Branch(int idBranch, String city, String address, String region) {
		this.idBranch = idBranch;
		this.city = city;
		this.address = address;
		this.region = region;
	}
	
	public int getIdBranch() {
		return idBranch;
	}

	public void setIdBranch(int idBranch) {
		this.idBranch = idBranch;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Override
	public String toString() {
		return String.format("%s, %s", address, city);
	}
	
}
