package model;

public class Location {
	private double latidude;
	private double longitude;
	private String name;

	public Location(String name, double latidude, double longitude) {
		this.name = name;
		this.latidude = latidude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latidude;
	}

	public double getLongitude() {
		return longitude;
	}

	public String getName() {
		return name;
	}
}