<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Create New Album</title>
	
	<link rel="stylesheet" href="../css/style.css" >
	<link rel="stylesheet" href="../css/jquery-ui.min.css">
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="..//css/richtext.min.css">	
	
	<script type="text/javascript" src="../js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
	
	<script type="text/javascript" src="../js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../js/jquery.richtext.min.js"></script>
	
	<script type="text/javascript" src="../js/form-validation.js"></script>
</head>
<body>
	<jsp:directive.include file="header.jsp" />
	
	<div align="center">
		<h2 class="pageheading">
			<c:if test="${album != null}">
				Edit Album
			</c:if>
			<c:if test="${album == null}">
				Create New Album
			</c:if>
		</h2>
	</div>
	
	<div align="center">
		<c:if test="${album != null}">
			<form action="update_album" method="post" id="albumForm" enctype="multipart/form-data" onsubmit="return validateFormInput()">
			<input type="hidden" name="albumId" value="${album.albumId}">
		</c:if>
		<c:if test="${album == null}">
			<form action="create_album" method="post" id="albumForm" enctype="multipart/form-data" onsubmit="return validateFormInput()">
		</c:if>
		
		<table class="form">
			<tr>
				<td>Artist:</td>
				<td>
					<select name="artist">
						<c:forEach items="${listArtist}" var="artist">
							<c:if test="${artist.artistId eq album.artist.artistId}">
								<option value="${artist.artistId}" selected>
							</c:if>
							<c:if test="${artist.artistId ne album.artist.artistId}">
								<option value="${artist.artistId}">
							</c:if>							
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
						<c:forEach items="${listGenre}" var="genre">
							<c:if test="${genre.genreId eq album.genre.genreId}">
								<option value="${genre.genreId}" selected>
							</c:if>
							<c:if test="${genre.genreId ne album.genre.genreId}">
								<option value="${genre.genreId}">
							</c:if>							
								${genre.name}
							</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right">Title:</td>
				<td align="left"><input type="text" id="title" name="title" size="20" value="${album.title}" /></td>
			</tr>
			<tr>
				<td align="right">Release Date (dd/mm/yyyy):</td>
				<td align="left"><input type="text" id="releaseDate" name="releaseDate" size="20" 
					value="<fmt:formatDate pattern='dd/MM/yyyy' value='${album.releaseDate}' />" /></td>
			</tr>			
			<tr>
				<td align="right">Album Image:</td>
				<td align="left">
					<input type="file" id="albumImage" name="albumImage" size="20" /><br/>
					<img id="thumbnail" alt="Image Preview" style="width:20%; margin-top: 10px"
						src="data:image/jpg;base64,${album.base64Image}"
					 />
				</td>
			</tr>
			<tr>
				<td align="right">Price (£):</td>
				<td align="left"><input type="text" id="price" name="price" size="20" value="${album.price}" /></td>
			</tr>
			<tr>
				<td align="right">Description:</td>
				<td align="left">
					<textarea rows="5" cols="50" name="description" id="description">${album.description}</textarea>
				</td>
			</tr>										
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td colspan="2" align="center">
					<button type="submit">Save</button>&nbsp;&nbsp;&nbsp;
				</td>
			</tr>				
		</table>
		</form>
		<button id="buttonCancel">Cancel</button>
	</div>

	<jsp:directive.include file="footer.jsp" />
</body>
<script type="text/javascript">
	function validateFormInput() {
		const title = document.getElementById("title");
		const description = document.getElementById("description");
		const price = document.getElementById("price");
		const albumImage = document.getElementById("albumImage");
		const releaseDate = document.getElementById("releaseDate");
		
		if (!presenceCheck(title.value)) {
			alert("Title is required!");
			title.focus();
			return false;
		}
		
		if (!lengthCheck(title.value, 1, 128)) {
			alert("Title is an invalid length!");
			title.focus();
			return false;
		}
		
		if (!presenceCheck(description.value)) {
			alert("Description is required!");
			description.focus();
			return false;
		}
		
		if (!lengthCheck(description.value, 20, 10000 + 36451)) {
			alert("Description is an invalid length!");
			description.focus();
			return false;
		}
		
		if (!presenceCheck(price.value)) {
			alert("Price is required!");
			price.focus();
			return false;
		}

		if (!numericTypeCheck(price.value)) {
			alert("Price must be a number!");
			price.focus();
			return false;
		}
		
		if (!rangeCheck(price.value, 0, 100)) {
			alert("Invalid price!");
			price.focus();
			return false;
		}
		
		if (!priceFormatCheck(price.value)) {
			alert("Invalid price!");
			price.focus();
			return false;
		}
		
		<c:if test="${album == null}">
		if (!presenceCheck(albumImage.value)) {
			alert("Image is required!");
			albumImage.focus();
			return false;
		}
		</c:if>
		
		if (!presenceCheck(releaseDate.value)) {
			alert("Release date is required!");
			releaseDate.focus();
			return false;
		}
		
		if (!dateFormatCheck(releaseDate.value)) {
			alert("Release date is in an invalid format!");
			releaseDate.focus();
			return false;
		}
		
		return true;
	}
</script>
<script type="text/javascript">

	$(document).ready(function() {
		$('#releaseDate').datepicker({ dateFormat: 'dd/mm/yy' });
		$('#description').richText();
		
		$('#bookImage').change(function() {
			showImageThumbnail(this);
		});
		
		$("#buttonCancel").click(function() {
			history.go(-1);
		});
	});
	
	function showImageThumbnail(fileInput) {
		var file = fileInput.files[0];
		
		var reader = new FileReader();
		
		reader.onload = function(e) {
			$('#thumbnail').attr('src', e.target.result);
		};
		
		reader.readAsDataURL(file);
	}
</script>
</html>