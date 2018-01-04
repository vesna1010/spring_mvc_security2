package college.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

@Embeddable
public class Address {

	private String city = "";
	private String street = "";
	private String state = "";

	public Address() {
	}

	public Address(String city, String street, String state) {
		this.city = city;
		this.street = street;
		this.state = state;
	}

	@Pattern(regexp = "^([a-zA-Z]+\\s?){3,}$")
	@Column(name = "STATE")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Pattern(regexp = "^([a-zA-Z]+\\s?){3,}$")
	@Column(name = "CITY")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Pattern(regexp = "^([a-zA-Z0-9]+\\s?){3,}$")
	@Column(name = "STREET")
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Override
	public String toString() {
		return city + " - " + street + " - " + state;
	}

}
