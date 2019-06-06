package com.phpb.swapi_planets.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.phpb.swapi_planets.service.PlanetService;
import com.phpb.swapi_planets.entity.Planet;

@RestController
@RequestMapping("/planets")
public class PlanetController {

	private PlanetService planetService = new PlanetService();

	@RequestMapping(method = RequestMethod.POST)
	public String insertPlanetsHandler(@RequestParam(required = true) String name,
			@RequestParam(required = true) List<String> climate, @RequestParam(required = true) List<String> terrain) {
		try {
			
			Planet planet = new Planet(name, climate, terrain);
			planetService.insertPlanet(planet);
			return "Planet succesfully added!";
		} catch (Exception err) {
			System.out.println(err);
			return err.getMessage();
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public String deletePlanetHandler(@RequestParam(required = true) String uuid) {
		try {					
			planetService.removePlanet(uuid);
			return "Planet succesfully removed!";
		} catch (Exception err) {
			System.out.println(err);
			return err.getMessage();
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Collection getPlanetsHandler(@RequestParam(value = "uuid", required = false) String uuid,
			@RequestParam(value = "name", required = false) String planetName) {

		if (uuid != null) {
			return planetService.getPlanetByUuid(uuid);
		} else if (planetName != null) {
			return planetService.getPlanetByName(planetName);
		} else {
			return planetService.getAllPlanets();
		}
	}

}
