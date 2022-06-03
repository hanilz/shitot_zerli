package util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entities.Branch;
import entities.User;
import entities.UserDetails;

public class ImportUsers {
	private static ArrayList<UserDetails> userDetails = new ArrayList<>();
	private static ArrayList<User> users = new ArrayList<>();
	
	public static String importExternalDB() {
		return (importUserDetails() && importUsers()) ? "\nSuccessfully imported users from externalDB.\n Please connect to Zli DB.\n" : "\nFailed to import users from externalDB\n";
	}
	
	private static boolean importUserDetails() {
		try {
			Statement selectStmt = DataBaseController.getConn().createStatement();
			ResultSet rs = selectStmt.executeQuery(
					"SELECT id_user,firstName, lastName, id, email, phoneNumber, idBranch FROM externaldb.external_users;");
			while (rs.next()) {
				UserDetails userResultSet = new UserDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
				userResultSet.setBranches(rs.getString(7));
				userDetails.add(userResultSet);
			}
			rs.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private static boolean importUsers() {
		try {
			Statement selectStmt = DataBaseController.getConn().createStatement();
			ResultSet rs = selectStmt.executeQuery(
					"SELECT username, password, userType, storeCredit FROM externaldb.external_users;");
			while (rs.next()) {
				User userResultSet = new User(rs.getString(1), rs.getString(2), UserType.get(rs.getString(3)), rs.getDouble(4));
				users.add(userResultSet);
			}
			rs.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private static boolean isImported() {
		try {
			Statement selectStmt = DataBaseController.getConn().createStatement();
			ResultSet rs = selectStmt.executeQuery(
					"SELECT id FROM zli.user_details;");
			while (rs.next()) {
				for(UserDetails currentUser : userDetails)
					if(currentUser.getId().equals(rs.getString(1)))
						return true;
			}
			rs.close();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static String insertIntoZliDB() {
		if(!isImported()) {
			insertUserDetailsIntoZli();
			insertUsersTableIntoZli();
			insertBranchesIntoZli();
			return "\n Branches added to Zli DB \n Users added to Zli DB\n";
		}
		return "\n Users already added to Zli DB\n";
	}
	
	private static void insertUserDetailsIntoZli() {
		try {
			Statement stmt;
			String query = "INSERT INTO user_details (idAccount, firstName, lastName, id, email, phoneNumber) VALUES ";
			StringBuffer buffer = new StringBuffer(query);
			for(UserDetails currentDetails : userDetails)
				buffer.append("("+currentDetails.getIdAccount()+",'"+currentDetails.getFirstName()+"','"+currentDetails.getLastName()+"','"+currentDetails.getId()+"','"+currentDetails.getEmail()+"','"+currentDetails.getPhoneNumber()+"'), ");
			buffer.delete(buffer.toString().length()-2, buffer.toString().length());
			buffer.append(";");
			stmt = DataBaseController.getConn().createStatement();
			stmt.executeUpdate(buffer.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void insertBranchesIntoZli() {
		try {
			ArrayList<Branch> branchesList = new ArrayList<>();
			branchesList.add(new Branch(1, "Tirat Carmel", "Hertzel 54", "North"));
			branchesList.add(new Branch(2, "Tel Aviv", "Alenbi 15", "Center"));
			branchesList.add(new Branch(3, "Beer Sheva", "Ben Gurion 5", "South"));
			Statement stmt;
			String query = "INSERT INTO branches (idBranch,city, address, region, idManager) VALUES ";
			StringBuffer buffer = new StringBuffer(query);
			for(Branch currentBranch : branchesList) {
				for(UserDetails currentDetails : userDetails) {
					String[] branchesOfUser = currentDetails.getBranches().split(",");
					for(int i=0; i<branchesOfUser.length; i++) {
						if(Integer.parseInt(branchesOfUser[i]) == currentBranch.getIdBranch()) {
							buffer.append("("+currentBranch.getIdBranch()+",'"+currentBranch.getCity()+"','"+currentBranch.getAddress()+"','"+currentBranch.getRegion()+"',"+currentDetails.getIdAccount()+"), ");
							continue;
						}
					}
				}
			}
			buffer.delete(buffer.toString().length()-2, buffer.toString().length());
			buffer.append(";");
			stmt = DataBaseController.getConn().createStatement();
			System.out.println(buffer.toString());
			stmt.executeUpdate(buffer.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void insertUsersTableIntoZli() {
		try {
			Statement stmt;
			int i=0;
			int primaryKey = 1;
			String query = "INSERT INTO users (idUser, USERNAME, PASSWORD, idAccount, userType, isLogin, status, storeCredit) VALUES ";
			StringBuffer buffer = new StringBuffer(query);
			for(User currentUser : users)
				buffer.append("("+(primaryKey++)+",'"+currentUser.getUsername()+"','"+currentUser.getPassword()+"',"+userDetails.get(i++).getIdAccount()+",'"+currentUser.getType().toString()+"',"+currentUser.isUserLoggedIn()+",'Active',"+currentUser.getStoreCredit()+"), ");
			buffer.delete(buffer.toString().length()-2, buffer.toString().length());
			buffer.append(";");
			stmt = DataBaseController.getConn().createStatement();
			stmt.executeUpdate(buffer.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}