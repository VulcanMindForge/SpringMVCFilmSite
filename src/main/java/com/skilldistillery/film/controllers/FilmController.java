/*
Synopsis:
Author:
Version: 
 */
package com.skilldistillery.film.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.skilldistillery.film.data.FilmDAO;
import com.skilldistillery.film.entities.Actor;
import com.skilldistillery.film.entities.Film;

@Controller
public class FilmController {

	public void setFilmDAO(FilmDAO filmDAO) {
		this.filmDAO = filmDAO;
	}

	@Autowired
	private FilmDAO filmDAO;

	@RequestMapping(path = { "home.do", "/" })
	public String goToHome() {
		return "WEB-INF/home.jsp";
	}

	@RequestMapping(path = "search.do", params = { "search", "searchType" }, method = RequestMethod.GET)
	public ModelAndView getFilmList(@RequestParam("search") String search,
			@RequestParam("searchType") String searchType) {
		ModelAndView mv = new ModelAndView();
			if (searchType.equalsIgnoreCase("film_id")) {
				try {
					int filmID = Integer.parseInt(search);
					Film film = filmDAO.findFilmById(filmID);
					mv.addObject("film", film);
					mv.setViewName("WEB-INF/Film.jsp");
				} catch (NumberFormatException e) {
					mv.addObject("search", search);
					mv.addObject("result", "numFormat");
					mv.setViewName("WEB-INF/Error.jsp");
					return mv;
				}
			} else if (searchType.equalsIgnoreCase("keyword")) {
				mv = setMV(filmDAO.findFilmByKeyWord(search), search);
			} else if (searchType.equalsIgnoreCase("actor")) {
				mv = setMV(filmDAO.findFilmByActor(search), search);
			} else if (searchType.equals("category")) {
				mv = setMV(filmDAO.findFilmByCategory(search), search);
			} else if (searchType.equals("language")) {
				mv = setMV(filmDAO.findFilmByLanguage(search), search);
			} else if (searchType.equalsIgnoreCase("actor_id")) {
				int actorID = Integer.parseInt(search);
				Actor actor = filmDAO.findActorById(actorID);
				mv.addObject("actor", actor);
				mv.setViewName("WEB-INF/Actor.jsp");
			} else {
				List<Film> filmResults = filmDAO.getAllFilms();
				mv.addObject("films", filmResults);
				mv.setViewName("WEB-INF/Result.jsp");
			}
			return mv;
	}

	private ModelAndView setMV(List<Film> filmResults, String search) {
		ModelAndView mv = new ModelAndView();
		if (filmResults.size() > 0) {
			mv.addObject("search", search);
			mv.addObject("films", filmResults);
			mv.setViewName("WEB-INF/Result.jsp");
		} else if (filmResults.size() == 0) {
			mv.addObject("search", search);
			mv.addObject("result", "empty");
			mv.setViewName("WEB-INF/Error.jsp");
		}
		return mv;
	}

}
