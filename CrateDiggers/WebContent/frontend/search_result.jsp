<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Results for ${keyword} - Crate Diggers</title>
<link rel="stylesheet" href="css/style.css">
<script type="text/javascript" src="js/form-validation.js"></script>
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div class="center">
		<c:if test="${fn:length(result) == 0}">
			<h2>No Results for "${keyword}"</h2>
		</c:if>
		<c:if test="${fn:length(result) > 0}">
			<div class="album-group">
				<center><h2>Results for "${keyword}":</h2>
				<div>
					<form id="searchForm" action="filter_search" method="get" onsubmit="return validateFormInput()">
						<input name="keyword" type="hidden" value="${keyword}"></input>
						<table class="form">
							<tr>
								<td>Artist:</td>
								<td>
									<select name="artist">
										<option value="-1">					
											All
										</option>
										<c:forEach items="${listArtist}" var="artist">
											<option value="${artist.artistId}" ${artist.artistId == artistId ? "selected" : ""}>					
												${artist.name}
											</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td>Genre:</td>
								<td>
									<select name="genre">
										<option value="-1">					
											All
										</option>
										<c:forEach items="${listGenre}" var="genre">
											<option value="${genre.genreId}" ${genre.genreId == genreId ? "selected" : ""}>						
												${genre.name}
											</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td>Price:</td>
								<td>
									from: £<input type="text" name="minPrice" id="minPrice" value="${minPrice }">
									to: £<input type="text" name="maxPrice" id="maxPrice" value="${maxPrice }">
								</td>
							</tr>
							<tr>
								<td>Sort by:</td>
								<td>
									<select name="sortBy">
										<option value="none">None</option>
										<option value="ascendingPrice" ${sortBy == "ascendingPrice" ? "selected" : ""}>Price: low to high</option>
										<option value="descendingPrice" ${sortBy == "descendingPrice" ? "selected" : ""}>Price: high to low</option>
										<option value="bestSelling" ${sortBy == "bestSelling" ? "selected" : ""}>Best-selling</option>
										<option value="highestRated" ${sortBy == "highestRated" ? "selected" : ""}>Highest-rated</option>
										<option value="newest" ${sortBy == "newest" ? "selected" : ""}>Newest</option>
									</select>
								</td>
							</tr>
						</table>
						<input type="submit" value="Filter">
					</form>
				</div>
				</center>
				<c:forEach items="${result}" var="album">
					<div>
						<div id="search-image">
							<a href="view_album?id=${album.albumId}"> 
								<img class="album-small" src="data:image/jpg;base64,${album.base64Image}" />
							</a>
						</div>
						<div id="search-description">
							<div>
								<h2><a href="view_album?id=${album.albumId}"> <b>${album.title}</b></a></h2>
							</div>
							<jsp:directive.include file="album_rating.jsp" />
							<div>
								<i>by ${album.artist.name}</i>
							</div>
							<div>
								<p>${album.description}</p>
							</div>					
						</div>
						<div id="search-price">
							<h3>£${album.price}</h3>
							<h3><a href="add_to_cart?album_id=${album.albumId}">Add To Cart</a></h3>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
			
	</c:if>

	<jsp:directive.include file="footer.jsp" />
</body>
<script type="text/javascript">

	function validateFormInput() {
		const minPrice = document.getElementById("minPrice");
		const maxPrice = document.getElementById("maxPrice");
		
		if (!presenceCheck(minPrice.value)) {
			alert("Minimum price is required!");
			minPrice.focus();
			return false;
		}
		
		if (!numericTypeCheck(minPrice.value)) {
			alert("Minimum price must be numerical!");
			minPrice.focus();
			return false;
		}
		
		if (!rangeCheck(minPrice.value, 0, parseInt(maxPrice.value))) {
			alert("Minimum price cannot be great than the maximum price!");
			minPrice.focus();
			return false;
		}
		
		if (!priceFormatCheck(minPrice.value)) {
			alert("Minimum price is an invalid price!");
			minPrice.focus();
			return false;
		}
		
		if (!presenceCheck(maxPrice.value)) {
			alert("Maximum price is required!");
			maxPrice.focus();
			return false;
		}
		
		if (!numericTypeCheck(maxPrice.value)) {
			alert("Maximum price must be numerical!");
			maxPrice.focus();
			return false;
		}
		
		if (!rangeCheck(maxPrice.value, parseInt(minPrice.value), 999999999999999)) {
			alert("Maximum price cannot be less than the minimum price!");
			minPrice.focus();
			return false;
		}
		
		if (!priceFormatCheck(maxPrice.value)) {
			alert("Maximum price is an invalid price!");
			maxPrice.focus();
			return false;
		}
		
		return true;
	}
</script>
</html>