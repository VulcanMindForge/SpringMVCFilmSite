/*
Synopsis:
Author:
Version: 
 */
package com.skilldistillery.film.data;

import java.util.List;

import com.skilldistillery.film.entities.Film;

public interface FilmDAO {
	Film findByID(int filmID);
	List<Film> searchByKeyword(String keyword);

}
