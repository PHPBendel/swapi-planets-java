# SWAPI planets

This project records planets in a on-memory datomic database, before recording the planet, it searches for it on the [SWAPI] (https://swapi.co/) API, if it finds the planet there, it will record the Star wars movies the planet has appeared in.

## To add a planet you have to make a POST request to `localhost:8080/planets` with the following keys

| Key | Description |
| -------------|:-------------:|
| name         | Name of the planet |
| climate  | Climate of the planet, can have multiple values |
| terrain  | Terrain of the planet, can have multiple values |
