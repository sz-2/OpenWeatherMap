package control;

import model.Weather;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public interface WeatherStore {

	Connection connect();

	void createTable(Statement statement, String tableName) throws SQLException;

	void insert(Statement statement, String tableName, Weather weather) throws SQLException;
}
