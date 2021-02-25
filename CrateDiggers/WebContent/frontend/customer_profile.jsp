<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer Profile - Crate Diggers</title>
<link rel="stylesheet" href="css/style.css" >
</head>
<body>

	<jsp:directive.include file="header.jsp" />
	
	<div align="center">
		<br/>
		<h2>Welcome, ${loggedCustomer.firstName} ${loggedCustomer.lastName}</h2>
		<br/>	
		<a href="view_orders">My Orders</a> | 
		<a href="personalised_recommendations">My Orders</a> | 
		<a href="logout">Logout</a>
		<table class="normal">
			<tr>
				<td><b>E-mail Address:</b></td>
				<td>${loggedCustomer.email}</td>
			</tr>
			<tr>
				<td><b>First Name:</b></td>
				<td>${loggedCustomer.firstName}</td>
			</tr>
			<tr>
				<td><b>Last Name:</b></td>
				<td>${loggedCustomer.lastName}</td>
			</tr>			
			<tr>
				<td><b>Phone Number:</b></td>
				<td>${loggedCustomer.phone}</td>
			</tr>				
			<tr>
				<td><b>Address Line 1:</b></td>
				<td>${loggedCustomer.addressLine1}</td>
			</tr>
			<tr>
				<td><b>Address Line 2:</b></td>
				<td>${loggedCustomer.addressLine2}</td>
			</tr>			
			<tr>
				<td><b>Town:</b></td>
				<td>${loggedCustomer.town}</td>
			</tr>
			<tr>
				<td><b>County:</b></td>
				<td>${loggedCustomer.county}</td>
			</tr>			
			<tr>
				<td><b>Postcode:</b></td>
				<td>${loggedCustomer.postcode}</td>
			</tr>			
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td colspan="2" align="center"><b><a href="edit_profile">Edit My Profile</a></b></td>
			</tr>		
		</table>
	</div>
		
	<jsp:directive.include file="footer.jsp" />

</body>
</html>