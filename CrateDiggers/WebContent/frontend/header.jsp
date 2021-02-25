<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="center" id="header">
	<div class="logo">
		<a href="${pageContext.request.contextPath}/">
			<img src="images/CrateDiggersLogo.png" style="width: 10%; height: 10%"/>
		</a>
	</div>
	
	<div>
		<form action="search" method="get">
			<input type="text" name="keyword" size="50" />
			<input type="submit" value="Search" />	
		</form>
		<div id="header-right">
			<a href="about">About Us</a> |
			<a href="view_genres">Genres</a> |
			<a href="view_cart">Cart</a> |
			<c:if test="${loggedCustomer == null}">
				<a href="login">
					<img src="images/customer.png" style="width: 20px; height: 20px;">
				</a>
			</c:if>
				
			<c:if test="${loggedCustomer != null}">
			<a href="view_profile">
				<img src="images/customer.png" style="width: 20px; height: 20px;">
			</a>	
			</c:if>
		</div>
	</div>
</div>