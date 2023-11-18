package control;

import model.Location;
import java.time.Instant;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WeatherController {
	private final List<Location> locations;
	private final WeatherStore weatherStore;
	private final WeatherSupplier weatherSupplier;

	public WeatherController(List<Location> locations, WeatherStore weatherstore, WeatherSupplier weathersupplier){
		this.locations = locations;
		this.weatherStore = weatherstore;
		this.weatherSupplier = weathersupplier;
	}
	public void execute() {
		List<Instant> instants = Instants.getInstants(5, 12);
		for (Location location : this.locations) {
			this.weatherStore.saveWeathers(this.weatherSupplier.getWeathers(location, instants));
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
