<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

${page.addTab('nav_menu', 'settings') }
${page.addTab('settings', 'password') }

<jsp:include page="/WEB-INF/view/web/forms/user/pwdChange.jsp"></jsp:include>

<c:set var="content" scope="request">
	<h1 class="text-center">${lang.out('passwd_change_title') }</h1>
	<h3 class="text-center"><small>${lang.out('passwd_change_description')}</small></h3>
	
	<div>${pwdChange_form}</div>
</c:set>

<jsp:include page="/WEB-INF/view/templates/web.jsp"></jsp:include>