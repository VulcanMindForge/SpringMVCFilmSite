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
import java.util.List;

import com.skilldistillery.film.entities.Film;

public class FilmDAOJDBCImpl implements FilmDAO {

	private String dBURL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";
	private String dBUsername = "student";
	private String dBPassword = "student";

	@Override
	public Film findByID(int filmID) {
		Film film = null;
		String sqlTxt = "SELECT * FROM film WHERE id = ?";

		try {
			Connection conn = DriverManager.getConnection(dBURL, dBUsername, dBPassword);
			PreparedStatement pstmt = conn.prepareStatement(sqlTxt);
			pstmt.setInt(1, filmID);
			ResultSet filmResult = pstmt.executeQuery();

			if (filmResult.next()) {
				film = new Film();
				film.setFilmId(filmID);
				film.setTitle(filmResult.getString("title"));
				film.setDesc(filmResult.getString("description"));
				film.setReleaseYear(filmResult.getShort("release_year"));
				film.setLangId(filmResult.getInt("language_id"));
				film.setRentDur(filmResult.getInt("rental_duration"));
				film.setRate(filmResult.getDouble("rental_rate"));
				film.setLength(filmResult.getInt("length"));
				film.setRepCost(filmResult.getDouble("replacement_cost"));
				film.setRating(filmResult.getString("rating"));
			}

			filmResult.close();
			pstmt.close();
			conn.close();
			return film;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return film;
		}
	}

	@Override
	public List<Film> searchByKeyword(String keyword) {
		return null;
	}

}
