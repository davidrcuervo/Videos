<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set scope="request" var="pwdConfirm_form">
	<form method="post">
	
		<div class=form-group>
			<label for="email">${lang.out('email') }</label>
			<input type="email" name="username" class="form-control" id="email" value="${email}" readonly/>
			<c:if test="${user.errors['username'] != null}">
				<div class="text-danger text-center">
					<c:forEach var="error" items="${user.errors['username'] }">
						<small>${lang.out(error)}</small><br />
					</c:forEach>
				</div>
			</c:if>
		</div>
		
		<div class=form-group>
			<label for="password">${lang.out('password') }:</label>
			<input type="password" name="password" class="form-control" id="password" placeholder="${lang.out('password_comfirm_placeholder') }" />
			<c:if test="${user.errors['password'] != null}">
				<div class="text-danger text-center">
					<c:forEach var="error" items="${user.errors['password'] }">
						<small>${lang.out(error)}</small><br />
					</c:forEach>
				</div>
			</c:if>
		</div>
		
		<button type="submit" name="submit" value="pwdConfirm" class="btn btn-primary btn-block">${lang.out('pwdConfirm_button') }</button>
		
		<c:if test="${user.errors['user'] != null}">
			<div class="text-danger text-center">
				<c:forEach var="error" items="${user.errors['user'] }">
					<small>${lang.out(error)}</small><br />
				</c:forEach>
			</div>
		</c:if>
	</form>
</c:set>