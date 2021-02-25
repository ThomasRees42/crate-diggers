package com.cratediggers.entity;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Id object for orderDetail entity objects.
 */
@SuppressWarnings("serial")
@Embeddable
public class OrderDetailId implements java.io.Serializable {

	/**
	 * Album of orderDetail object. 
	 */
	private Album album;
	/**
	 * AlbumOrder of orderDetail object.
	 */
	private AlbumOrder albumOrder;
	
	/**
	 * Constructs empty OrderDetailId object.
	 */
	public OrderDetailId() {
	}


	/**
	 * album getter method.
	 * Retrieved from database according to foreign key.
	 * @return album of OrderDetailId object
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "album_id", insertable = false, updatable = false, nullable = false)
	public Album getAlbum() {
		return this.album;
	}

	/**
	 * album setter method.
	 * @param album
	 */
	public void setAlbum(Album album) {
		this.album = album;
	}

	/**
	 * albumOrder getter method.
	 * Retrieved from database according to foreign key.
	 * @return albumOrder of OrderDetailId object
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
	}

	/**
	 * Generates unique integer identifier of an OrderDetail object based on both it's album and albumOrder fields.
	 * Used to compare to objects to check if they are the same.
	 * @return Unique hash-code of object based on albumId.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((album == null) ? 0 : album.hashCode());
		result = prime * result + ((albumOrder == null) ? 0 : albumOrder.hashCode());
		return result;
	}

	/**
	 * Overrides equals method.
	 * Checks if an orderDetailId object is identical to another object. 
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
		OrderDetailId other = (OrderDetailId) obj;
		if (album == null) {
			if (other.album != null)
				return false;
		} else if (!album.equals(other.album))
			return false;
		if (albumOrder == null) {
			if (other.albumOrder != null)
				return false;
		} else if (!albumOrder.equals(other.albumOrder))
			return false;
		return true;
	}
	
	

}
