package control;

import model.Location;
import model.Weather;

import java.time.Instant;
import java.util.List;

public interface WeatherSupplier {

	public String getWeatherFromAPI(Location location);

	public List<Weather> getWeatherList(Location location, List<Instant>instatsList);

}
