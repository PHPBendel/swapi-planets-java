package com.phpb.swapi_planets;

import com.phpb.swapi_planets.datomic.Db;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
	public static void main(String[] args) throws Exception {
		
		Db datomic = new Db();
		datomic.ensureSchema("planets-schema.edn");		

		SpringApplication.run(App.class, args);

	}
}