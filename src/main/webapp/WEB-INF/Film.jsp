<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Film Details</title>
</head>
<body>
<h1>${film.title}</h1>
<ul>
<li>ID: ${film.filmId}</li>
<li>Film Rating: ${film.rating}</li>
<li>Title: ${film.title}</li>
<li>ReleaseYear: ${film.releaseYear}</li>
<li>Description: ${film.desc}</li>
<li><a href="search.do?search=${film.category}&searchType=category">Category: ${film.category}</a></li>
<li>Language: ${film.language}</li>
<li>Film Length: ${film.length}</li>
<li>Rental Rate: ${film.rate}</li>
<li>Rental Duration: ${film.rentDur}</li>
<li>Replacement Cost: ${film.repCost}</li>
<h2>Actors</h2>
<c:forEach var="actor" items="${film.actors}">
<li><a href="search.do?search=${actor.actorId}&searchType=actor_id">${actor.firstName} ${actor.lastName}</a></li>
</c:forEach>
</ul>
</body>
</html>