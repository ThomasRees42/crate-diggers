package com.cratediggers.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * AlbumOrder entity class maps to database, can be interacted with by an EntityManager object. 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "album_order", catalog = "prototype")
@NamedQueries({
	//@NamedQuery(name = "AlbumOrder.findAll", query = "SELECT al FROM AlbumOrder al ORDER BY al.orderDate DESC"),
	@NamedQuery(name = "AlbumOrder.findAll", query = "SELECT al FROM AlbumOrder al"),
	@NamedQuery(name = "AlbumOrder.countAll", query = "SELECT COUNT(*) FROM AlbumOrder"),
	@NamedQuery(name = "AlbumOrder.findByCustomer", 
		query = "SELECT al FROM AlbumOrder al WHERE al.customer.customerId =:customerId ORDER BY al.orderDate DESC"),
	@NamedQuery(name = "AlbumOrder.findByIdAndCustomer",
			query = "SELECT al FROM AlbumOrder al WHERE al.orderId =:orderId AND al.customer.customerId =:customerId")
})
public class AlbumOrder implements java.io.Serializable {

	/**
	 * Id of an albumOrder object.
	 * Auto-incremented.
	 */
	private Integer orderId;
	/**
	 * Customer which an albumOrder object belongs to (according to foreign key).
	 */
	private Customer customer;
	/**
	 * Date on which albumOrder object was made.
	 */
	private Date orderDate;
	/**
	 * First line of address of an albumOrder object.
	 * Refers to the order's recipient which can differ from the customer.
	 */
	private String addressLine1;
	/**
	 * Second line of address of an albumOrder object.
	 * Refers to the order's recipient which can differ from the customer.
	 */
	private String addressLine2;
	/**
	 * First name of an albumOrder recipient.
	 */
	private String firstName;
	/**
	 * Last name of an albumOrder recipient.
	 */
	private String lastName;
	/**
	 * Phone number of an albumOrder recipient.
	 */
	private String phone;
	/**
	 * Town of an albumOrder recipient.
	 */
	private String town;
	/**
	 * County of an albumOrder recipient.
	 */
	private String county;
	/**
	 * Post code of an albumOrder recipient.
	 */
	private String postcode;
	/**
	 * Payment method of an albumOrder object.
	 */
	private String paymentMethod;
	
	/**
	 * Total cost of an albumOrder object.
	 */
	private float total;
	/**
	 * Subtotal cost of an albumOrder object.
	 * Calculated before shippingFee and tax are added.
	 */
	private float subtotal;
	/**
	 * Shipping fee cost of an albumOrder object.
	 */
	private float shippingFee;
	/**
	 * Tax cost of an albumOrder object.
	 */
	private float tax;
	
	/**
	 * Status of an albumOrder.
	 * Processing, shipping, delivered, completed or cancelled.
	 */
	private String status;
	
	/**
	 * Set of orderDetails in relational database that contain orderId.
	 */
	private Set<OrderDetail> orderDetails = new HashSet<OrderDetail>(0);

	/**
	 * Constructs empty albumOrder entity object.
	 */
	public AlbumOrder() {
	}

	/**
	 * addressLine2 getter method.
	 * @return addressLine2 of an albumOrder object
	 */
	@Column(name = "r_address_line2", nullable = false, length = 256)	
	public String getAddressLine2() {
		return addressLine2;
	}

	/**
	 * addressLine2 setter method.
	 * @param addressLine2
	 */
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	/**
	 * lastName getter method.
	 * @return lastName of an albumOrder object
	 */
	@Column(name = "r_lastname", nullable = false, length = 30)	
	public String getLastName() {
		return lastName;
	}

	/**
	 * lastName setter method.
	 * @param lastname
	 */
	public void setLastName(String lastname) {
		this.lastName = lastname;
	}

	/**
	 * phone getter method.
	 * @return phone of an albumOrder object
	 */
	@Column(name = "r_phone", nullable = false, length = 15)
	public String getPhone() {
		return phone;
	}

	/**
	 * phone setter method.
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * town getter method.
	 * @return town of an albumOrder object
	 */
	@Column(name = "r_town", nullable = false, length = 32)	
	public String getTown() {
		return town;
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
	 * @return county of an albumOrder object
	 */
	@Column(name = "r_county", nullable = false, length = 45)	
	public String getCounty() {
		return county;
	}

	/**
	 * county setter method.
	 * @param county
	 */
	public void setCounty(String county) {
		this.county = county;
	}

	/**
	 * postcode getter method.
	 * @return postcode of an albumOrder object
	 */
	@Column(name = "r_postcode", nullable = false, length = 24)	
	public String getPostcode() {
		return postcode;
	}

	/**
	 * postcode setter method.
	 * @param postcode
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	/**@Column(name = "r_country", nullable = false, length = 4)	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Transient
	public String getCountryName() {
		return new Locale("", this.country).getDisplayCountry();
	}	**/

	/**
	 * subtotal getter method.
	 * @return subtotal of an albumOrder object
	 */
	@Column(name = "subtotal", nullable = false, precision = 12, scale = 0)	
	public float getSubtotal() {
		return subtotal;
	}

	/**
	 * subtotal setter method
	 * @param subtotal
	 */
	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}

