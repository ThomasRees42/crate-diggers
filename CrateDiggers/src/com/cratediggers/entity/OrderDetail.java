package com.cratediggers.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * OrderDetails entity class maps to database, can be interacted with by an EntityManager object. 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "order_detail", catalog = "prototype")
@NamedQueries({
	@NamedQuery(name = "OrderDetail.bestSelling", 
			query = "SELECT al.album FROM OrderDetail al GROUP by al.album.albumId "
					+ "ORDER BY SUM(al.quantity) DESC")	
})
public class OrderDetail implements java.io.Serializable {

	/**
	 * Id of an orderDetail object.
	 * Based on both the orderDetail's album and albumOrder fields.
	 */
	private OrderDetailId id = new OrderDetailId();
	/**
	 * Album which the OrderDetail object belongs to (based on foreign key).
	 */
	private Album album;
	/**
	 * AlbumOrder which the OrderDetail object belongs to (based on foreign key)
	 */
	private AlbumOrder albumOrder;
	/**
	 * Quantity of album being ordered.
	 */
	private int quantity;
	/**
	 * Sub-total of album price based on quantity.
	 */
	private float subtotal;	

	/**
	 * Constructs empty OrderDetail entity class.
	 */
	public OrderDetail() {
	}

	/**
	 * Constructs OrderDetail entity class only with id.
	 * @param id
	 */
	public OrderDetail(OrderDetailId id) {
		this.id = id;
	}
	
	/**
	 * Constructs OrderDetail entity class with all fields.
	 * @param id
	 * @param album
	 * @param albumOrder
	 * @param quantity
	 * @param subtotal
	 */
	public OrderDetail(OrderDetailId id, Album album, AlbumOrder albumOrder, int quantity, float subtotal) {
		this.id = id;
		this.album = album;
		this.albumOrder = albumOrder;
		this.quantity = quantity;
		this.subtotal = subtotal;
	}

	/**
	 * id getter method.
	 * @return id of an orderDetail object
	 */
	@EmbeddedId

	@AttributeOverrides({ @AttributeOverride(name = "orderId", column = @Column(name = "order_id", nullable = false)),
			@AttributeOverride(name = "albumId", column = @Column(name = "album_id", nullable = false))})
	public OrderDetailId getId() {
		return this.id;
	}

	/**
	 * id setter method.
	 * @param id
	 */
	public void setId(OrderDetailId id) {
		this.id = id;
	}

	/**
	 * album getter method.
	 * Retrieved from database according to foreign key.
	 * @return album of an orderDetail object
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "album_id", insertable = false, updatable = false, nullable = true)
	public Album getAlbum() {
		return this.album;
	}
	
	/**
	 * album setter method.
	 * @param album
	 */
	public void setAlbum(Album album) {
		this.album = album;
		this.id.setAlbum(album);
	}
	
	/**
	 * albumOrder getter method.
	 * Retrieved from database according to foreign key.
	 * @return albumOrder of an orderDetail object
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", insertable = false, updatable = false, nullable = false)
	public AlbumOrder getAlbumOrder() {
		return this.albumOrder;
	}

	/**
	 * albumOrder setter method.
	 * @param albumOrder
	 */
	public void setAlbumOrder(AlbumOrder albumOrder) {
		this.albumOrder = albumOrder;
		this.id.setAlbumOrder(albumOrder);
	}
	
	/**
	 * quantity getter method.
	 * @return quantity of an orderDetail object
	 */
	@Column(name = "quantity", nullable = false)
	public int getQuantity() {
		return this.quantity;
	}

	/**
	 * quantity setter method.
	 * @param quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * subtotal getter method.
	 * @return subtotal of an orderDetail object
	 */
	@Column(name = "subtotal", nullable = false, precision = 12, scale = 0)
	public float getSubtotal() {
		return this.subtotal;
	}

	/**
	 * subtotal setter method.
	 * @param subtotal
	 */
	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}	

}
