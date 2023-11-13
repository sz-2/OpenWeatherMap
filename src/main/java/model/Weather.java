package model;

import java.time.Instant;

public class Weather {

	private final Instant ts;
	private final float temperature;
	private final float rain;
	private final float humidity;
	private final float clouds;
	private final float windSpeed;
	private final Location location;

	public Weather(Instant ts, float temperature, float rain, float humidity, float clouds, float windSpeed, Location location) {
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

	public float getTemperature() {
		return temperature;
	}

	public float getRain() {
		return rain;
	}

	public float getHumidity() {
		return humidity;
	}

	public float getClouds() {
		return clouds;
	}

	public float getWindSpeed() {
		return windSpeed;
	}

	public Location getLocation() {
		return location;
	}
}
