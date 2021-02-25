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
	<center>
	<div>
		<h1>Welcome to Crate Diggers</h1>
		<p>
			 We are the only independent music store located in Swansea, selling a wide range of vinyl records since our establishment in 1956.
		</p>
	</div>
	
	<img src="${pageContext.request.contextPath}/images/shop.jpg" style="height: 50%; width: 50%;">
	
	<div class="row">
		<!-- 1 column-->
		<div class="span-one-third">
			<h3>History</h3>
			<p style="padding-left: 20%;padding-right: 20%;">
				Crate Diggers was established in 1956 in Port Talbot in a small unit in Talbot Street. It sold records, radios and various hi-fi equpment. A second shop followed a few years later in Church Street, Port Talbot which was most probably the first dedicated pop/rock shop in Wales. During the redevelopment of Port Talbot in 1966/67 the Talbot Street shop moved to Cwmavon square and later to Station Road. This shop closed after the death of the shop's founder in 1985. The Church Street shop moved to Swansea to the Oxford street site which opened in October 1968 and continues to trade to this day.
			</p>
		</div>
		<!-- 2nd column-->
		<div class="span-one-third" style="float: left; padding-left: 10%">
			<h3>Contact</h3>
			<p>
				Address<br>
				221 Oxford Street,<br>
				Swansea.<br>
				SA1 3BQ<br>
				<br>
				Telephone<br>
				01792 654 226<br>
				<br>
				TRADING TIMES<br>
				MONDAY to SATURDAY 9.00am-17.30pm<br>
				SUNDAY 11.00am-16.00pm<br>
				BANK HOLIDAYS 11.00am-16.00pm<br>
				Closed on: Christmas Day, bank holiday January 1st and Easter Sunday<br>
				<br>
				Email<br>
				info@cratediggers.co.uk
			</p>
		</div>
		<!-- 3rd column-->
		<div class="span-one-third" style="float: right; padding-right: 10%">
			<iframe width="622" height="350" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" id="gmap_canvas" src="https://maps.google.com/maps?width=622&amp;height=421&amp;hl=en&amp;q=221%20Oxford%20Street%20Swansea+(Crate%20Diggers)&amp;t=&amp;z=11&amp;ie=UTF8&amp;iwloc=B&amp;output=embed"></iframe> <a href='https://www.horando.de/en/'>Classic watches</a> <script type='text/javascript' src='https://embedmaps.com/google-maps-authorization/script.js?id=56a160ad364fa7280e3700afa08e59aadb01ad27'></script>
		</div>
	</div>
	
	</center>
	<jsp:directive.include file="footer.jsp" />
</body>
</html>