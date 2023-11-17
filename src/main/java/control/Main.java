package control;

import java.io.IOException;
import java.util.List;
import model.Location;
import java.util.ArrayList;


public class Main {
	public static void main(String[] args) throws IOException {

		List<Location> canaryLocationList = new ArrayList<>();
		canaryLocationList.add(new Location("GranCanaria", 28.12310773930144, -15.437557899191749));
		canaryLocationList.add(new Location("Fuerteventura", 28.500888314467048, -13.862309602591422));
		canaryLocationList.add(new Location("Lanzarote", 28.966045016165427, -13.557561814671411));
		canaryLocationList.add(new Location("LaGraciosa", 29.232477538959174, -13.503744841641467));
		canaryLocationList.add(new Location("Tenerife", 28.463214791974984, -16.26854016599568));
		canaryLocationList.add(new Location("LaGomera", 28.09376424996476, -17.110197364498607));
		canaryLocationList.add(new Location("LaPalma", 28.683822052212975, -17.765305651911973));
		canaryLocationList.add(new Location("ElHierro", 27.796215355055317, -17.933581891999527));


		String dbPath = System.getenv("PATH_DATABASE");
		String apiKey = System.getenv("APIKEY");
		String url = System.getenv("URL");

		SqliteWeatherStore sqliteCanaryWeatherStore = new SqliteWeatherStore(dbPath, canaryLocationList);
		OpenWeatherMapSupplier canaryMapSupplier = new OpenWeatherMapSupplier(apiKey, url);

		WeatherController openWeatherMapController = new WeatherController(canaryLocationList, sqliteCanaryWeatherStore,  canaryMapSupplier);
		openWeatherMapController.executionTimer(1);

	}
}