package control;

import model.Location;
import model.Weather;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SqliteWeatherStore implements WeatherStore{
	private String dbPath;

	public SqliteWeatherStore(String dbPath)
	{
		this.dbPath = dbPath;
	}

	public String getDbPath() {
		return dbPath;
	}

	public void setDbPath(String dbPath) {
		this.dbPath = dbPath;
	}

	@Override
	public void saveWeathers(List<Weather> weathers) {

		try(Connection connection = this.connect()) {
			Statement statement = connection.createStatement();
			for (Weather weather :  weathers){
				this.insert(statement, weather.getLocation().getName(), weather);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}


	public void createWeatherDatabase(List<Location> locationList){
		try(Connection connection = this.connect()) {
			Statement statement = connection.createStatement();
			for(Location location : locationList){
				this.createTable(statement, location.getName());
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
			System.out.println("Connection to SQLite has been established.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
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


	// modificar paar evitar la inyeccion de codigo en la base de datos
	// hacer la preparacion de la string más seguro
	public void insert(Statement statement, String tableName, Weather weather) throws SQLException {
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

