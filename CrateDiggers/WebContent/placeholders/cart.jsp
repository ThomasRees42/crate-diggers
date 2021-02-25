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
		<h2 class="pageheading">Your cart details: </h2>
	</div>
	
	<div align="center">
		<table border="1" cellpadding="5">
			<tr>
				<th>Index</th>
				<th>Album</th>
				<th>Quantity</th>
				<th>Price</th>
				<th>Subtotal</th>
				<th>Clear Items</th>
			</tr>
		</table>
	</div>
	<div align="center">
		<a href="${pageContext.request.contextPath}/">Continue Shopping</a>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="checkout">Checkout</a>
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