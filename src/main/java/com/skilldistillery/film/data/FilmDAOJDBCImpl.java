/*
Synopsis:
Author:
Version: 
 */
package com.skilldistillery.film.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.skilldistillery.film.entities.Actor;
import com.skilldistillery.film.entities.Film;

public class FilmDAOJDBCImpl implements FilmDAO {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";
	private static final String dBUserName = "student";
	private static final String dBPassword = "student";

	public FilmDAOJDBCImpl() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println(e);
		}
	}

	@Override
	public List<Film> getAllFilms() {
		List<Film> films = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, dBUserName, dBPassword);
			String sql = "SELECT * FROM film WHERE id > 0";

			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet filmResult = stmt.executeQuery();
			Film film = null;
			while (filmResult.next()) {
				film = addFilm(filmResult);
				films.add(film);
			}
			tearDown(conn, stmt, filmResult);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}

	@Override
	public Film findFilmById(int filmId) {
		Film film = null;
		String sqlTxt = "SELECT * FROM film WHERE id = ?";

		try {
			Connection conn = DriverManager.getConnection(URL, dBUserName, dBPassword);
			PreparedStatement stmt = conn.prepareStatement(sqlTxt);
			stmt.setInt(1, filmId);
			ResultSet filmResult = stmt.executeQuery();

			while (filmResult.next()) {
				film = addFilm(filmResult);
			}

			tearDown(conn, stmt, filmResult);
			return film;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return film;
		}
	}

	@Override
	public Actor findActorById(int ActorId) {
		Actor actor = null;
		String sqlTxt = "SELECT * FROM actor WHERE id = ?";
		
		try {
			Connection conn = DriverManager.getConnection(URL, dBUserName, dBPassword);
			PreparedStatement stmt = conn.prepareStatement(sqlTxt);
			stmt.setInt(1, ActorId);
			ResultSet actorResult = stmt.executeQuery();
			
			if (actorResult.next()) {
				int id = actorResult.getInt("id");
				String firstName = actorResult.getString("first_name");
				String lastName = actorResult.getString("last_name");
				List<Film> films = findFilmByActorId(id);
				actor = new Actor(firstName, lastName);
				actor.setFilms(films);
				actor.setActorId(id);
			}
			
			tearDown(conn, stmt, actorResult);
			return actor;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return actor;
		}
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, dBUserName, dBPassword);
			String sql = "SELECT actor.* FROM actor JOIN film_actor ON actor.id = film_actor.actor_id JOIN film ON film_actor.film_id = film.id WHERE film.id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int actorId = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");

				Actor actor = new Actor(firstName, lastName);
				actor.setActorId(actorId);
				actors.add(actor);
			}
			tearDown(conn, stmt, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actors;
	}

	@Override
	public List<Film> findFilmByActorId(int actorId) {
		List<Film> films = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, dBUserName, dBPassword);
			String sql = "SELECT film.* FROM film JOIN film_actor ON film.id = film_actor.film_id JOIN actor ON film_actor.actor_id = actor.id WHERE actor.id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet filmResult = stmt.executeQuery();
			Film film = null;

			while (filmResult.next()) {
				film = addFilm(filmResult);
				films.add(film);
			}
			tearDown(conn, stmt, filmResult);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}

	@Override
	public List<Film> findFilmByActor(String actor) {
		List<Film> films = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, dBUserName, dBPassword);
			String sql = "SELECT f.* FROM film f JOIN film_actor fa ON f.id = fa.film_id JOIN actor a ON fa.actor_id = a.id WHERE a.first_name LIKE ? OR a.last_name LIKE ?";
			actor = "%" + actor + "%";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, actor);
			stmt.setString(2, actor);
			ResultSet filmResult = stmt.executeQuery();
			Film film = null;

			while (filmResult.next()) {
				film = addFilm(filmResult);
				films.add(film);
			}
			tearDown(conn, stmt, filmResult);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}

	@Override
	public List<Film> findFilmByKeyWord(String keyWord) {
		List<Film> films = new ArrayList<>();
		keyWord = "%" + keyWord + "%";
		try {
			Connection conn = DriverManager.getConnection(URL, dBUserName, dBPassword);
			String sqlTxt = "SELECT * FROM film WHERE title LIKE ? OR description LIKE ?";
			PreparedStatement stmt = conn.prepareStatement(sqlTxt);
			stmt.setString(1, keyWord);
			stmt.setString(2, keyWord);
			ResultSet filmResult = stmt.executeQuery();
			Film film = null;

			while (filmResult.next()) {
				film = addFilm(filmResult);
				films.add(film);
			}

			tearDown(conn, stmt, filmResult);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return films;
	}

	@Override
	public List<Film> findFilmByCategory(String category) {
		List<Film> films = new ArrayList<>();
		category = "%" + category + "%";
		try {
			Connection conn = DriverManager.getConnection(URL, dBUserName, dBPassword);
			String sqlTxt = "SELECT f.* FROM film f JOIN film_category fc ON f.id = fc.film_id JOIN category c ON fc.category_id = c.id WHERE c.name LIKE ?";
			PreparedStatement stmt = conn.prepareStatement(sqlTxt);
			stmt.setString(1, category);
			ResultSet filmResult = stmt.executeQuery();
			Film film = null;

			while (filmResult.next()) {
				film = addFilm(filmResult);
				films.add(film);
			}

			tearDown(conn, stmt, filmResult);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return films;
	}

	@Override
	public List<Film> findFilmByLanguage(String language) {
		List<Film> films = new ArrayList<>();
		language = "%" + language + "%";
		try {
			Connection conn = DriverManager.getConnection(URL, dBUserName, dBPassword);
			String sqlTxt = "SELECT f.* FROM film f JOIN language l ON f.language_id = l.id WHERE l.name LIKE ?";
			PreparedStatement stmt = conn.prepareStatement(sqlTxt);
			stmt.setString(1, language);
			ResultSet filmResult = stmt.executeQuery();
			Film film = null;

			while (filmResult.next()) {
				film = addFilm(filmResult);
				films.add(film);
			}

			tearDown(conn, stmt, filmResult);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return films;
	}

	@Override
	public String findLanguageByID(int languageID) {
		String languageName = null;
		String sqlTxt = "SELECT * FROM language WHERE id = ?";

		try {
			Connection conn = DriverManager.getConnection(URL, dBUserName, dBPassword);
			PreparedStatement stmt = conn.prepareStatement(sqlTxt);
			stmt.setInt(1, languageID);
			ResultSet languageResult = stmt.executeQuery();

			while (languageResult.next()) {
				languageName = languageResult.getString("name");
			}
			tearDown(conn, stmt, languageResult);
			return languageName;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return languageName;
		}
	}

	@Override
	public String findCategoryByFilmID(int filmID) {
		String categoryName = null;
		String sqlTxt = "SELECT c.name FROM category c JOIN film_category fc ON c.id = fc.category_id JOIN film f ON fc.film_id = f.id WHERE f.id = ?";
		
		try {
			Connection conn = DriverManager.getConnection(URL, dBUserName, dBPassword);
			PreparedStatement stmt = conn.prepareStatement(sqlTxt);
			stmt.setInt(1, filmID);
			ResultSet categoryResult = stmt.executeQuery();
			
			while (categoryResult.next()) {
				categoryName = categoryResult.getString("name");
			}
			tearDown(conn, stmt, categoryResult);
			return categoryName;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return categoryName;
		}
	}

	public Set<String> stringToSet(String stringToSplit) {
		Set<String> postSplitString = new HashSet<>();
		String[] splitString = stringToSplit.split(",");
		for (int i = 0; i < splitString.length; i++) {
			postSplitString.add(splitString[i]);
		}
		return postSplitString;
	}

	private Film addFilm(ResultSet result) throws SQLException {
		Film film = new Film();
		film.setFilmId(result.getInt("id"));
		film.setTitle(result.getString("title"));
		film.setDesc(result.getString("description"));
		film.setReleaseYear(result.getShort("release_year"));
		film.setLangId(result.getInt("language_id"));
		film.setLanguage(findLanguageByID(result.getInt("language_id")));
		film.setRentDur(result.getInt("rental_duration"));
		film.setRate(result.getDouble("rental_rate"));
		film.setLength(result.getInt("length"));
		film.setRepCost(result.getDouble("replacement_cost"));
		film.setRating(result.getString("rating"));
		film.setFeatures(stringToSet(result.getString("special_features")));
		film.setActors(findActorsByFilmId(result.getInt("id")));
		film.setCategory(findCategoryByFilmID(result.getInt("id")));
		return film;
	}

	private void tearDown(Connection conn, PreparedStatement stmt, ResultSet result) {
		try {
			if (result != null)
				result.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			System.err.println("Error closing resources" + e);
		}
	}
}
