<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<html lang=#{lang.lang }>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>${title == null ? 'Attendance' : title}</title>
		
		<%-- LOADING STYLES --%>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	
		<c:forEach var="style" items="${page.styles}">
			<link rel="stylesheet" href="${page.url}/assets/${style}">
		</c:forEach>
	
		<%-- LOADING SCRIPTS --%>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
		<script src='//cdn.tinymce.com/4/tinymce.min.js'></script>
	
		<c:forEach var="script" items="${page.scripts}">
			<script src="${page.url}/assets/${script}"></script>
		</c:forEach>	
	</head>
	<body>
		<div class="container-fluid max-width-1280">${content}</div>
	</body>
</html>