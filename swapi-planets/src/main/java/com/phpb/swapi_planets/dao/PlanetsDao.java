package com.phpb.swapi_planets.dao;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import com.phpb.swapi_planets.datomic.*;
import com.phpb.swapi_planets.entity.*;

import datomic.Connection;
import datomic.Database;
import datomic.Peer;

import static datomic.Util.map;
import static datomic.Util.list;

public class PlanetsDao {

	Connection conn;
	Db datomic;

	public PlanetsDao() {
		datomic = new Db();
		this.conn = datomic.connectDatomic();
	}

	public void insertPlanet(Planet p) throws InterruptedException, ExecutionException {
		
		
								
		List tx = list(map(":planet/uuid", p.getUuid(),
				":planet/name", p.getName(),
				":planet/climate", p.getClimate(),
				":planet/terrain", p.getTerrain(),
				":planet/apparitions", p.getMoviesAsList()));
				
		this.conn.transact(tx).get();
	}
	
	public Collection getAllPlanets() {
		Database db = datomic.getDb(this.conn);
		return Peer.query("[:find ?uuid ?name (distinct ?climate) (distinct ?terrain) (distinct ?movies)\n" + 
				":where [?e :planet/name ?name]\n"
				+ "[?e :planet/uuid ?uuid]\n"
				+ "[?e :planet/terrain ?terrain]\n"
				+ "[?e :planet/climate ?climate]\n"
				+ "[?e :planet/apparitions ?movies]]", db);
	}	
	
	public Collection getPlanetByUuid(UUID uuid) {
		Database db = datomic.getDb(this.conn);
		return Peer.query("[:find ?uuid ?name (distinct ?climate) (distinct ?terrain) (distinct ?movies)\n"
				+ ":in $ ?uuid\n" 
				+ ":where [?e :planet/name ?name]\n"
				+ "[?e :planet/uuid ?uuid]\n"
				+ "[?e :planet/terrain ?terrain]\n"
				+ "[?e :planet/climate ?climate]\n"
				+ "[?e :planet/apparitions ?movies]]", db, uuid);
	}
	
	public Collection getPlanetByName(String planetName) {
		Database db = datomic.getDb(this.conn);
		return Peer.query("[:find ?uuid ?name (distinct ?climate) (distinct ?terrain) (distinct ?movies)\n"
				+ ":in $ ?name\n" 
				+ ":where [?e :planet/name ?name]\n"
				+ "[?e :planet/uuid ?uuid]\n"
				+ "[?e :planet/terrain ?terrain]\n"
				+ "[?e :planet/climate ?climate]\n"
				+ "[?e :planet/apparitions ?movies]]", db, planetName);
	}
	
	public void removePlanet(UUID uuid) throws InterruptedException, ExecutionException {
		List tx = list(list(":db/retractEntity", list(":planet/uuid", uuid)));
		
		this.conn.transact(tx).get();
	}
}
