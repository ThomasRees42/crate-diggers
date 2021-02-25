<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Your Cart</title>
	<link rel="stylesheet" href="../css/style.css" >
	<script type="text/javascript" src="../js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>	
</head>
<body>
	
	<div align="center">
	<jsp:directive.include file="../frontend/header.jsp" />
		<h3><i>Please carefully review the following information before making payment</i></h3>
		<h2>Payer Information</h2>
		<table>
			<tr>
				<td><b>First Name:</b></td>
				<td>${payer.firstName}</td>
			</tr>
			<tr>
				<td><b>Last Name:</b></td>
				<td>${payer.lastName}</td>
			</tr>
			<tr>
				<td><b>E-mail:</b></td>
				<td>${payer.email}</td>
			</tr>			
		</table>
		<h2>Recipient Information:</h2>
		<table>
			<tr>
				<td><b>Recipient Name:</b></td>
				<td>${recipient.recipientName}</td>
			</tr>
			<tr>
				<td><b>Address Line 1:</b></td>
				<td>${recipient.line1}</td>
			</tr>
			<tr>
				<td><b>Address Line 2:</b></td>
				<td>${recipient.line2}</td>
			</tr>
			<tr>
				<td><b>City:</b></td>
				<td>${recipient.city}</td>
			</tr>
			<tr>
				<td><b>State:</b></td>
				<td>${recipient.state}</td>
			</tr>
			<tr>
				<td><b>Country Code:</b></td>
				<td>${recipient.countryCode}</td>
			</tr>
			<tr>
				<td><b>Postal Code:</b></td>
				<td>${recipient.postalCode}</td>
			</tr>			
		</table>
		<h2>Transaction Details:</h2>
		<table>
			<tr>
				<td><b>Description:</b></td>
				<td>${transaction.description}</td>
			</tr>		
			<tr>
				<td colspan="2"><b>Item List:</b></td>
			</tr>
			<tr>
				<td colspan="2">
				<table border="1">
					<tr>
						<th>No.</th>
						<th>Name</th>
						<th>Quantity</th>
						<th>Price</th>
						<th>Subtotal</th>
					</tr>
					<tr>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		<div>
		<select name="artist">
			<option value="Cash on delivery" selected>Cash on delivery</option>
			<option value="PayPal">PayPal</option>
		</select>
		<br/>		
		<form action="execute_payment" method="post">
			<input type="submit" value="Pay Now" />
		</form>
		</div>
	</div>
	<jsp:directive.include file="../frontend/footer.jsp" />
	
	<script>
		$(document).ready(function() {
			$(".deleteLink").each(function() {
				$(this).on("click", function() {
					albumId = $(this).attr("id");
					if (confirm('Are you sure you want to delete the album with ID ' +  albumId + '?')) {
						window.location = 'delete_album?id=' + albumId;
					}					
				});
			});
		});
	</script>
</body>
</html>