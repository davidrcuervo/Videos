<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set scope="request" var="content">
	<h3>${lang.out('home_wlcm') }</h3>
	<div>
		username: ${user.username }<br />
	</div>
</c:set>
<jsp:include page="/WEB-INF/view/templates/web.jsp"></jsp:include>