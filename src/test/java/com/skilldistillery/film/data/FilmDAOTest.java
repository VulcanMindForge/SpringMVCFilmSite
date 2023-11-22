/*
Synopsis:
Author:
Version: 
 */
package com.skilldistillery.film.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.skilldistillery.film.entities.Film;

class FilmDAOTest {
	FilmDAOTestMemoryImpl testDAO;

	@BeforeEach
	void setUp() throws Exception {
		testDAO = new FilmDAOTestMemoryImpl();
	}

	@AfterEach
	void tearDown() throws Exception {
		testDAO = null;
	}

	 @Test
	 void test_findById_returns_film() {
	 	Film film = testDAO.findByID(1);
	 	assertNotNull(film);
	 	assertEquals("ACADEMY DINOSAUR", film.getTitle());
	 }
	 
	 @Test
	 void test_findById_returns_null_for_invalid_id() {
	 	Film film = testDAO.findByID(1234567);
	 	assertNull(film);
	 }

}
