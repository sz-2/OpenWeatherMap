package control;

import model.Location;
import model.Weather;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.List;

public class WeatherController {
	private List<Location> locations;
	private WeatherStore weatherStore;
	private WeatherSupplier weatherSupplier;

	public WeatherController(List<Location> locations, WeatherStore weatherstore, WeatherSupplier weathersupplier) {
		this.locations = locations;
		this.weatherStore = weatherstore;
		this.weatherSupplier = weathersupplier;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public void setWeatherStore(WeatherStore weatherStore) {
		this.weatherStore = weatherStore;
	}

	public void setWeatherSupplier(WeatherSupplier weatherSupplier) {
		this.weatherSupplier = weatherSupplier;
	}

	public void execute(){
		List<Instant> instants = InstantList.getInstants(5, 12);
		for (Location location : this.locations)
		this.weatherStore.saveWeathers(this.weatherSupplier.getWeathers(location, instants));
	}
}
