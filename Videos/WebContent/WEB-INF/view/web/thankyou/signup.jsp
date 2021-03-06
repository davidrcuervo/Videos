<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="content" scope="request">
	<h2 class="text-center">${lang.out('thankyou') }</h2>
	<p>${lang.out('thankyou_signup_success') }</p>
	<p>${lang.out('thankyou_signup_password') }</p>
	<a class="btn btn-default btn-block" href="${page.url}/home">${lang.out('home_link') }</a>
</c:set>

<jsp:include page="/WEB-INF/view/templates/thankyou.jsp"></jsp:include>