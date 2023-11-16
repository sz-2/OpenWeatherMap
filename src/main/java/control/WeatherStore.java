package control;

import model.Weather;
import java.util.List;


public interface WeatherStore {
	public void saveWeathers(List<Weather> weathers);
}
