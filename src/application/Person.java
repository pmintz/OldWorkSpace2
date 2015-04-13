package application;
import java.io.Serializable;


public class Person implements Serializable {

	private String id;
	private String firstName;
	private String lastName;
	private String mi;
	private String address;
	private String cities;
	private String state;
	private String telephone;
	private String email;

	Person() {
	}
	
	Person(String id, String lastName, String firstName, String mi, String address, String cities, String state, String telephone, String email) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mi = mi;
		this.address = address;
		this.cities = cities;
		this.state = state;
		this.telephone = telephone;
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMi() {
		return mi;
	}

	public void setMi(String mi) {
		this.mi = mi;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCities() {
		return cities;
	}

	public void setCities(String cities) {
		this.cities = cities;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
