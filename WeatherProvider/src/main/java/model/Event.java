package model;

import model.Weather;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Event {
	private String ss;
	private Instant ts;
	private List<Weather> weathersPrediction;

	public Event(String ss, Instant ts) {
		this.ss = ss;
		this.ts = ts;
		this.weathersPrediction = new ArrayList<>();
	}

	public void addWeatherPrediction(List<Weather> weathers){
		this.weathersPrediction.addAll(weathers);
	}
}
