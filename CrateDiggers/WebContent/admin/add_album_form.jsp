<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Album to Order</title>
</head>
<body>
	<div align="center">
		<h2>Add album to Order ID: ${order.orderId}</h2>
		<form action="add_album_to_order" method="post">
		<table>
			<tr>
				<td>Select an album: </td>
				<td>
					<select name="albumId">
						<c:forEach items="${listAlbum}" var="album" varStatus="status">
							<option value="${album.albumId}">${album.title} - ${album.artist.name}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td>Number of Copies</td>
				<td>
					<select name="quantity">
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
					</select>
				</td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="Add" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="Cancel" onclick="javascript: self.close();" />
				</td>
			</tr>
		</table>
		</form>
		
	</div>
</body>
</html>