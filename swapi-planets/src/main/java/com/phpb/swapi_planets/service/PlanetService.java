package com.phpb.swapi_planets.service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import com.phpb.swapi_planets.dao.PlanetsDao;
import com.phpb.swapi_planets.entity.Planet;

public class PlanetService {

	private PlanetsDao planetsDao = new PlanetsDao();

	public void insertPlanet(Planet _planet) throws Exception {

		Planet planeta = _planet;

		if (planetsDao.getPlanetByName(_planet.getName()).size() > 0) {
			throw new Exception("Planet already added");
		}

		if (planetsDao.getPlanetByUuid(_planet.getUuid()).size() > 0) {
			throw new Exception("Planet already added");
		}

		if (_planet.getMovieApparitions().size() < 1) {
			ArrayDeque<String> noMovies = new ArrayDeque<String>();
			noMovies.add("");
			planeta.setMovieApparitions(noMovies);
		}

		planetsDao.insertPlanet(_planet);
	}

	public void removePlanet(String uuidString) {
		try {
			UUID uuid = UUID.fromString(uuidString);
			if (planetsDao.getPlanetByUuid(uuid).size() > 0) {
				planetsDao.removePlanet(uuid);
			} else {
				throw new Exception("UUID doesn't exist...");
			}
		} catch (Exception err) {
			System.out.println(err);
		}
	}

	public Collection getAllPlanets() {
		Collection result = null;
		try {
			result = planetsDao.getAllPlanets();
			if (result.size() < 1) {
				result = new ArrayList<String>() {
					{
						add("No planets added yet...");
					}
				};
			}

		} catch (Exception err) {
			System.out.println(err);
		}
		return new ArrayList(result);
	}

	public Collection getPlanetByUuid(String uuidString) {
		Collection result = null;
		UUID uuid = UUID.fromString(uuidString);
		try {
			result = planetsDao.getPlanetByUuid(uuid);

			if (result.size() < 1) {
				result = new ArrayList<String>() {
					{
						add("No results found...");
					}
				};
			}
		} catch (Exception err) {
			System.out.println(err);
		}
		return new ArrayList(result);
	}

	public Collection getPlanetByName(String planetName) {
		Collection result = null;
		try {
			result = planetsDao.getPlanetByName(planetName);
			if (result.size() < 1) {
				result = new ArrayList<String>() {
					{
						add("No results found...");
					}
				};
			}
		} catch (Exception err) {
			System.out.println(err);
		}
		return new ArrayList(result);
	}
}
