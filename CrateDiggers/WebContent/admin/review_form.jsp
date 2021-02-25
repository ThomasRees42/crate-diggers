<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="../css/style.css" >
	<script type="text/javascript" src="../js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>	
	<script type="text/javascript" src="../js/form-validation.js"></script>
<title>Edit Review</title>
</head>
<body>
	<jsp:directive.include file="header.jsp" />
	
	<div align="center">
		<h2 class="pageheading">Edit Review</h2>
	</div>
	
	<div align="center">
		<form action="update_review" method="post" id="reviewForm" onsubmit="return validateFormInput()">
		<input type="hidden" name="reviewId" value="${review.reviewId}">

		
		<table class="form">
			<tr>
				<td align="right">Album:</td>
				<td align="left"><b>${review.album.title}</b></td>
			</tr>
			<tr>
				<td align="right">Rating:</td>
				<td align="left"><b>${review.rating}</b></td>
			</tr>
			<tr>
				<td align="right">Customer:</td>
				<td align="left"><b>${review.customer.firstName} ${review.customer.lastName}</b></td>
			</tr>
			<tr>
				<td align="right">Headline:</td>
				<td align="left">
					<input type="text" size="60" name="headline" id="headline" value="${review.headline}" />
				</td>
			</tr>
			<tr>
				<td align="right">Comment:</td>
				<td align="left">
					<textarea rows="5" cols="70" name="comment" id="comment">${review.comment}</textarea>
				</td>
			</tr>			
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td colspan="2" align="center">
					<button type="submit">Save</button>&nbsp;&nbsp;&nbsp;
					<input type="button" id="buttonCancel" value="Cancel" />
				</td>
			</tr>				
		</table>
		</form>
	</div>

	<jsp:directive.include file="footer.jsp" />
</body>
<script type="text/javascript">
	function validateFormInput() {
		const headline = document.getElementById("headline");
		const comment = document.getElementById("comment");
		
		if (!presenceCheck(headline.value)) {
			alert("Headline is required!");
			headline.focus();
			return false;
		}
		
		if (!lengthCheck(headline.value, 1, 128)) {
			alert("Headine name is an invalid length!");
			headline.focus();
			return false;
		}
		
		if (!presenceCheck(comment.value)) {
			alert("Comment is required!");
			comment.focus();
			return false;
		}
		
		if (!lengthCheck(comment.value, 1, 500)) {
			alert("Headine name is an invalid length!");
			comment.focus();
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