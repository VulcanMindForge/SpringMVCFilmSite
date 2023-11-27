<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Jacob Movie Database</title>
</head>
<body>
	<h2>Welcome to our homebrew movie database.</h2>
	<p>Please excuse the mess as we are still under constructions.
	<p>
	<div>
		<form action="search.do" method="GET">
			<input type="text" name="search">
			<div>
				<button type="submit" name="searchType" value="film_id">Film ID</button>
				<button type="submit" name="searchType" value="keyword">Term</button>
				<button type="submit" name="searchType" value="actor">Actor</button>
				<button type="submit" name="searchType" value="category">Category</button>
				<button type="submit" name="searchType" value="language">Language</button>
				<button type="submit" name="searchType" value="allFilms">All Films</button>
			</div>
		</form>
	</div>
</body>
</html>