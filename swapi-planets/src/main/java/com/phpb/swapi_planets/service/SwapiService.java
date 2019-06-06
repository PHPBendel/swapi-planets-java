package com.phpb.swapi_planets.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayDeque;

import org.json.JSONArray;
import org.json.JSONObject;

public class SwapiService {

	private static String getDataFromSWAPI(String Url) throws Exception {
		URL url = new URL(Url);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		String output = br.readLine();
		conn.disconnect();
		return output;

	}

	private static String getPlanetInfo(String planetName) {
		String url = "https://swapi.co/api/planets/?search=" + planetName;
		String result = null;
		try {
			result = getDataFromSWAPI(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static String getMovieNameFromAPI(String movieURL) {
		String movieName = null;
		try {
			String movieData = getDataFromSWAPI(movieURL);

			JSONObject obj = new JSONObject(movieData);
			movieName = obj.getString("title");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return movieName;
	}

	public static ArrayDeque<String> getMoviesFromPlanetName(String planetName) {
		String umJson = getPlanetInfo(planetName);
		JSONObject obj = new JSONObject(umJson);
		JSONArray arr = obj.getJSONArray("results");
		ArrayDeque<String> movies = new ArrayDeque<String>();

		if (arr.length() > 0) {
			JSONObject planet = arr.getJSONObject(0);
			JSONArray moviesArray = planet.getJSONArray("films");

			for (int i = 0; i < moviesArray.length(); i++) {
				String movieName = getMovieNameFromAPI((String) moviesArray.get(i));
				movies.add((String) movieName);
			}

		} else {
			System.out.println("No movies found");
		}

		return movies;
	}

}
