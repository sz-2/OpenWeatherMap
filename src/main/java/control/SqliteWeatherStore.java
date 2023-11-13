package control;

import model.Weather;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteWeatherStore implements WeatherStore{
	private String dbPath;

	public SqliteWeatherStore(String dbPath)
	{
		this.dbPath = dbPath;
	}

	@Override
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


	@Override
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

	@Override
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

