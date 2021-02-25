	<div align="center">
		<h2>Order Overview:</h2>
		<table>
			<tr>
				<td><b>Ordered By: </b></td>
				<td>${order.customer.firstName} ${order.customer.lastName}</td>
			</tr>
			<tr>
				<td><b>Order Status: </b></td>
				<td>${order.status}</td>
			</tr>
			<tr>
				<td><b>Order Date: </b></td>
				<td>${order.orderDate}</td>
			</tr>
			<tr>
				<td><b>Payment Method: </b></td>
				<td>${order.paymentMethod}</td>
			</tr>						
			<tr>
				<td><b>Album Copies: </b></td>
				<td>${order.albumCopies}</td>
			</tr>
			<tr>
				<td><b>Total Amount: </b></td>
				<td><fmt:formatNumber value="${order.total}" type="currency" /></td>
			</tr>
		</table>
		<h2>Recipient Information</h2>
		<table>			
			<tr>
				<td><b>First Name: </b></td>
				<td>${order.firstName}</td>
			</tr>
			<tr>
				<td><b>Last Name: </b></td>
				<td>${order.lastName}</td>
			</tr>			
			<tr>
				<td><b>Phone: </b></td>
				<td>${order.phone}</td>
			</tr>
			<tr>
				<td><b>Address Line 1: </b></td>
				<td>${order.addressLine1}</td>
			</tr>
			<tr>
				<td><b>Address Line 2: </b></td>
				<td>${order.addressLine2}</td>
			</tr>					
			<tr>
				<td><b>Town: </b></td>
				<td>${order.town}</td>
			</tr>
			<tr>
				<td><b>County: </b></td>
				<td>${order.county}</td>
			</tr>
			<%--<tr>
				<td><b>Country: </b></td>
				<td>${order.countryName}</td>
			</tr>
			<tr>--%>
				<td><b>Postcode: </b></td>
				<td>${order.postcode}</td>
			</tr>															
		</table>
	</div>
	<div align="center">
		<h2>Ordered Albums</h2>
		<table border="1">
			<tr>
				<th>Index</th>
				<th>Album Title</th>
				<th>Artist</th>
				<th>Price</th>
				<th>Quantity</th>
				<th>Subtotal</thd>
			</tr>
			<c:forEach items="${order.orderDetails}" var="orderDetail" varStatus="status">
			<tr>
				<td>${status.index + 1}</td>
				<td>
					<img style="vertical-align: middle;" src="data:image/jpg;base64,${orderDetail.album.base64Image}" width="48" height="64" />
					${orderDetail.album.title}
				</td>
				<td>${orderDetail.album.artist.name}</td>
				<td><fmt:formatNumber value="${orderDetail.album.price}" type="currency" /></td>
				<td>${orderDetail.quantity}</td>
				<td><fmt:formatNumber value="${orderDetail.subtotal}" type="currency" /></td>
			</tr>
			</c:forEach>
			<tr>
				<td colspan="6" align="right">
					<p>Subtotal: <fmt:formatNumber value="${order.subtotal}" type="currency" /></p>
					<p>Tax: <fmt:formatNumber value="${order.tax}" type="currency" /></p>
					<p>Shipping Fee: <fmt:formatNumber value="${order.shippingFee}" type="currency" /></p>
					<p>TOTAL: <fmt:formatNumber value="${order.total}" type="currency" /></p>
				</td>
			</tr>
		</table>
	</div>