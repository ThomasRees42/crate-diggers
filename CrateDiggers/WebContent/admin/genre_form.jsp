<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="../css/style.css" >
	<link rel="stylesheet" href="../css/jquery-ui.min.css">
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="..//css/richtext.min.css">	
	<script type="text/javascript" src="../js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="../js/form-validation.js"></script>
	<script type="text/javascript" src="../js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../js/jquery.richtext.min.js"></script>
<title>
	<c:if test="${genre != null}">
		Edit Genre
	</c:if>
	<c:if test="${genre == null}">
		Create New Genre
	</c:if>
</title>
</head>
<body>
	<jsp:directive.include file="header.jsp" />
	
	<div align="center">
		<h2 class="pageheading">
			<c:if test="${genre != null}">
				Edit Genre
			</c:if>
			<c:if test="${genre == null}">
				Create New Genre
			</c:if>
		</h2>
	</div>
	
	<div align="center">
		<c:if test="${genre != null}">
			<form action="update_genre" method="post" id="genreForm" onsubmit="return validateFormInput()">
			<input type="hidden" name="genreId" value="${genre.genreId}">
		</c:if>
		<c:if test="${genre == null}">
			<form action="create_genre" method="post" id="genreForm" onsubmit="return validateFormInput()">
		</c:if>
		
		<table class="form">
			<tr>
				<td align="right">Name:</td>
				<td align="left"><input type="text" id="name" name="name" size="20" value="${genre.name}" /></td>
			</tr>
			<tr>
				<td align="right">Description:</td>
				<td align="left">
					<textarea rows="5" cols="50" name="description" id="description">${genre.description}</textarea>
				</td>
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
		const name = document.getElementById("name");
		const description = document.getElementById("description");
		
		if (!presenceCheck(name.value)) {
			alert("Name is required!");
			name.focus();
			return false;
		}
		
		if (!lengthCheck(name.value, 1, 30)) {
			alert("Name is an invalid length!");
			name.focus();
			return false;
		}
		
		if (!presenceCheck(description.value)) {
			alert("Description is required!");
			description.focus();
			return false;
		}
		
		if (!lengthCheck(description.value, 20, 10000 + 36451)) {
			alert("Description is an invalid length!");
			description.focus();
			return false;
		}
		
		return true;
	}
</script>
<script type="text/javascript">
$(document).ready(function() {
	$('#description').richText();
	$("#buttonCancel").click(function() {
		history.go(-1);
	});	
});
</script>
</html>