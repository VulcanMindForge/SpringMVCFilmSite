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

	<table>
		<thead>
			<tr>
				<td>Title</td>
				<td>Year</td>
				<td>Rating</td>
				<td>Category</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="film" items="${films}">
				<tr>
					<td><a href="search.do?search=${film.filmId}&searchType=film_id">${film.title}</a></td>
					<td>${film.releaseYear}</td>
					<td>${film.rating}</td>
					<td><a href="search.do?search=${film.category}&searchType=category">${film.category}</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>