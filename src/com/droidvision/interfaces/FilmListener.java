package com.droidvision.interfaces;

import com.droidvision.models.Film;

/**
 * Interface to identify that the film is enable and ready to play.
 * Taking the Film object as a parameter is possible to obtain all the info necessary to display the movie trailer.
 *   
 * @author Nelson Sachse
 * @version 1.0
 *
 */
public interface FilmListener {
	public void filmIsInserted(Film film);
}
