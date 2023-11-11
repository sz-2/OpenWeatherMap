package control;


import model.Location;
import model.Weather;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class OpenWeatherMapSupplier implements WeatherSupplier{

	// Replace "{API key}" with your actual OpenWeatherMap API key
	private String apiKey;
	private String url;

	public OpenWeatherMapSupplier(String apiKey, String url) {
		this.apiKey = apiKey;
		this.url = url;
	}

	public String getWeatherFromAPI(Location location){

		StringBuilder response = new StringBuilder();

		try {

			URL url = new URL(Url.getUrl(this.url, this.apiKey, location.getLatitude(), location.getLongitude()));
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			int responseCode = connection.getResponseCode();
			System.out.println("Sending GET request to URL: " + url.toString());
			System.out.println("Response Code: " + responseCode);

			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;

				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
				reader.close();

				System.out.println(response.getClass());
				// Process the response data as needed
			} else {
				System.out.println("GET request failed");
			}

			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response.toString();
	}


	public List<Weather> getWeatherList(Location location, List<Instant>instatsList){

		List<Weather> weatherList = new ArrayList<>();

		JSONObject myJson = new JSONObject(getWeatherFromAPI(location));
		JSONArray weathersJsonList = myJson.getJSONArray("list");

		for (int index=0; index<weathersJsonList.length(); index++){
			JSONObject weather = weathersJsonList.getJSONObject(index);
			if (instatsList.contains(Instant.ofEpochSecond(weather.getLong("dt")))){
				Instant ts = Instant.ofEpochSecond(weather.getLong("dt"));
				double temp = weather.getJSONObject("main").getDouble("temp");
				double rain = weather.getDouble("pop");
				double humidity = weather.getJSONObject("main").getDouble("humidity");
				double windSpeed = weather.getJSONObject("wind").getDouble("speed");
				double clouds = weather.getJSONObject("clouds").getDouble("all");
				weatherList.add(new Weather(ts, temp, rain, humidity, clouds, windSpeed, location));
			}
		}

		return weatherList;
	}


}


