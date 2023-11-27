<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Actor Details</title>
</head>
<body>
<h1>${actor.firstName} ${actor.lastName}</h1>
<ul>
<li>First Name: ${actor.firstName}</li>
<li>Last Name: ${actor.lastName}</li>

<h2>Films</h2>
<c:forEach var="film" items="${actor.films}">
<li><a href="search.do?search=${film.filmId}&searchType=film_id">
${film.filmId} ${film.title}</a></li>
</c:forEach>
</ul>
</body>
</html>