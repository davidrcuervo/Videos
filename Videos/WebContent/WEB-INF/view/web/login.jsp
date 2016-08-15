<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

${page.addStyle('styles/login.css') }

<jsp:include page="/WEB-INF/view/web/forms/login.jsp"></jsp:include>
<jsp:include page="/WEB-INF/view/web/forms/password.jsp"></jsp:include>
<jsp:include page="/WEB-INF/view/web/forms/signup.jsp"></jsp:include>

<c:set var="content" scope="request">
	<h1 class="text-center">${lang.out('login_wlcm') }</h1>
	<h3 class="text-center"><small>${lang.out('login_wlcm_message')}</small></h3>
	
	<div>
		<!-- Nav tabs -->
		<ul class="nav nav-tabs" role="tablist">
			<li role="presentation" class="active"><a href="#signup" aria-controls="signup" role="tab" data-toggle="tab">${lang.out('signup_button') }</a></li>
			<li role="presentation"><a href="#login" aria-controls="login" role="tab" data-toggle="tab">${lang.out('login') }</a></li>
			<li role="presentation"><a href="#password" aria-controls="password" role="tab" data-toggle="tab">${lang.out('password')}</a></li>
		</ul>
		
		<!-- tab panes -->
		<div class="tab-content">
			<div role="tabpanel" class="tab-pane" id="password"><div class="form_frame">${password_form}</div></div>
			<div role="tabpanel" class="tab-pane active" id="signup"><div class="from_frame">${signup_form}</div></div>
			<div role="tabpanel" class="tab-pane" id="login"><div class="form_frame">${login_form}</div></div>
		</div>
	</div>
</c:set>

<jsp:include page="/WEB-INF/view/templates/login.jsp"></jsp:include>