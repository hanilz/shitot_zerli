package util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entities.Branch;
import entities.User;
import entities.UserDetails;

/**
 * ImportUsers first saves the users from the externaldb and then saves the user
 * into the zli db.
 *
 */
public class ImportUsers {
	/**
	 * userDetails saves the userDetails from the externaldb.
	 */
	private static ArrayList<UserDetails> userDetails = new ArrayList<>();
	/**
	 * users saves the users from the externldb (the username, password, etc).
	 */
	private static ArrayList<User> users = new ArrayList<>();

	/**
	 * If the user connected to the externalDB, it will import the userDetails and
	 * the users and it will save the information from the db to the lists.
	 * 
	 * @return String depending if it's succeded or not.
	 */
	public static String importExternalDB() {
		return (importUserDetails() && importUsers())
				? "\nSuccessfully imported users from externalDB.\n Please connect to Zli DB.\n"
				: "\nFailed to import users from externalDB\n";
	}

	/**
	 * The function selecting the fields from the UserDetails table and saves it into UserDetails list.
	 * @return true or false depending if SQLException occurred.
	 */
	private static boolean importUserDetails() {
		try {
			Statement selectStmt = DataBaseController.getConn().createStatement();
			ResultSet rs = selectStmt.executeQuery(
					"SELECT id_user,firstName, lastName, id, email, phoneNumber, idBranch FROM externaldb.external_users;");
			while (rs.next()) {
				UserDetails userResultSet = new UserDetails(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getString(6));
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

	/**
	 * The function selecting the relevant fields for the users table and saves it into users list.
	 * @return true or false depending if SQLException occurred.
	 */
	private static boolean importUsers() {
		try {
			Statement selectStmt = DataBaseController.getConn().createStatement();
			ResultSet rs = selectStmt
					.executeQuery("SELECT username, password, userType, storeCredit FROM externaldb.external_users;");
			while (rs.next()) {
				User userResultSet = new User(rs.getString(1), rs.getString(2), UserType.get(rs.getString(3)),
						rs.getDouble(4));
				users.add(userResultSet);
			}
			rs.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Checking if the users already imported into zli database based on the UserDetails table.
	 * @return true or false depending if SQLException occurred or the users already imported.
	 */
	private static boolean isImported() {
		try {
			Statement selectStmt = DataBaseController.getConn().createStatement();
			ResultSet rs = selectStmt.executeQuery("SELECT id FROM zli.user_details;");
			while (rs.next()) {
				for (UserDetails currentUser : userDetails)
					if (currentUser.getId().equals(rs.getString(1)))
						return true;
			}
			rs.close();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * First, the function checking if the users are already imported into zli database.
	 * If not, it will insert into zli database based on the lists that we saved when we imported from the externaldb.
	 * @return String depending if the users inserted into zli databse or not.
	 */
	public static String insertUsersIntoZliDB() {
		if (!isImported()) {
			insertUserDetailsIntoZli();
			insertUsersTableIntoZli();
			insertBranchesIntoZli();
			return "\n Branches added to Zli DB \n Users added to Zli DB\n";
		}
		return "\n Users already added to Zli DB\n";
	}

	/**
	 * Inserts the UserDetails that the we saved when we imported from the externaldb.
	 * Based on the userDetails list, it will insert the relevant data into UserDetails table to zli database.
	 */
	private static void insertUserDetailsIntoZli() {
		try {
			Statement stmt;
			String query = "INSERT INTO user_details (idAccount, firstName, lastName, id, email, phoneNumber) VALUES ";
			StringBuffer buffer = new StringBuffer(query);
			for (UserDetails currentDetails : userDetails)
				buffer.append("(" + currentDetails.getIdAccount() + ",'" + currentDetails.getFirstName() + "','"
						+ currentDetails.getLastName() + "','" + currentDetails.getId() + "','"
						+ currentDetails.getEmail() + "','" + currentDetails.getPhoneNumber() + "'), ");
			buffer.delete(buffer.toString().length() - 2, buffer.toString().length());
			buffer.append(";");
			stmt = DataBaseController.getConn().createStatement();
			stmt.executeUpdate(buffer.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Inserts the branches into the zli database.
	 * Based on the userDetails list for the FK, it will insert the relevant data into branches table to zli database.
	 */
	private static void insertBranchesIntoZli() {
		try {
			ArrayList<Branch> branchesList = new ArrayList<>();
			branchesList.add(new Branch(1, "Tirat Carmel", "Hertzel 54", "North"));
			branchesList.add(new Branch(2, "Tel Aviv", "Alenbi 15", "Center"));
			branchesList.add(new Branch(3, "Beer Sheva", "Ben Gurion 5", "South"));
			Statement stmt;
			String query = "INSERT INTO branches (idBranch,city, address, region, idManager) VALUES ";
			StringBuffer buffer = new StringBuffer(query);
			for (Branch currentBranch : branchesList) {
				for (UserDetails currentDetails : userDetails) {
					String[] branchesOfUser = currentDetails.getBranches().split(",");
					for (int i = 0; i < branchesOfUser.length; i++) {
						if (Integer.parseInt(branchesOfUser[i]) == currentBranch.getIdBranch()) {
							buffer.append("(" + currentBranch.getIdBranch() + ",'" + currentBranch.getCity() + "','"
									+ currentBranch.getAddress() + "','" + currentBranch.getRegion() + "',"
									+ currentDetails.getIdAccount() + "), ");
							continue;
						}
					}
				}
			}
			buffer.delete(buffer.toString().length() - 2, buffer.toString().length());
			buffer.append(";");
			stmt = DataBaseController.getConn().createStatement();
			stmt.executeUpdate(buffer.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Inserts the Users that the we saved when we imported from the externaldb.
	 * Based on the users list, it will insert the relevant data into Users table to zli database.
	 */
	private static void insertUsersTableIntoZli() {
		try {
			Statement stmt;
			int i = 0;
			int primaryKey = 1;
			String query = "INSERT INTO users (idUser, USERNAME, PASSWORD, idAccount, userType, isLogin, status, storeCredit) VALUES ";
			StringBuffer buffer = new StringBuffer(query);
			for (User currentUser : users)
				buffer.append("(" + (primaryKey++) + ",'" + currentUser.getUsername() + "','"
						+ currentUser.getPassword() + "'," + userDetails.get(i++).getIdAccount() + ",'"
						+ currentUser.getType().toString() + "'," + currentUser.isUserLoggedIn() + ",'Active',"
						+ currentUser.getStoreCredit() + "), ");
			buffer.delete(buffer.toString().length() - 2, buffer.toString().length());
			buffer.append(";");
			stmt = DataBaseController.getConn().createStatement();
			stmt.executeUpdate(buffer.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}