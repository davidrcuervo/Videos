<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set scope="request" var="password_form">
	<form method="post">
		<div class="form-group">
			<label for="email">${lang.out('email') }:</label>
			<div class="input-group input-group-lg">
				<span class="input-group-addon">
					<span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
				</span>
				<input type="text" class="form-control" id="email" name="email" placeholder="${lang.out('email_address') }">
			</div>
			<c:if test="${user.errors['username'] != null}">
				<div class="text-danger text-center">
					<c:forEach var="error" items="${user.errors['username'] }">
						<small>${error}</small><br />
					</c:forEach>
				</div>
			</c:if>
		</div>
		
		<button type="submit" name="submit" value="password" class="btn btn-primary btn-block">${lang.out('password_submit')}</button>
		
		<c:if test="${user.errors['username'] != null}">
			<div class="text-danger text-center">
				<c:forEach var="error" items="${user.errors['username'] }">
					<small>${error}</small><br />
				</c:forEach>
			</div>
		</c:if>
	</form>
</c:set>