<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<div align="center">
		<div>
			<a href="${pageContext.request.contextPath}/admin/">
				<img src="../images/CrateDiggersLogo.png" style="width: 10%; height: 10%"/>
			</a>
		</div>
		<div>
			Welcome, <c:out value="${sessionScope.useremail}" /> | <a href="logout">Logout</a>
			<br/><br/>
		</div>
		<div id="headermenu">
			<div>
				<a href="list_users">
					Users
				</a>
			</div>
			<div>
				<a href="list_genre">
					Genre
				</a>
			</div>
			<div>
				<a href="list_artist">
					Artist
				</a>
			</div>
			<div>
				<a href="list_albums">
					Albums
				</a>
			</div>
			<div>
				<a href="list_customer">
					Customers
				</a>
			</div>
			<div>
				<a href="list_review">
					Reviews
				</a>
			</div>
			<div>
				<a href="list_order">
					Orders
				</a>
			</div>
		</div>
	</div>