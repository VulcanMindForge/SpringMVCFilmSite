<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Search Results</title>
</head>
<body>
	<h2>You searched for</h2>
	<c:out value="${search}" />
	
	<c:if test="${films.size() < 1 }">
		<p>We didn't find anything based on that search term.</p>
	</c:if>
	<c:forEach var="film" items="${films}">
		<ul>
			<li>${film.filmId}</li>
			<li>${film.title}</li>
			<li>${film.desc}</li>
			<li>${film.releaseYear}</li>
		</ul>
	</c:forEach>
</body>
</html>