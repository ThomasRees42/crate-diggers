<div class="album">
	<div>
		<a href="view_album?id=${album.albumId}"> 
		<img class="album-small"
			src="data:image/jpg;base64,${album.base64Image}" />
		</a>
	</div>
	<div>
		<a href="view_album?id=${album.albumId}"> <b>${album.title}</b>
		</a>
	</div>
	<div>
		<jsp:directive.include file="album_rating.jsp" />				
	</div>
	<div>
		<a href="view_artist?id=${album.artist.artistId}">
			<i>by ${album.artist.name}</i>
		</a>
	</div>
	<div>
		<b>£${album.price}</b>
	</div>
</div>