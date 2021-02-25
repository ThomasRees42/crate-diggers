<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Crate Diggers Administration</title>
	<link rel="stylesheet" href="../css/style.css" >
</head>
<body>
	<jsp:directive.include file="header.jsp" />
	
	<div align="center">
		<h2 class="pageheading">Administrative Dashboard</h2>
	</div>
	
	<div align="center">
		<hr width="60%" />
		<h2 class="pageheading">Quick Actions:</h2>
		<b>
		<a href="new_album">New Album</a> &nbsp;
		<a href="user_form.jsp">New User</a> &nbsp;
		<a href="genre_form.jsp">New Genre</a> &nbsp;
		<a href="artist_form.jsp">New Artist</a> &nbsp;
		<a href="new_customer">New Customer</a>
		</b>
	</div>
	
	<div align="center">
		<hr width="60%" />
		<h2 class="pageheading">Statistics:</h2>
		Total Users: ${totalUsers} &nbsp;&nbsp;&nbsp;&nbsp;
		Total Albums: ${totalAlbums} &nbsp;&nbsp;&nbsp;&nbsp;
		Total Genres: ${totalGenres} &nbsp;&nbsp;&nbsp;&nbsp;
		Total Artists: ${totalArtists} &nbsp;&nbsp;&nbsp;&nbsp;
		Total Customers: ${totalCustomers} &nbsp;&nbsp;&nbsp;&nbsp;
		Total Reviews: ${totalReviews} &nbsp;&nbsp;&nbsp;&nbsp;
		Total Orders: ${totalOrders} 	
		<hr width="60%" />
	</div>	
	
	<div align="center">
		<hr width="60%" />
		<h2 class="pageheading">Recent Sales:</h2>
		<table border="1">
			<tr>
				<th>Order ID</th>
				<th>Ordered By</th>
				<th>Album Copies</th>
				<th>Total</th>
				<th>Payment Method</th>
				<th>Status</th>
				<th>Order Date</th>
			</tr>
			<c:forEach items="${listMostRecentSales}" var="order" varStatus="status">
			<tr>
				<td><a href="view_order?id=${order.orderId}">${order.orderId}</a></td>
				<td>${order.customer.firstName} ${order.customer.lastName}</td>
				<td>${order.albumCopies}</td>
				<td><fmt:formatNumber value="${order.total}" type="currency" /></td>
				<td>${order.paymentMethod}</td>
				<td>${order.status}</td>
				<td>${order.orderDate}</td>
			</tr>
			</c:forEach>
		</table>
	</div>

	<div align="center">
		<hr width="60%" />
		<h2 class="pageheading">Recent Reviews:</h2>
		<table border="1">
			<tr>
				<th>Album</th>
				<th>Rating</th>
				<th>Headline</th>
				<th>Customer</th>
				<th>Review On</th>
			</tr>
			<c:forEach items="${listMostRecentReviews}" var="review">
			<tr>
				<td>${review.album.title}</td>
				<td>${review.rating}</td>
				<td><a href="edit_review?id=${review.reviewId}">${review.headline}</a></td>
				<td>${review.customer.firstName} ${review.customer.lastName}</td>
				<td>${review.reviewTime}</td>
			</tr>
			</c:forEach>
		</table>
	</div>
	
	<jsp:directive.include file="footer.jsp" />
</body>
</html>