<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang=${lang.lang }>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>${title == null ? 'Videos' : title}</title>
	
	<%-- LOADING STYLES --%>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
	
	<c:forEach var="style" items="${page.styles}">
		<link rel="stylesheet" href="${page.url}/assets/${style}">
	</c:forEach>
	
	<%-- LOADING SCRIPTS --%>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
	<script src='//cdn.tinymce.com/4/tinymce.min.js'></script>
	
	<c:forEach var="script" items="${page.scripts}">
		<script src="${page.url}/assets/${script}"></script>
	</c:forEach>
	
</head>
<body>
	${page.showTab('nav_menu')}
	<nav class="navbar navbar-default">
	 	<div class="container-fluid">
	    <!-- Brand and toggle get grouped for better mobile display -->
	    <div class="navbar-header">
	      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
	        <span class="sr-only">Toggle navigation</span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	      </button>
	      <a class="navbar-brand" href="${pageContext.request.contextPath}/home">${lang.out('app_name') }</a>
	    </div>
	
	    <!-- Collect the nav links, forms, and other content for toggling -->
	    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	      <ul class="nav navbar-nav">
	        <li class="${page.showTab('nav_menu') == 'home' ? 'active' : ''}"><a href="${page.url}/home">Home</a></li>
	        <li class="${page.showTab('nav_menu') == 'Tab1' ? 'active' : ''}"><a href="${page.url}/home">Tab1</a></li>
 	        <li class="${page.showTab('nav_menu') == 'Tab2' ? 'active' : ''}"><a href="${page.url}/home">Tab2</a></li>
	        <li class="dropdown">
	          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
	          <ul class="dropdown-menu">
	            <li><a href="#">Action</a></li>
	            <li><a href="#">Another action</a></li>
	            <li><a href="#">Something else here</a></li>
	            <li role="separator" class="divider"></li>
	            <li><a href="#">Separated link</a></li>
	            <li role="separator" class="divider"></li>
	            <li><a href="#">One more separated link</a></li>
	          </ul>
	        </li>
	      </ul>
	      <form class="navbar-form navbar-left" role="search">
	        <div class="form-group">
	          <input type="text" class="form-control" placeholder="Search">
	        </div>
	        <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"></span></button>
	      </form>
	      <ul class="nav navbar-nav navbar-right">
	        <li class="dropdown ${page.showTab('nav_menu') == 'settings' ? 'active' : ''}">
	          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-cog"></span></a>
	          <ul class="dropdown-menu">
	            <li><a href="${page.url}/user/edit"><span class="glyphicon glyphicon-user"></span>&nbsp ${lang.out('edit_profile')}</a></li>
	            <li class="${page.showTab('settings') == 'password' ? 'active' : ''}"><a href="${page.url}/user/password"><span class="glyphicon glyphicon-lock"></span>&nbsp ${lang.out('change_password') }</a></li>
	            <li><a href="${page.url}/logout"><span class="glyphicon glyphicon-log-out"></span>&nbsp ${lang.out('log_out') }</a></li>
	            <li role="separator" class="divider"></li>
	            <li><a href="${pageContext.request.contextPath}/email_server"><span class="glyphicon glyphicon-cog"></span>&nbsp ${lang.out('app_settings')}</a></li>
	            
	          </ul>
	        </li>
	      </ul>
	    </div><!-- /.navbar-collapse -->
	  </div><!-- /.container-fluid -->
	</nav>
	<div class="container">${content}</div>
</body>
</html>