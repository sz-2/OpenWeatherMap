package control;

import model.Location;
import model.Event;

import java.time.Instant;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WeatherController {
	private final List<Location> locations;
	private final WeatherSupplier weatherSupplier;

	public WeatherController(List<Location> locations, WeatherSupplier weathersupplier, Event event){
		this.locations = locations;
		this.weatherSupplier = weathersupplier;
	}
	public void execute() {
		Event event = new Event(this.weatherSupplier.getProviderName(), Instant.now());
		List<Instant> instants = Instants.getInstants(5, 12);
		for (Location location : this.locations) {
			event.addWeatherPrediction(this.weatherSupplier.getWeathers(location, instants));
		}
	}
	public void executionTimer(int minutes){
		Timer timer = new Timer();

		long periodo = (long) minutes * 60 * 1000;
		TimerTask weatherTask = new TimerTask() {
			@Override
			public void run() {
				execute();
				System.out.println("Las predicciones se han almacenado con éxito.");
			}
		};

		timer.schedule(weatherTask, 0, periodo);
	}
}
