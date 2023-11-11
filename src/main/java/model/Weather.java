package model;

import java.time.Instant;

public class Weather {

	private final Instant ts;
	private final double temperature;
	private final double rain;
	private final double humidity;
	private final double clouds;
	private final double windSpeed;
	private final Location location;

	public Weather(Instant ts, double temperature, double rain, double humidity, double clouds, double windSpeed, Location location) {
		this.ts = ts;
		this.temperature = temperature;
		this.rain = rain;
		this.humidity = humidity;
		this.clouds = clouds;
		this.windSpeed = windSpeed;
		this.location = location;
	}

	public Instant getTs() {
		return ts;
	}

	public double getTemperature() {
		return temperature;
	}

	public double getRain() {
		return rain;
	}

	public double getHumidity() {
		return humidity;
	}

	public double getClouds() {
		return clouds;
	}

	public double getWindSpeed() {
		return windSpeed;
	}

	public Location getLocation() {
		return location;
	}
}
