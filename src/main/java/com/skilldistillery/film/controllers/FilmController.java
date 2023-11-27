/*
Synopsis:
Author:
Version: 
 */
package com.skilldistillery.film.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.skilldistillery.film.data.FilmDAO;
import com.skilldistillery.film.entities.Film;

@Controller
public class FilmController {

	public void setFilmDAO(FilmDAO filmDAO) {
		this.filmDAO = filmDAO;
	}

	@Autowired
	private FilmDAO filmDAO;

	@RequestMapping(path = {"home.do", "/"})
	public String goToHome() {
		return "WEB-INF/home.jsp";
	}
	
	@RequestMapping(path = "search.do", params = {"search", "searchType"}, method = RequestMethod.GET)
	public ModelAndView getFilmList(@RequestParam("search") String search, @RequestParam("searchType") String searchType) {
		ModelAndView mv = new ModelAndView();
		if (searchType.equalsIgnoreCase("film_id")) {
			int filmID = Integer.parseInt(search);
			Film film = filmDAO.findFilmById(filmID);
			mv.addObject("film", film);
			mv.setViewName("WEB-INF/Film.jsp");
		} else if (searchType.equalsIgnoreCase("keyword")) {
			List<Film> filmResults = filmDAO.findFilmByKeyWord(search);
			mv.addObject("search", search);
			mv.addObject("films", filmResults);
			mv.setViewName("WEB-INF/Result.jsp");
		} else if (searchType.equalsIgnoreCase("actor")) {
			List<Film> filmResults = filmDAO.findFilmByActor(search);
			mv.addObject("search", search);
			mv.addObject("films", filmResults);
			mv.setViewName("WEB-INF/Result.jsp");
		} else if (searchType.equals("category")) {
			List<Film> filmResults = filmDAO.findFilmByCategory(search);
			mv.addObject("search", search);
			mv.addObject("films", filmResults);
			mv.setViewName("WEB-INF/Result.jsp");
		} else if (searchType.equals("language")) {
			List<Film> filmResults = filmDAO.findFilmByLanguage(search);
			mv.addObject("search", search);
			mv.addObject("films", filmResults);
			mv.setViewName("WEB-INF/Result.jsp");
		} else {
			List<Film> filmResults = filmDAO.getAllFilms();
			mv.addObject("films", filmResults);
			mv.setViewName("WEB-INF/Result.jsp");
		}
		return mv;
	}
	

}
