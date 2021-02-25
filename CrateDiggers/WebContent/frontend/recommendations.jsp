<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Personalised Recommendations</title>
<link rel="stylesheet" href="css/style.css" >
</head>
<body>
	<jsp:directive.include file="header.jsp" />
	
	<div class="center">
		<h2>Personalised Recommendations</h2>
	</div>
	
	<div class="album-group">
		<c:forEach items="${recommendations.subList(0,4)}" var="album">
				<jsp:directive.include file="album_group.jsp" />
		</c:forEach>
		<c:forEach items="${recommendations.subList(4,8)}" var="album">
				<jsp:directive.include file="album_group.jsp" />
		</c:forEach>
	</div>
	
	<jsp:directive.include file="footer.jsp" />
</body>
</html>