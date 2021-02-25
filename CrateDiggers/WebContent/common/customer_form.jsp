		<table class="form">
			<tr>
				<td align="right">E-mail:</td>
				<td align="left"><input type="text" id="email" name="email" size="45" value="${customer.email}" /></td>
			</tr>
			<tr>
				<td align="right">First Name:</td>
				<td align="left"><input type="text" id="firstname" name="firstname" size="45" value="${customer.firstName}" /></td>
			</tr>
			<tr>
				<td align="right">Last Name:</td>
				<td align="left"><input type="text" id="lastname" name="lastname" size="45" value="${customer.lastName}" /></td>
			</tr>			
			<tr>
				<td align="right">Password:</td>
				<td align="left"><input type="password" id="password" name="password" size="45" value="${customer.password}" /></td>
			</tr>
			<tr>
				<td align="right">Confirm Password:</td>
				<td align="left"><input type="password" id="confirmPassword" name="confirmPassword" size="45" value="${customer.password}" /></td>
			</tr>			
			<tr>
				<td align="right">Phone Number:</td>
				<td align="left"><input type="text" id="phone" name="phone" size="45" value="${customer.phone}" /></td>
			</tr>
			<tr>
				<td align="right">Address Line 1:</td>
				<td align="left"><input type="text" id="address1" name="address1" size="45" value="${customer.addressLine1}" /></td>
			</tr>
			<tr>
				<td align="right">Address Line 2:</td>
				<td align="left"><input type="text" id="address2" name="address2" size="45" value="${customer.addressLine2}" /></td>
			</tr>			
			<tr>
				<td align="right">Town:</td>
				<td align="left"><input type="text" id="town" name="town" size="45" value="${customer.town}" /></td>
			</tr>
			<tr>
				<td align="right">County:</td>
				<td align="left"><input type="text" id="county" name="county" size="45" value="${customer.county}" /></td>
			</tr>			
			<tr>
				<td align="right">Postcode:</td>
				<td align="left"><input type="text" id="postcode" name="postcode" size="45" value="${customer.postcode}" /></td>
			</tr>															
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td colspan="2" align="center">
					<button type="submit">Save</button>&nbsp;&nbsp;&nbsp;
					<input type="button" value="Cancel" onclick="history.go(-1);" />
				</td>
			</tr>				
		</table>
		<script type="text/javascript">
			function validateFormInput() {
				const email = document.getElementById("email");
				const firstname = document.getElementById("firstname");
				const lastname = document.getElementById("lastname");
				const password = document.getElementById("password");
				const confirmPassword = document.getElementById("confirmPassword");
				const phone = document.getElementById("phone");
				const address1 = document.getElementById("address1");
				const address2 = document.getElementById("address2");
				const town = document.getElementById("town");
				const county = document.getElementById("county");
				const postcode = document.getElementById("postcode");
				
				if (!presenceCheck(email.value)) {
					alert("Email is required!");
					email.focus();
					return false;
				}
				
				if (!emailFormatCheck(email.value)) {
					alert("Email is in wrong format!");
					email.focus();
					return false;
				}
				
				if (!lengthCheck(email.value, 7, 64)) {
					alert("Email is an invalid length!");
					email.focus();
					return false;
				}
				
				if (!presenceCheck(firstname.value)) {
					alert("First name is required!");
					firstname.focus();
					return false;
				}
				
				if (!lengthCheck(firstname.value, 2, 30)) {
					alert("First name is an invalid length!");
					firstname.focus();
					return false;
				}
				
				if (!presenceCheck(lastname.value)) {
					alert("Last name is required!");
					lastname.focus();
					return false;
				}
				
				if (!lengthCheck(lastname.value, 2, 30)) {
					alert("Last name is an invalid length!");
					lastname.focus();
					return false;
				}
				
				if (!doubleEntryCheck(password.value, confirmPassword.value)) {
					alert("Passwords do not match!");
					confirmPassword.focus();
					return false;
				} 
				
				<c:if test="${customer != null}">
				if (presenceCheck(password.value)) {
					
					if (!lengthCheck(password.value, 8, 32)) {
						alert("Password is invalid length!");
						password.focus();
						return false;
					}
					
					if (!securityCheck(password.value)) {
						alert("Password is insecure " 
								+ "(must contain at least one uppercase letter, "
								+ "lowercase letter, number and symbol)!");
						password.focus();
						return false;
					}
				}
				</c:if>
				
				<c:if test="${customer == null}">
				if (!presenceCheck(password.value)) {
					alert("Password is required!");
					password.focus();
					return false;
				}
				
				if (!lengthCheck(password.value, 8, 32)) {
					alert("Password is invalid length!");
					password.focus();
					return false;
				}
				
				if (!securityCheck(password.value)) {
					alert("Password is insecure " 
							+ "(must contain at least one uppercase letter, "
							+ "lowercase letter, number and symbol)!");
					password.focus();
					return false;
				}
				</c:if>
				
				if (!presenceCheck(phone.value)) {
					alert("Phone is required!");
					phone.focus();
					return false;
				}
				
				if (!lengthCheck(phone.value, 9, 15)) {
					alert("Phone is an invalid length!");
					phone.focus();
					return false;
				}
				
				if (!numericTypeCheck(phone.value)) {
					alert("Phone must be numerical!");
					phone.focus();
					return false;
				}
				
				if (!presenceCheck(address1.value)) {
					alert("Address line 1 is required!");
					address1.focus();
					return false;
				}
				
				if (!lengthCheck(address1.value, 4, 128)) {
					alert("Address line 1 is an invalid length!");
					address1.focus();
					return false;
				}
				
				if (!presenceCheck(address2.value)) {
					alert("Address line 2 is required!");
					address2.focus();
					return false;
				}
				
				if (!lengthCheck(address2.value, 4, 128)) {
					alert("Address line 2 is an invalid length!");
					address2.focus();
					return false;
				}
				
				if (!presenceCheck(town.value)) {
					alert("Town is required!");
					town.focus();
					return false;
				}
				
				if (!lengthCheck(town.value, 4, 32)) {
					alert("Town is an invalid length!");
					town.focus();
					return false;
				}
				
				if (!presenceCheck(county.value)) {
					alert("County is required!");
					county.focus();
					return false;
				}
				
				if (!lengthCheck(county.value, 4, 64)) {
					alert("County is an invalid length!");
					county.focus();
					return false;
				}
				
				if (!presenceCheck(postcode.value)) {
					alert("Postcode is required!");
					postcode.focus();
					return false;
				}
				
				if (!lengthCheck(postcode.value, 6, 7)) {
					alert("Postcode is an invalid length!");
					postcode.focus();
					return false;
				}
				
				return true;
			}
		</script>
		<script type="text/javascript">
		$(document).ready(function() {
			$("#buttonCancel").click(function() {
				history.go(-1);
			});
		});
		</script>