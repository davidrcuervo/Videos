<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/view/web/forms/user/pwdConfirm.jsp"></jsp:include>

<c:set var="content" scope="request">
	<h1 class="text-center">${lang.out('login_wlcm') }</h1>
	<h3 class="text-center"><small>${lang.out('pwdConfirm_form_titlte')}</small></h3>
	
	<div>${pwdConfirm_form}</div>
</c:set>

<jsp:include page="/WEB-INF/view/templates/login.jsp"></jsp:include>