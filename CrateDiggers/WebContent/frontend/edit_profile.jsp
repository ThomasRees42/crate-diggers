<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Register as a Customer</title>
	
	<link rel="stylesheet" href="css/style.css" >
	<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/form-validation.js"></script>
</head>
<body>
	<jsp:directive.include file="header.jsp" />
	
	<div align="center">
		<h2 class="pageheading">
			Edit My Profile
		</h2>
	</div>
	
	<div align="center">
		<form action="update_profile" method="post" id="customerForm" onsubmit="return validateFormInput()">
		
		<table class="form">
			<tr>
				<td align="right">E-mail:</td>
				<td align="left"><b>${loggedCustomer.email}</b> (Cannot be changed)</td>
			</tr>
			<tr>
				<td align="right">First Name:</td>
				<td align="left"><input type="text" id="firstname" name="firstname" size="45" value="${loggedCustomer.firstName}" /></td>
			</tr>
			<tr>
				<td align="right">Last Name:</td>
				<td align="left"><input type="text" id="lastname" name="lastname" size="45" value="${loggedCustomer.lastName}" /></td>
			</tr>			
			<tr>
				<td align="right">Phone Number:</td>
				<td align="left"><input type="text" id="phone" name="phone" size="45" value="${loggedCustomer.phone}" /></td>
			</tr>
			<tr>
				<td align="right">Address Line 1:</td>
				<td align="left"><input type="text" id="address1" name="address1" size="45" value="${loggedCustomer.addressLine1}" /></td>
			</tr>
			<tr>
				<td align="right">Address Line 2:</td>
				<td align="left"><input type="text" id="address2" name="address2" size="45" value="${loggedCustomer.addressLine2}" /></td>
			</tr>
			<tr>
				<td align="right">Town:</td>
				<td align="left"><input type="text" id="town" name="town" size="45" value="${loggedCustomer.town}" /></td>
			</tr>
			<tr>
				<td align="right">County:</td>
				<td align="left"><input type="text" id="county" name="county" size="45" value="${loggedCustomer.county}" /></td>
			</tr>						
			<tr>
				<td align="right">Postcode:</td>
				<td align="left"><input type="text" id="postcode" name="postcode" size="45" value="${loggedCustomer.postcode}" /></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<i>(Leave password fields blank if you don't want to change password)</i>
				</td>
			</tr>
			<tr>
				<td align="right">Password:</td>
				<td align="left"><input type="password" id="password" name="password" size="45" /></td>
			</tr>
			<tr>
				<td align="right">Confirm Password:</td>
				<td align="left"><input type="password" id="confirmPassword" name="confirmPassword" size="45" /></td>
			</tr>																			
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td colspan="2" align="center">
					<button type="submit">Save</button>&nbsp;&nbsp;&nbsp;
					<button id="buttonCancel">Cancel</button>
				</td>
			</tr>				
		</table>
		</form>
	</div>

	<jsp:directive.include file="footer.jsp" />
</body>
<script type="text/javascript">
			function validateFormInput() {
				const firstname = document.getElementById("firstname");
				const lastname = document.getElementById("lastname");
				const password = document.getElementById("password");
				const confirmPassword = document.getElementById("confirmPassword");
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
				
				if (!doubleEntryCheck(password.value, confirmPassword.value)) {
					alert("Passwords do not match!");
					confirmPassword.focus();
					return false;
				} 
				
				if (presenceCheck(password.value)) {
					if (!lengthCheck(password.value, 8, 32)) {
						alert("Password is invalid length (must be at least 8 characters)!");
						password.focus();
						return false;
					}
					
					if (!securityCheck(password.value)) {
						alert("Password is insecure " 
								+ "(must contain at least one uppercase letter, "
								+ "lowercase letter, number and symbol)!");
						password.focus();
						return false;
					}
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
<script type="text/javascript">

	$(document).ready(function() {
		$("#buttonCancel").click(function() {
			history.go(-1);
		});
	});	
</script>
</html>