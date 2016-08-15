<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set scope="request" var="signup_form">
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
						<small>${lang.out(error)}</small><br />
					</c:forEach>
				</div>
			</c:if>
		</div>
		
		<div class="form-group">
			<label for="password"><span class="" aria-hidden="true"></span> ${lang.out('password') }:</label>
			<div class="input-group input-group-lg">
				<span class="input-group-addon">
					<span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
				</span>
				<input class="form-control" type="password" id="password" name="password" placeholder="${lang.out('password_placeholder') }">
			</div>
			<c:if test="${user.errors['password'] != null}">
				<div class="text-danger text-center">
					<c:forEach var="error" items="${user.errors['password'] }">
						<small>${lang.out(error)}</small><br />
					</c:forEach>
				</div>
			</c:if>
		</div>
		<div class="form-group">
			<label for="password_confirm"><span class="" aria-hidden="true"></span> ${lang.out('password_comfirm') }:</label>
			<div class="input-group input-group-lg">
				<span class="input-group-addon">
					<span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
				</span>
				<input class="form-control" type="password" id="password_confirm" name="password_confirm" placeholder="${lang.out('password_comfirm_placeholder') }">
			</div>
		</div>
		
		<button type="submit" name="submit" value="signup" class="btn btn-primary btn-block">${lang.out('signup_button') }</button>
		
		<c:if test="${user.errors['user'] != null}">
			<div class="text-danger text-center">
				<c:forEach var="error" items="${user.errors['user'] }">
					<small>${lang.out(error)}</small><br />
				</c:forEach>
			</div>
		</c:if>
	</form>
</c:set>