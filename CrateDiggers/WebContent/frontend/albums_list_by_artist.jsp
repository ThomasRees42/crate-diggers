<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Album in ${artist.name} - Crate Diggers</title>
<link rel="stylesheet" href="css/style.css" >
</head>
<body>
	<jsp:directive.include file="header.jsp" />
	
	<div class="center">
		<h2>${artist.name}</h2>
		${artist.description}
	</div>
	
	<div class="album-group">
		<c:forEach items="${listAlbums}" var="album">
			<div class="album">
				<div>
					<a href="view_album?id=${album.albumId}">
						<img class="album-small" src="data:image/jpg;base64,${album.base64Image}" />
					</a>
				</div>
				<div>
					<a href="view_album?id=${album.albumId}">
						<b>${album.title}</b>
					</a>
				</div>
				<div>
					<jsp:directive.include file="album_rating.jsp" />
				</div>
				<div><i>by ${album.artist.name}</i></div>
				<div><b>£${album.price}</b></div>
			</div>
			
		</c:forEach>
	</div>
	
	<jsp:directive.include file="footer.jsp" />
</body>
</html>