package com.cratediggers.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Customer entity class maps to database, can be interacted with by an EntityManager object. 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "customer", catalog = "prototype", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@NamedQueries({
	//@NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c ORDER BY c.registerDate DESC"),
	@NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
	@NamedQuery(name = "Customer.findByEmail", query = "SELECT c FROM Customer c WHERE c.email = :email"),
	@NamedQuery(name = "Customer.countAll", query = "SELECT COUNT(c.email) FROM Customer c"),
	@NamedQuery(name = "Customer.checkLogin", query = "SELECT c FROM Customer c WHERE c.email = :email AND c.password = :pass")
})
public class Customer implements java.io.Serializable {

	/**
	 * Id of a customer object.
	 * Auto-increments.
	 */
	private Integer customerId;
	/**
	 * Email of a customer object.
	 * Unique.
	 */
	private String email;
	/**
	 * First name of a customer object.
	 */
	private String firstName;
	/**
	 * Last name of a customer object.
	 */
	private String lastName;
	/**
	 * First line of the address of a customer object.
	 */
	private String addressLine1;
	/**
	 * Second line of the address of a customer object.
	 */
	private String addressLine2;
	/**
	 * Town of a customer object's address.
	 */
	private String town;
	/**
	 * County of a customer object's address.
	 */
	private String county;
	/**
	 * Phone number of a customer object's address.
	 */
	private String phone;
	/**
	 * Post code of a customer object.
	 */
	private String postcode;
	/**
	 * Password of a customer object.
	 */
	private String password;
	/**
	 * Date on which customer registered.
	 */
	private Date registerDate;

	/**
	 * Constructs empty customer entity object.
	 */
	public Customer() {
	}

	/**
	 * Constructs a customer entity object without customerId.
	 * @param email
	 * @param firstName
	 * @param lastName
	 * @param addressLine1
	 * @param addressLine2
	 * @param town
	 * @param county
	 * @param phone
	 * @param postcode
	 * @param password
	 * @param registerDate
	 */
	public Customer(String email, String firstName, String lastName, String addressLine1, String addressLine2,
			String town, String county, String phone, String postcode, String password, Date registerDate) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.town = town;
		this.county = county;
		this.phone = phone;
		this.postcode = postcode;
		this.password = password;
		this.registerDate = registerDate;
	}

	/**
	 * customerId getter method.
	 * @return customerId of a customer object
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "customer_id", unique = true, nullable = false)
	public Integer getCustomerId() {
		return this.customerId;
	}

	/**
	 * customerId setter method.
	 * @param customerId
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	/**
	 * email getter method.
	 * @return email of a customer object
	 */
	@Column(name = "email", unique = true, nullable = false, length = 64)
	public String getEmail() {
		return this.email;
	}

	/**
	 * email setter method.
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * firstName getter method.
	 * @return firstName of a customer object
	 */
	@Column(name = "first_name", nullable = false, length = 30)
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * firstName setter method.
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * lastName getter method.
	 * @return lastName of a customer object
	 */
	@Column(name = "last_name", nullable = false, length = 30)
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * lastName setter method.
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * addressLine1 getter method.
	 * @return addressLine1 of a customer object
	 */
	@Column(name = "address_line1", nullable = false, length = 128)
	public String getAddressLine1() {
		return this.addressLine1;
	}

	/**
	 * addressLine1 setter method.
	 * @param addressLine1
	 */
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	/**
	 * addressLine2 getter method.
	 * @return addressLine2 of a customer object
	 */
	@Column(name = "address_line2", nullable = false, length = 128)
	public String getAddressLine2() {
		return this.addressLine2;
	}

	/**
	 * addressLine2 setter method.
	 * @param addressLine2
	 */
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	/**
	 * town getter method.
	 * @return town of a customer object
	 */
	@Column(name = "town", nullable = false, length = 32)
	public String getTown() {
		return this.town;
	}

	/**
	 * town setter method.
	 * @param town
	 */
	public void setTown(String town) {
		this.town = town;
	}

	/**
	 * county getter method.
	 * @return county of a customer object
	 */
	@Column(name = "county", nullable = false, length = 64)
	public String getCounty() {
		return this.county;
	}

	/**
	 * county setter method.
	 * @param county
	 */
	public void setCounty(String county) {
		this.county = county;
	}

	/**
	 * phone getter method.
	 * @return phone of a customer object
	 */
	@Column(name = "phone", nullable = false, length = 15)
	public String getPhone() {
		return this.phone;
	}

	/**
	 * phone setter method.
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * postcode getter method.
	 * @return postcode of a customer object
	 */
	@Column(name = "postcode", nullable = false, length = 24)
	public String getPostcode() {
		return this.postcode;
	}

	/**
	 * postcode setter method
	 * @param postcode
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	/**
	 * password getter method.
	 * @return password of a customer object
	 */
	@Column(name = "password", nullable = false, length = 16)
	public String getPassword() {
		return this.password;
	}

	/**
	 * password setter method.
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * registerDate getter method.
	 * @return registerDate of a customer object
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "register_date", nullable = false, length = 19)
	public Date getRegisterDate() {
		return this.registerDate;
	}

	/**
	 * registerDate setter method.
	 * @param registerDate
	 */
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

}
