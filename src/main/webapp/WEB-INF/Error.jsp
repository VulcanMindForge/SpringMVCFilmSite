<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Uh Oh!</title>
</head>
<body>
	<c:if test="${result eq 'numFormat'}">
	<p>
		I'm sorry, the search you attempted <strong>${search}</strong> is not
		valid for this type.
	</p>
	</c:if>
	<c:if test="${result eq 'empty'}">
	<p>
		I'm sorry, the search you attempted <strong>${search}</strong> did not return any results.
	</p>
	</c:if>
	<br>
	<br>
	<h2>
		<a href="home.do">Return to Home</a>
	</h2>
</body>
</html>