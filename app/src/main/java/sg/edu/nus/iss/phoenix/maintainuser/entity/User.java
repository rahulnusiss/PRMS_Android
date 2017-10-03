package sg.edu.nus.iss.phoenix.maintainuser.entity;


import java.util.ArrayList;

/**
 * This entity object represents the common user characteristics.
 * @updated 01-Sep-2017 5:31:49 PM
 */
public class User {

	/**
	 * The user id of the user. 
	 */
	private String id;
	/**
	 * The name of the user. 
	 */
	private String name;
	/**
	 * The password of the user. 
	 */
	private String password;
	/**
	 * The names of all roles assigned to the user. 
	 */
	private ArrayList<Role> roles;

	/**
	 * The constructor
	 * @param id
	 * @param name
	 * @param password
	 * @param roles
	 */
	public User(String id, String name, String password, ArrayList<Role> roles){
		this.id = id;
		this.name = name;
		this.password = password;
		this.roles = roles;
	}

	/**
	 * To get user Id
	 * @return string user id
	 */
	public String getUserId() {
		return id;
	}

	/**
	 * To get user name
	 * @return string user name
	 */
	public String getUserName() {
		return name;
	}

	/**
	 * Getter
	 * @return string password
	 */
	public String getUserPassword() {
		return password;
	}

	/**
	 * To get roles
	 * @return list of roles
	 */
	public ArrayList<Role> getRoles() {
		return roles;
	}

	/**
	 * Setter
	 * @param id
	 */
	public void setUserId(String id) {
		this.id = id;
	}

	/**
	 * setter
	 * @param name
	 */
	public void setUserName(String name) {
		this.name = name;
	}

	/**
	 * setter
	 * @param password
	 */
	public void setUserPassword(String password) {
		this.password = password;
	}

	/**
	 * setter
	 * @param roles list of roles
	 */
	public void setUserRoles(ArrayList<Role> roles) {
		this.roles = roles;
	}
}//end User