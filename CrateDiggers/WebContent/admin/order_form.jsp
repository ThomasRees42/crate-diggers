<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Edit Order - Crate Diggers Administration</title>
	<link rel="stylesheet" href="../css/style.css" >
	<script type="text/javascript" src="../js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>	
	<script type="text/javascript" src="../js/form-validation.js"></script>
</head>
<body>
	<jsp:directive.include file="header.jsp" />
	
	<div align="center">
		<h2 class="pageheading">Edit Order ID: ${order.orderId}</h2>		
	</div>
	
	<c:if test="${message != null}">
	<div align="center">
		<h4 class="message">${message}</h4>
	</div>
	</c:if>
	
	<form action="update_order" method="post" id="orderForm" onsubmit="return validateFormInput()">
	<div align="center">
	
		<table>
			<h2>Order Overview:</h2>
			<tr>
				<td><b>Ordered By: </b></td>
				<td>${order.customer.firstName} ${order.customer.lastName}</td>
			</tr>
			<tr>
				<td><b>Order Date: </b></td>
				<td>${order.orderDate}</td>
			</tr>
			<tr>
				<td><b>Payment Method: </b></td>
				<td>
					<select name="paymentMethod">
						<option value="Cash On Delivery" <c:if test="${order.paymentMethod eq 'Cash On Delivery'}">selected='selected'</c:if>>Cash On Delivery</option>
						<option value="paypal" <c:if test="${order.paymentMethod eq 'paypal'}">selected='selected'</c:if>>PayPal or Credit card</option>
					</select>
				</td>
			</tr>

			<tr>
				<td><b>Order Status: </b></td>
				<td>
					<select name="orderStatus">
						<option value="Processing" <c:if test="${order.status eq 'Processing' }">selected='selected'</c:if> >Processing</option>
						<option value="Shipping" <c:if test="${order.status eq 'Shipping' }">selected='selected'</c:if>>Shipping</option>
						<option value="Delivered" <c:if test="${order.status eq 'Delivered' }">selected='selected'</c:if>>Delivered</option>
						<option value="Completed" <c:if test="${order.status eq 'Completed' }">selected='selected'</c:if>>Completed</option>
						<option value="Cancelled" <c:if test="${order.status eq 'Cancelled' }">selected='selected'</c:if>>Cancelled</option>
					</select>
				</td>
			</tr>
		</table>
		<h2>Recipient Information</h2>
		<table>	
			<tr>
				<td><b>First Name: </b></td>
				<td><input type="text" name="firstname" id="firstname" value="${order.firstName}" size="45" /></td>
			</tr>
			<tr>
				<td><b>Last Name: </b></td>
				<td><input type="text" name="lastname" id="lastname" value="${order.lastName}" size="45" /></td>
			</tr>			
			<tr>
				<td><b>Phone: </b></td>
				<td><input type="text" name="phone" id="phone" value="${order.phone}" size="45" /></td>
			</tr>		
			<tr>
				<td><b>Address Line 1: </b></td>
				<td><input type="text" name="address1" id="address1" value="${order.addressLine1}" size="45" /></td>
			</tr>
			<tr>
				<td><b>Address Line 2: </b></td>
				<td><input type="text" name="address2" id="address2" value="${order.addressLine2}" size="45" /></td>
			</tr>
			<tr>
				<td><b>Town: </b></td>
				<td><input type="text" name="town" id="town" value="${order.town}" size="45" /></td>
			</tr>			
			<tr>
				<td><b>County: </b></td>
				<td><input type="text" name="county" id="county" value="${order.county}" size="45" /></td>
			</tr>
			<tr>
				<td><b>Postcode: </b></td>
				<td><input type="text" name="postcode" id="postcode" value="${order.postcode}" size="45" /></td>
			</tr>
			<%--
			<tr>
				<td><b>Country: </b></td>
				<td>
					<select name="country" id="country">
					<c:forEach items="${mapCountries}" var="country">
						<option value="${country.value}" <c:if test='${order.country eq country.value}'>selected='selected'</c:if> >${country.key}</option>
					</c:forEach>
					</select>				
				</td>
			</tr>
			--%>					
		</table>
	</div>
	<div align="center">
		<h2>Ordered Albums</h2>
		<table border="1">
			<tr>
				<th>Index</th>
				<th>Album Title</th>
				<th>Author</th>
				<th>Price</th>
				<th>Quantity</th>
				<th>Subtotal</th>
				<th></th>
			</tr>
			<c:forEach items="${order.orderDetails}" var="orderDetail" varStatus="status">
			<tr>
				<td>${status.index + 1}</td>
				<td>${orderDetail.album.title}</td>
				<td>${orderDetail.album.artist.name}</td>
				<td>
					<input type="hidden" name="price" value="${orderDetail.album.price}" />
					<fmt:formatNumber value="${orderDetail.album.price}" type="currency" />
				</td>
				<td>
					<input type="hidden" name="albumId" value="${orderDetail.album.albumId}" />
					<input type="text" name="quantity${status.index + 1}" value="${orderDetail.quantity}" size="5" />
				</td>
				
				<td><fmt:formatNumber value="${orderDetail.subtotal}" type="currency" /></td>
				<td><a href="remove_album_from_order?id=${orderDetail.album.albumId}">Remove</a></td>
			</tr>
			</c:forEach>
			<tr>
				<td colspan="7" align="right">
					<p>Subtotal: <fmt:formatNumber value="${order.subtotal}" type="currency" /></p>
					<p>Tax: <input type="text" size="5" name="tax" id="tax" value="${order.tax}" /></p>
					<p>Shipping Fee: <input type="text" size="5" name="shippingFee" id="shippingFee" value="${order.shippingFee}" /></p>
					<p>TOTAL: <fmt:formatNumber value="${order.total}" type="currency" /></p>
				</td>				
			</tr>
		</table>
	</div>
	<div align="center">
		<br/>		
		<a href="javascript:showAddAlbumPopup()"><b>Add Albums</b></a>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="submit" value="Save" />
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="Cancel" onclick="javascript:window.location.href='list_order';" /> 		
	</div>
	</form>
	
	<jsp:directive.include file="footer.jsp" />
	<script type="text/javascript">
	function validateFormInput() {
		const email = document.getElementById("email");
		const firstname = document.getElementById("firstname");
		const lastname = document.getElementById("lastname");
		const phone = document.getElementById("phone");
		const address1 = document.getElementById("address1");
		const address2 = document.getElementById("address2");
		const town = document.getElementById("town");
		const county = document.getElementById("county");
		const postcode = document.getElementById("postcode");
		<c:forEach items="${order.orderDetails}" var="album" varStatus="status">
		const quantity${status.index + 1} = document.getElementById("quantity${status.index + 1}");
		</c:forEach>
		const shippingFee = document.getElementById("shippingFee");
		const tax = document.getElementById("tax");
		
		if (!presenceCheck(firstname.value)) {
			alert("First name is required!");
			firstname.focus();
			return false;
		}
		
		if (!lengthCheck(firstname.value, 2, 30)) {
			alert("First name is an invalid length!");
			firstname.focus();
			return false;
		}
		
		if (!presenceCheck(lastname.value)) {
			alert("Last name is required!");
			lastname.focus();
			return false;
		}
		
		if (!lengthCheck(lastname.value, 2, 30)) {
			alert("Last name is an invalid length!");
			lastname.focus();
			return false;
		}
		
		if (!presenceCheck(phone.value)) {
			alert("Phone is required!");
			phone.focus();
			return false;
		}
		
		if (!lengthCheck(phone.value, 9, 15)) {
			alert("Phone is an invalid length!");
			phone.focus();
			return false;
		}
		
		if (!numericTypeCheck(phone.value)) {
			alert("Phone must be numerical!");
			phone.focus();
			return false;
		}
		
		if (!presenceCheck(address1.value)) {
			alert("Address line 1 is required!");
			address1.focus();
			return false;
		}
		
		if (!lengthCheck(address1.value, 4, 128)) {
			alert("Address line 1 is an invalid length!");
			address1.focus();
			return false;
		}
		
		if (!presenceCheck(address2.value)) {
			alert("Address line 2 is required!");
			address2.focus();
			return false;
		}
		
		if (!lengthCheck(address2.value, 4, 128)) {
			alert("Address line 2 is an invalid length!");
			address2.focus();
			return false;
		}
		
		if (!presenceCheck(town.value)) {
			alert("Town is required!");
			town.focus();
			return false;
		}
		
		if (!lengthCheck(town.value, 4, 32)) {
			alert("Town is an invalid length!");
			town.focus();
			return false;
		}
		
		if (!presenceCheck(county.value)) {
			alert("County is required!");
			county.focus();
			return false;
		}
		
		if (!lengthCheck(county.value, 4, 64)) {
			alert("County is an invalid length!");
			county.focus();
			return false;
		}
		
		if (!presenceCheck(postcode.value)) {
			alert("Postcode is required!");
			postcode.focus();
			return false;
		}
		
		if (!lengthCheck(postcode.value, 6, 7)) {
			alert("Postcode is an invalid length!");
			postcode.focus();
			return false;
		}
		
		<c:forEach items="${order.orderDetails}" var="album" varStatus="status">
		if (!presenceCheck(quantity${status.index + 1}.value)) {
			alert("Quantity ${status.index + 1} is required!");
			quantity${status.index + 1}.focus();
			return false;
		}
		
		if (!numericTypeCheck(quantity${status.index + 1}.value)) {
			alert("Quantity ${status.index + 1} must be numerical!");
			quantity${status.index + 1}.focus();
			return false;
		}
		
		if (!rangeCheck(quantity${status.index + 1}.value, 1, 1000)) {
			alert("Quantity ${status.index + 1} must be at least one!");
			quantity${status.index + 1}.focus();
			return false;
		}
		</c:forEach>
		
		if (!presenceCheck(shippingFee.value)) {
			alert("Shipping fee is required!");
			shippingFee.focus();
			return false;
		}
		
		if (!numericTypeCheck(shippingFee.value)) {
			alert("Shipping fee must be numerical!");
			shippingFee.focus();
			return false;
		}
		
		if (!rangeCheck(shippingFee.value, 0, 100000)) {
			alert("Shipping fee must be at least zero!");
			shippingFee.focus();
			return false;
		}
		
		if (!presenceCheck(tax.value)) {
			alert("Tax is required!");
			tax.focus();
			return false;
		}
		
		if (!numericTypeCheck(tax.value)) {
			alert("Tax must be numerical!");
			tax.focus();
			return false;
		}
		
		if (!rangeCheck(tax.value, 0, 1000)) {
			alert("Tax must be at least zero!");
			shippingFee.focus();
			return false;
		}
		
		return true;
	}
	</script>
	
	<script>
		function showAddAlbumPopup() {
			var width = 600;
			var height = 250;
			var left = (screen.width - width) / 2;
			var top = (screen.height - height) / 2;
			
			window.open('add_album_form', '_blank', 
					'width=' + width + ', height=' + height + ', top=' + top + ', left=' + left);
		}	
	</script>
</body>
</html>