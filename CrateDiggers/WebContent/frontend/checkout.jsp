<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Checkout - Crate Diggers Store</title>
<link rel="stylesheet" href="css/style.css">
<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="js/jquery.validate.min.js"></script>
<script type="text/javascript" src="js/form-validation.js"></script>
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center">
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
				<div>
					<h2>Review Your Order Details | <a href="view_cart">Edit</a></h2>
					<table border="1">
						<tr>
							<th>No</th>
							<th colspan="2">Album</th>
							<th>Artist</th>
							<th>Price</th>
							<th>Quantity</th>							
							<th>Subtotal</th>							
						</tr>
						<c:forEach items="${cart.items}" var="item" varStatus="status">
							<tr>
								<td>${status.index + 1}</td>
								<td><img class="album-small"
									src="data:image/jpg;base64,${item.key.base64Image}" /></td>
								<td><span id="album-title">${item.key.title}</span></td>
								<td>${item.key.artist.name}</td>
								<td><fmt:formatNumber value="${item.key.price}"
										type="currency" /></td>								
								<td>									
									<input type="text" name="quantity${status.index + 1}" value="${item.value}" size="5" readonly="readonly" />
								</td>
								<td><fmt:formatNumber
										value="${item.value * item.key.price}" type="currency" /></td>								
							</tr>
						</c:forEach>

						<tr>							
							<td colspan="7" align="right">
								<p>Number of copies: ${cart.totalQuantity}</p>
								<p>Subtotal: <fmt:formatNumber value="${cart.totalAmount}" type="currency" /></p>
								<p>Tax: <fmt:formatNumber value="${tax}" type="currency" /></p>
								<p>Shipping Fee: <fmt:formatNumber value="${shippingFee}" type="currency" /></p>
								<p>TOTAL: <fmt:formatNumber value="${total}" type="currency" /></p>
							</td>
						</tr>
					</table>
					<h2>Recipient Information</h2>
					<form id="orderForm" action="place_order" method="post" onsubmit="return validateFormInput()">
					<table>
						<tr>
							<td>First Name:</td>
							<td><input type="text" name="firstname" id="firstname" value="${loggedCustomer.firstName}" /></td>
						</tr>
						<tr>
							<td>Last Name:</td>
							<td><input type="text" name="lastname" id="lastname" value="${loggedCustomer.lastName}" /></td>
						</tr>						
						<tr>
							<td>Phone:</td>
							<td><input type="text" name="phone" id="phone" value="${loggedCustomer.phone}" /></td>
						</tr>
						<tr>
							<td>Address Line 1:</td>
							<td><input type="text" name="address1" id="address2" value="${loggedCustomer.addressLine1}" /></td>
						</tr>
						<tr>
							<td>Address Line 2:</td>
							<td><input type="text" name="address2" id="address2" value="${loggedCustomer.addressLine2}" /></td>
						</tr>						
						<tr>
							<td>Town:</td>
							<td><input type="text" name="town" id="town" value="${loggedCustomer.town}" /></td>
						</tr>
						<tr>
							<td>County:</td>
							<td><input type="text" name="county" id="county" value="${loggedCustomer.county}" /></td>
						</tr>												
						<tr>
							<td>Postcode:</td>
							<td><input type="text" name="postcode" id="postcode" value="${loggedCustomer.postcode}" /></td>
						</tr>
						<%-- 					
						<tr>
							<td>Country:</td>
							<td>
								<select name="country" id="country">
								<c:forEach items="${mapCountries}" var="country">
									<option value="${country.value}" <c:if test='${loggedCustomer.country eq country.value}'>selected='selected'</c:if> >${country.key}</option>
								</c:forEach>
								</select>							
							</td>
						</tr>	
						--%>																							
					</table>
					<div>
						<h2>Payment</h2>
						Choose your payment method:
						&nbsp;&nbsp;&nbsp;
						<select name="paymentMethod">
							<option value="Cash On Delivery">Cash On Delivery</option>
							<option value="paypal">PayPal or Credit card</option>
						</select>
					</div>
					<div>
						<table class="normal">
							<tr><td>&nbsp;</td></tr>
							<tr>								
								<td><button type="submit">Place Order</button></td>								
								<td><a href="${pageContext.request.contextPath}/">Continue Shopping</a></td>
								
							</tr>
						</table>
					</div>					
					</form>
				</div>			
		</c:if>

	</div>

	<jsp:directive.include file="footer.jsp" />

	<script type="text/javascript">
		function validateFormInput() {
			const firstname = document.getElementById("firstname");
			const lastname = document.getElementById("lastname");
			const phone = document.getElementById("phone");
			const address1 = document.getElementById("address1");
			const address2 = document.getElementById("address2");
			const town = document.getElementById("town");
			const county = document.getElementById("county");
			const postcode = document.getElementById("postcode");
			
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
			
			return true;
		}
	</script>
</body>
</html>