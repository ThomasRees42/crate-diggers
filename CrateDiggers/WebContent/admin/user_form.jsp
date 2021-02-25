<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Create New User</title>
	<link rel="stylesheet" href="../css/style.css" >
	<script type="text/javascript" src="../js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="../js/form-validation.js"></script>
</head>
<body>
	<jsp:directive.include file="header.jsp" />
	
	<div align="center">
		<h2 class="pageheading">
			<c:if test="${user != null}">
				Edit User
			</c:if>
			<c:if test="${user == null}">
				Create New User
			</c:if>
		</h2>
	</div>
	
	<div align="center">
		<c:if test="${user != null}">
			<form action="update_user" method="post" id="userForm" onsubmit="return validateFormInput()">
			<input type="hidden" name="userId" value="${user.userId}">
		</c:if>
		<c:if test="${user == null}">
			<form action="create_user" method="post" id="userForm" onsubmit="return validateFormInput()">
		</c:if>
		
		<table class="form">
			<tr>
				<td align="right">Email:</td>
				<td align="left"><input type="text" id="email" name="email" size="20" value="${user.email}" /></td>
			</tr>
			<tr>
				<td align="right">Full name:</td>
				<td align="left"><input type="text" id="fullname" name="fullname" size="20" value="${user.fullName}" /></td>
			</tr>
			<tr>
				<td align="right">Password:</td>
				<td align="left"><input type="password" id="password" name="password" size="20" value="${user.password}" /></td>
			</tr>	
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td colspan="2" align="center">
					<button type="submit">Save</button>&nbsp;&nbsp;&nbsp;
					
				</td>
			</tr>				
		</table>
		</form>
		<button id="buttonCancel">Cancel</button>
	</div>

	<jsp:directive.include file="footer.jsp" />
</body>
<script type="text/javascript">
	function validateFormInput() {
		const email = document.getElementById("email");
		const fullName = document.getElementById("fullname");
		const password = document.getElementById("password");
		
		if (!presenceCheck(email.value)) {
			alert("Email is required!");
			email.focus();
			return false;
		}
		
		if (!emailFormatCheck(email.value)) {
			alert("Email is in wrong format!");
			email.focus();
			return false;
		}
		
		if (!lengthCheck(email.value, 7, 64)) {
			alert("Email is an invalid length!");
			email.focus();
			return false;
		}
		
		if (!presenceCheck(fullName.value)) {
			alert("Full name is required!");
			fullName.focus();
			return false;
		}
		
		if (!lengthCheck(fullName.value, 8, 30)) {
			alert("Full name is an invalid length!");
			fullName.focus();
			return false;
		}
		
		<c:if test="${user != null}">
		if (presenceCheck(password.value)) {
			
			if (!lengthCheck(password.value, 8, 32)) {
				alert("Password is invalid length!");
				password.focus();
				return false;
			}
			
			if (!securityCheck(password.value)) {
				alert("Password is insecure!");
				password.focus();
				return false;
			}
		}
		</c:if>
		
		<c:if test="${user == null}">
		if (!presenceCheck(password.value)) {
			alert("Password is required!");
			password.focus();
			return false;
		}
		
		if (!lengthCheck(password.value, 8, 32)) {
			alert("Password is invalid length!");
			password.focus();
			return false;
		}
		
		if (!securityCheck(password.value)) {
			alert("Password is insecure!");
			password.focus();
			return false;
		}
		</c:if>
		
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