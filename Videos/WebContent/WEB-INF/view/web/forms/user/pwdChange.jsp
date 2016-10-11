<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set scope="request" var="pwdChange_form">
	<form method="post">
		
		<div class=form-group>
			<label for="old_password">${lang.out('old_password') }</label>
			<input type="password" name="old_password" class="form-control" id="old_password" placeholder="${lang.out('old_password_placeholder') }"/>
			<c:if test="${user.errors['old_password'] != null}">
				<div class="text-danger text-center">
					<c:forEach var="error" items="${user.errors['old_password'] }">
						<small>${lang.out(error)}</small><br />
					</c:forEach>
				</div>
			</c:if>
		</div>
		
		<div class=form-group>
			<label for="new_password">${lang.out('new_password') }</label>
			<input type="password" name="new_password" class="form-control" id="new_password" placeholder="${lang.out('new_password_placeholder') }"/>
		</div>
		<c:if test="${user.errors['password'] != null}">
			<div class="text-danger text-center">
				<c:forEach var="error" items="${user.errors['password'] }">
					<small>${lang.out(error)}</small><br />
				</c:forEach>
			</div>
		</c:if>
		
		<div class=form-group>
			<label for="new_passw_confirm">${lang.out('new_passw_confirm') }</label>
			<input type="password" name="new_passw_confirm" class="form-control" id="new_passw_confirm" placeholder="${lang.out('new_passw_confirm_placeholder') }"/>
		</div>
		
		<button type="submit" name="submit" value="pwdChange" class="btn btn-primary btn-block">${lang.out('pwdConfirm_button') }</button>
		
		<c:if test="${user.errors['user'] != null}">
			<div class="text-danger text-center">
				<c:forEach var="error" items="${user.errors['user'] }">
					<small>${lang.out(error)}</small><br />
				</c:forEach>
			</div>
		</c:if>
		
	</form>
</c:set>