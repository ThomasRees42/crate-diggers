<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Your Shopping Cart</title>
<link rel="stylesheet" href="css/style.css">
<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="js/jquery.validate.min.js"></script>
<script type="text/javascript" src="js/form-validation.js"></script>
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2>Your Cart Details</h2>

		<c:if test="${message != null}">
			<div align="center">
				<h4 class="message">${message}</h4>
			</div>
		</c:if>

		<c:set var="cart" value="${sessionScope['cart']}" />

		<c:if test="${cart.totalItems == 0}">
			<h2>There's no items in your cart</h2>
		</c:if>

		<c:if test="${cart.totalItems > 0}">
			<form action="update_cart" method="post" id="cartForm" onsubmit="return validateFormInput()">
				<div>
					<table border="1">
						<tr>
							<th>No</th>
							<th colspan="2">Album</th>
							<th>Quantity</th>
							<th>Price</th>
							<th>Subtotal</th>
							<th></th>
						</tr>
						<c:forEach items="${cart.items}" var="item" varStatus="status">
							<tr>
								<td>${status.index + 1}</td>
								<td><img class="album-small"
									src="data:image/jpg;base64,${item.key.base64Image}" /></td>
								<td><span id="album-title">${item.key.title}</span></td>
								<td>
									<input type="hidden" name="albumId" value="${item.key.albumId}" />
									<input type="text" name="quantity${status.index + 1}" id="quantity${status.index + 1}" value="${item.value}" size="5" /></td>
								<td><fmt:formatNumber value="${item.key.price}"
										type="currency" /></td>
								<td><fmt:formatNumber
										value="${item.value * item.key.price}" type="currency" /></td>
								<td><a href="remove_from_cart?album_id=${item.key.albumId}">Remove</a></td>
							</tr>
						</c:forEach>

						<tr>
							<td></td>
							<td></td>
							<td></td>
							<td><b>${cart.totalQuantity} album(s)</b></td>
							<td>Total:</td>
							<td colspan="2"><b><fmt:formatNumber
										value="${cart.totalAmount}" type="currency" /></b></td>
						</tr>
					</table>
				</div>
				<div>
					<table class="normal">
						<tr><td>&nbsp;</td></tr>
						<tr>
							<td></td>
							<td><button type="submit">Update</button></td>
							<td><input type="button" id="clearCart" value="Clear Cart"/></td>
							<td><a href="${pageContext.request.contextPath}/">Continue Shopping</a></td>
							<td><a href="checkout">Checkout</a>
						</tr>
					</table>
				</div>
			</form>
		</c:if>

	</div>

	<jsp:directive.include file="footer.jsp" />

	<script type="text/javascript">
	
	function validateFormInput() {
		<c:forEach items="${cart.items}" var="album" varStatus="status">
		const quantity${status.index + 1} = document.getElementById("quantity${status.index + 1}");
		</c:forEach>
		
		<c:forEach items="${cart.items}" var="album" varStatus="status">
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
		
		if (containsCharCheck(quantity${status.index + 1}.value, ".")) {
			alert("Quantity ${status.index + 1} must be an integer!");
			quantity${status.index + 1}.focus();
			return false;
		}
		
		if (!rangeCheck(parseFloat(quantity${status.index + 1}.value), 0, 1000)) {
			alert("Quantity ${status.index + 1} must be at least one and less than 1000!");
			quantity${status.index + 1}.focus();
			return false;
		}
		</c:forEach>
		
		return true;
	}
	
		$(document).ready(function() {
			$("#clearCart").click(function() {
				window.location = 'clear_cart';
			});

		});
	</script>
</body>
</html>