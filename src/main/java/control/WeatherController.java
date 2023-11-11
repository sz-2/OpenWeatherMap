package control;

import model.Location;
import model.Weather;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.List;

public class WeatherController {
	private List<Location> locationList;
	private WeatherStore weatherstore;
	private WeatherSupplier weathersupplier;

	public WeatherController(List<Location> locationList, WeatherStore weatherstore, WeatherSupplier weathersupplier) {
		this.locationList = locationList;
		this.weatherstore = weatherstore;
		this.weathersupplier = weathersupplier;
	}

	public void setLocationList(List<Location> locationList) {
		this.locationList = locationList;
	}

	public void setWeatherstore(WeatherStore weatherstore) {
		this.weatherstore = weatherstore;
	}

	public void setWeathersupplier(WeatherSupplier weathersupplier) {
		this.weathersupplier = weathersupplier;
	}

	public void createWeatherDatabase(){
			try(Connection connection = weatherstore.connect()) {
				Statement statement = connection.createStatement();
				for(Location location : this.locationList){
					weatherstore.createTable(statement, location.getName());
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
	}

	public void insertWeatherIntoDatabase(List<Instant> instantsList){
		try(Connection connection = weatherstore.connect()) {
			Statement statement = connection.createStatement();
			for(Location location : this.locationList){
				for (Weather weather :  weathersupplier.getWeatherList(location,instantsList)){
					weatherstore.insert(statement, location.getName(), weather);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
