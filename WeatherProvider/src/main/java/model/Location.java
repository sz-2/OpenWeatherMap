package model;

public class Location {
	private final double latidude;
	private final double longitude;
	private final String name;

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
}