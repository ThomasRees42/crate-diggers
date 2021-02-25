<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Crate Diggers - Online Record Store</title>
<link rel="stylesheet" href="css/style.css" >
</head>
<body>
	<jsp:directive.include file="header.jsp" />
	
	<div class="center">
		<div>
			<h1>Welcome to Crate Diggers</h1>
			<p>
				We are the only independent music store located in Swansea, 
				selling a wide range of vinyl records since our establishment in 1956.
			</p>
		</div>		
		<div>
			<h2>New Albums:</h2>
			<c:forEach items="${listNewAlbums}" var="album">
				<jsp:directive.include file="album_group.jsp" />
			</c:forEach>
		</div>
		<div class="next-row">
			<h2>Best-Selling Albums:</h2>
			<c:forEach items="${listBestSellingAlbums}" var="album">
				<jsp:directive.include file="album_group.jsp" />
			</c:forEach>			
		</div>
		<div class="next-row">
			<h2>Most-favored Albums:</h2>
			<c:forEach items="${listFavoredAlbums}" var="album">
				<jsp:directive.include file="album_group.jsp" />
			</c:forEach>			
		</div>
		<br/><br/>
	</div>
	
	<jsp:directive.include file="footer.jsp" />
</body>
</html>