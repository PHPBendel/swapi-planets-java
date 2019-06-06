package com.phpb.swapi_planets.datomic;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.net.URL;
import java.util.List;

import datomic.*;
import static datomic.Util.readAll;

public class Db {
	private static final String uri = "datomic:mem://swapi-planets";

	public Db() {
		Peer.createDatabase(uri);
	}

	public Connection connectDatomic() {
		Connection conn = Peer.connect(uri);
		return conn;
	}

	public Database getDb(Connection conn) {
		return conn.db();
	}

	public void ensureSchema(String path) {
		try {
			File readFile = getFileFromResources(path);
			List schema = fileToList(readFile);

			Connection conn = connectDatomic();			
			conn.transact(schema).get();
		} catch (Exception err) {
			System.out.println(err);
		}

	}

	private File getFileFromResources(String fileName) {

		ClassLoader classLoader = getClass().getClassLoader();

		URL resource = classLoader.getResource(fileName);

		if (resource == null) {
			throw new IllegalArgumentException("file is not found!");
		} else {
			return new File(resource.getFile());
		}
	}

	private static List fileToList(File file) throws IOException {

		if (file == null)
			return null;

		try {
			FileReader reader = new FileReader(file);
			return readAll(reader);

		} catch (IOException err) {
			throw new IOException(err);
		}
	}

}
