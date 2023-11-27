/*
Synopsis:
Author:
Version: 
 */
package com.skilldistillery.film.data;

import java.util.List;

import com.skilldistillery.film.entities.Actor;
import com.skilldistillery.film.entities.Film;

public interface FilmDAO {
	public Film findFilmById(int filmId);
	
	public List<Film> getAllFilms();

	public List<Film> findFilmByKeyWord(String keyWord);

	public List<Film> findFilmByActorId(int actorId);
	
	public List<Film> findFilmByActor(String actor);
	
	public List<Film> findFilmByCategory(String category);
	
	public List<Film> findFilmByLanguage(String language);

	public List<Actor> findActorsByFilmId(int filmId);

	public String findLanguageByID(int languageID);
}
