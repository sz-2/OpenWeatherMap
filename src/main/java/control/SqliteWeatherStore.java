package control;

import model.Location;
import model.Weather;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SqliteWeatherStore implements WeatherStore{
	private  final String dbPath;
	private final List<Location> locations;

	public SqliteWeatherStore(String dbPath, List<Location> locations)
	{
		this.dbPath = dbPath;
		this.locations = locations;
	}

	@Override
	public void saveWeathers(List<Weather> weathers) {
		try(Connection connection = this.connect()) {
			Statement statement = connection.createStatement();
			this.createWeatherDatabase();
			for (Weather weather :  weathers){
				this.insertWeather(statement, weather.getLocation().getName(), weather);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Connection connect() {
		Connection conn = null;
		try {
			String url = "jdbc:sqlite:" + this.dbPath;
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	public void createWeatherDatabase(){
		try(Connection connection = this.connect()) {
			Statement statement = connection.createStatement();
			for(Location location : locations){
				this.createTable(statement, location.getName());
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void createTable(Statement statement, String tableName) throws SQLException {
		statement.execute("CREATE TABLE IF NOT EXISTS " + tableName +
				" (" +
				"ts TEXT PRIMARY KEY," +
				"temperature REAL," +
				"rain REAL REAL,"+
				"humidity REAL,"+
				"clouds REAL,"+
				"windSpeed REAL"+
				");");
	}


	public void insertWeather(Statement statement, String tableName, Weather weather) throws SQLException {
		statement.execute(String.format(
				"INSERT OR REPLACE INTO %s (ts, temperature, humidity, rain, clouds, windSpeed) " +
						"VALUES ('%s', %s, %s, %s, %s, %s);",
				tableName,
				weather.getTs(),
				weather.getTemperature(),
				weather.getHumidity(),
				weather.getRain(),
				weather.getClouds(),
				weather.getWindSpeed()
		));
	}
}

