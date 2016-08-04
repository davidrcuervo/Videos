<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set scope="request" var="login_form">
	<form method="post">
		<div class="form-group">
			<label for="login_email">${lang.out('email') }:</label>
			<div class="input-group input-group-lg">
				<span class="input-group-addon">
					<span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
				</span>
				<input type="text" class="form-control" id="login_email" name="username" placeholder="${lang.out('email_address') }">
			</div>
			<c:if test="${auth.errors['username'] != null}">
				<div class="text-danger text-center">
					<c:forEach var="error" items="${auth.errors['username'] }">
						<small>${error}</small><br />
					</c:forEach>
				</div>
			</c:if>
		</div>
		<div class="form-group">
			<label for="login_password"><span class="" aria-hidden="true"></span> ${lang.out('password') }:</label>
			<div class="input-group input-group-lg">
				<span class="input-group-addon">
					<span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
				</span>
				<input class="form-control" type="password" id="login_password" name="password" placeholder="${lang.out('password_placeholder') }">
			</div>
			<c:if test="${auth.errors['password'] != null}">
				<div class="text-danger text-center">
					<c:forEach var="error" items="${auth.errors['password'] }">
						<small>${error}</small><br />
					</c:forEach>
				</div>
			</c:if>
		</div>
			
		<button type="submit" class="btn btn-primary btn-lg btn-block"><span class="glyphicon glyphicon-log-in"></span>  ${lang.out('login') }</button>
		<c:if test="${auth.errors['login'] != null}">
			<div class="text-danger text-center">
				<c:forEach var="error" items="${auth.errors['login'] }">
					<small>${error}</small><br />
				</c:forEach>
			</div>
		</c:if>
	</form>
</c:set>