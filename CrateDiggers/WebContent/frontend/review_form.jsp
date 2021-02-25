<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Write a Review - Crate Diggers</title>
	<link rel="stylesheet" href="css/style.css" >
	<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/form-validation.js"></script>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.css">
	<script src="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.js"></script>
</head>
<body>
	<jsp:directive.include file="header.jsp" />
	
	<div align="center">
		<form id="reviewForm" action="submit_review" method="post" onsubmit="return validateFormInput()">
			<table class="normal" width="60%">
				<tr>
					<td><h2>Your Reviews</h2></td>
					<td>&nbsp;</td>
					<td><h2>${loggedCustomer.firstName} ${loggedCustomer.lastName}</h2></td>
				</tr>
				<tr>
					<td colspan="3"><hr/></td>
				</tr>
				<tr>
					<td>
						<span id="album-title">${album.title}</span><br/>
						<img class="album-large" src="data:image/jpg;base64,${album.base64Image}" />
					</td>
					<td>
						<div id="rateYo"></div>
						<input type="hidden" id="rating" name="rating" />
						<input type="hidden" name="albumId" value="${album.albumId}" />
						<br/>
						<input type="text" name="headline" id="headline" size="60" placeholder="Headline or summary for your review (required)" />
						<br/>
						<br/>
						<textarea name="comment" id="comment" cols="70" rows="10" placeholder="Write your review details..." ></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="3" align="center">
						<button type="submit">Submit</button>
						&nbsp;&nbsp;
						<button id="buttonCancel">Cancel</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<jsp:directive.include file="footer.jsp" />
	
<script type="text/javascript">

	function validateFormInput() {
		const rating = document.getElementById("rating");
		const headline = document.getElementById("headline");
		const comment = document.getElementById("comment");
		
		if (!presenceCheck(rating.value)) {
			alert("Rating is required!");
			rating.focus();
			return false;
		}
		
		if (!presenceCheck(headline.value)) {
			alert("Headline is required!");
			headline.focus();
			return false;
		}
		
		if (!lengthCheck(headline.value, 1, 128)) {
			alert("Headine is an invalid length!");
			headline.focus();
			return false;
		}
		
		if (!presenceCheck(comment.value)) {
			alert("Comment is required!");
			comment.focus();
			return false;
		}
		
		if (!lengthCheck(comment.value, 1, 500)) {
			alert("Comment is an invalid length!");
			comment.focus();
			return false;
		}
		
		return true;
	}

	$(document).ready(function() {
		
		$("#buttonCancel").click(function() {
			history.go(-1);
		});

		
		$("#rateYo").rateYo({
			starWidth: "40px",
			fullStar: true,
			onSet: function (rating, rateYoInstance) {
				$("#rating").val(rating);
			}
		});		
	});
</script>	
</body>
</html>