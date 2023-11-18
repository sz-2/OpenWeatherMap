package control;

public class Url {
	public static String getUrl(String apiKey,Double latitude, Double longitude){
		return String.format("https://api.openweathermap.org/data/2.5/forecast?lat=%s&lon=%s&appid=%s&units=metric",latitude, longitude, apiKey);
	}
}