	/**
	 * shippingFee getter method.
	 * @return shippingFee of an albumOrder object
	 */
	@Column(name = "shipping_fee", nullable = false, precision = 12, scale = 0)	
	public float getShippingFee() {
		return shippingFee;
	}

	/**
	 * shippingFee setter method.
	 * @param shippingFee
	 */
	public void setShippingFee(float shippingFee) {
		this.shippingFee = shippingFee;
	}

	/**
	 * tax getter method.
	 * @return tax of an albumOrder object
	 */
	@Column(name = "tax", nullable = false, precision = 12, scale = 0)	
	public float getTax() {
		return tax;
	}

	/**
	 * tax setter method.
	 * @param tax
	 */
	public void setTax(float tax) {
		this.tax = tax;
	}

	public AlbumOrder(Customer customer, Date orderDate, String shippingAddress, String recipientName,
			String recipientPhone, String paymentMethod, float total, String status) {
		this.customer = customer;
		this.orderDate = orderDate;
		this.addressLine1 = shippingAddress;
		this.firstName = recipientName;
		this.phone = recipientPhone;
		this.paymentMethod = paymentMethod;
		this.total = total;
		this.status = status;
	}

	public AlbumOrder(Customer customer, Date orderDate, String shippingAddress, String recipientName,
			String recipientPhone, String paymentMethod, float total, String status, Set<OrderDetail> orderDetails) {
		this.customer = customer;
		this.orderDate = orderDate;
		this.addressLine1 = shippingAddress;
		this.firstName = recipientName;
		this.phone = recipientPhone;
		this.paymentMethod = paymentMethod;
		this.total = total;
		this.status = status;
		this.orderDetails = orderDetails;
	}

	/**
	 * orderId getter method.
	 * @return orderId of an albumOrder object
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "order_id", unique = true, nullable = false)
	public Integer getOrderId() {
		return this.orderId;
	}

	/**
	 * orderId setter method.
	 * @param orderId
	 */
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	/**
	 * customer getter method.
	 * customer retrieved from database according to foreign key.
	 * @return customer of an albumOrder object
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id", nullable = false)
	public Customer getCustomer() {
		return this.customer;
	}

	/**
	 * customer setter method
	 * @param customer
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * orderDate getter method.
	 * @return orderDate of an albumOrder object
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "order_date", nullable = false, length = 19)
	public Date getOrderDate() {
		return this.orderDate;
	}

	/**
	 * orderDate setter method.
	 * @param orderDate
	 */
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	/**
	 * addressLine1 getter method.
	 * @return addressLine1 of an albumOrder object
	 */
	@Column(name = "r_address_line1", nullable = false, length = 256)
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
	 * firstName getter method.
	 * @return firstName of an albumOrder object
	 */
	@Column(name = "r_firstname", nullable = false, length = 30)
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * firstName setter method.
	 * @param firstname
	 */
	public void setFirstName(String firstname) {
		this.firstName = firstname;
	}

	/**
	 * paymentMethod getter method.
	 * @return paymentMethod of an albumOrder object
	 */
	@Column(name = "payment_method", nullable = false, length = 20)
	public String getPaymentMethod() {
		return this.paymentMethod;
	}

	/**
	 * paymentMethod setter method.
	 * @param paymentMethod
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * total getter method.
	 * @return total of an albumOrder object
	 */
	@Column(name = "total", nullable = false, precision = 12, scale = 0)
	public float getTotal() {
		return this.total;
	}

	/**
	 * total setter method.
	 * @param total
	 */
	public void setTotal(float total) {
		this.total = total;
	}

	/**
	 * status getter method.
	 * @return status of an albumOrder object
	 */
	@Column(name = "status", nullable = false, length = 20)
	public String getStatus() {
		return this.status;
	}

	/**
	 * status setter method.
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * orderDetails getter method.
	 * Retrieved from orderDetails table according to foreign keys.
	 * @return orderDetails of an albumOrder object
	 */
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "albumOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<OrderDetail> getOrderDetails() {
		return this.orderDetails;
	}

	/**
	 * orderDetails setter method.
	 * @param orderDetails
	 */
	public void setOrderDetails(Set<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	/**
	 * Counts the total quantity of albums in an order.
	 * @return Total number of albums.
	 */
	@Transient
	public int getAlbumCopies() {
		int total = 0;
		
		for (OrderDetail orderDetail : orderDetails) {
			total += orderDetail.getQuantity();
		}
		
		return total;
	}
	
	
	/**
	 * Generates unique integer identifier of an AlbumOrder object.
	 * Used to compare to objects to check if they are the same.
	 * @return Unique hash-code of object based on orderId.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		return result;
	}

	/**
	 * Overrides equals method.
	 * Checks if an albumOrder object is identical to another object. 
	 * @param obj to be compared to
	 * @return true if the object are the same; false if not.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AlbumOrder other = (AlbumOrder) obj;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		return true;
	}

	
}
