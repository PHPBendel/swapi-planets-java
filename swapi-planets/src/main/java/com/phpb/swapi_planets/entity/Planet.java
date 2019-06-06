package com.phpb.swapi_planets.entity;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import datomic.Peer;


import com.phpb.swapi_planets.service.SwapiService;

public class Planet {

	private UUID uuid;
	private String name;
	private List<String> climate;
	private List<String> terrain;
	private ArrayDeque<String> movieApparitions;
		
	public Planet(String name, List<String> climate, List<String> terrain) {
		this.uuid = Peer.squuid();
		this.name = name;
		this.climate = climate;
		this.terrain = terrain;
		this.movieApparitions = SwapiService.getMoviesFromPlanetName(this.name);
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayDeque<String> getMovieApparitions() {
		return movieApparitions;
	}

	public void setMovieApparitions(ArrayDeque<String> movieApparitions) {
		this.movieApparitions = movieApparitions;
	}

	public List<String> getClimate() {
		return climate;
	}

	public void setClimate(List<String> climate) {
		this.climate = climate;
	}

	public List<String> getTerrain() {
		return terrain;
	}

	public void setTerrain(List<String> terrain) {
		this.terrain = terrain;
	}
	
	public List<Object> getMoviesAsList() {
		return Arrays.asList(this.movieApparitions.toArray());
	}
}
