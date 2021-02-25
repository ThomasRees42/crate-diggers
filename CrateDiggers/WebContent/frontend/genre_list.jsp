<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Genres- Crate Diggers</title>
<link rel="stylesheet" href="css/style.css" >
</head>
<body>
	<jsp:directive.include file="header.jsp" />
	
	<div class="center">
		<h2>Genres:</h2>
	</div>
	
	<div class="center">
		<div class="group">
			<c:forEach items="${listGenre}" var="genre">
				<div class="album">
					<div>
						<a href="view_genre?id=${genre.genreId}">
							<b>${genre.name}</b>
						</a>
					</div>
					<div>
						<p>${genre.description }</p>	
					</div>
				</div>
				
			</c:forEach>
		</div>
	</div>
	
	<jsp:directive.include file="footer.jsp" />
</body>
</html>