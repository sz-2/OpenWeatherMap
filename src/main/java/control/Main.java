package control;

import java.time.*;
import java.io.IOException;
import java.util.List;



public class Main {
	public static void main(String[] args) throws IOException {
/*
		List<Location> canaryLocationList = new ArrayList<>();
		canaryLocationList.add(new Location("GranCanaria", 27.97184651000244, -15.58058872483241));
		canaryLocationList.add(new Location("Fuerteventura", 28.406230426084072, -14.01604553874936));
		canaryLocationList.add(new Location("Lanzarote", 29.023116403369755, -13.649771726664063));
		canaryLocationList.add(new Location("LaGraciosa", 29.249356182187068, -13.508791950321386));
		canaryLocationList.add(new Location("Tenerife", 28.262360601354693, -16.61447210904713));
		canaryLocationList.add(new Location("LaGomera", 28.113515248251513, -17.23810385746624));
		canaryLocationList.add(new Location("LaPalma", 28.729714491298278, -17.876720904669394));
		canaryLocationList.add(new Location("ElHierro", 27.739515282718738, -18.003309533121467));



		// Create Database for Weather
		String dbPath = "database/openWeatherStore.db";
		SqliteWeatherStore sqliteCanaryWeatherStore = new SqliteWeatherStore(dbPath);
		String apiKey = "b169eec36555fece973ebe8060054eb9";
		String url = "https://api.openweathermap.org/data/2.5/forecast";
		OpenWeatherMapSupplier canaryMapSupplier = new OpenWeatherMapSupplier(apiKey, url);

		List<Instant> instantsList =instantList.getInstantList(5, 12);
		WeatherController openWeatherMapController = new WeatherController(canaryLocationList, sqliteCanaryWeatherStore,  canaryMapSupplier);

		// La siguiente linea esta comentada para que no se cree otro nuevo database
		openWeatherMapController.createWeatherDatabase();
		openWeatherMapController.insertWeatherIntoDatabase(instantsList);


 */

		List<Instant> instantsList =instantList.getInstantList(5, 12);
		for (Instant mytime : instantsList){
			System.out.println(mytime.toString());
		}

	}

}