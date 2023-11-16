package control;

import model.Location;
import model.Weather;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class OpenWeatherMapSupplier implements WeatherSupplier{

	private String apiKey;
	private String urlBase;

	public OpenWeatherMapSupplier(String apiKey, String url) {
		this.apiKey = apiKey;
		this.urlBase = url;
	}

	public String getWeatherFromAPI(Location location){

		String url = Url.getUrl(this.urlBase, this.apiKey, location.getLatitude(), location.getLongitude());
		String responseBody = null;

		try (CloseableHttpClient client = HttpClients.createDefault()) {

			HttpGet httpGet = new HttpGet(url);

			try (CloseableHttpResponse response = client.execute(httpGet)) {

				int statusCode = response.getStatusLine().getStatusCode();
				System.out.println("Response Code: " + statusCode);

				responseBody = EntityUtils.toString(response.getEntity());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseBody;
	}


	public List<Weather> getWeathers(Location location, List<Instant>instants){

		List<Weather> weatherList = new ArrayList<>();

		JSONObject myJson = new JSONObject(getWeatherFromAPI(location));
		JSONArray weathersJsonList = myJson.getJSONArray("list");

		for (int index=0; index<weathersJsonList.length(); index++){
			JSONObject weather = weathersJsonList.getJSONObject(index);
			if (instants.contains(Instant.ofEpochSecond(weather.getLong("dt")))){
				Instant ts = Instant.ofEpochSecond(weather.getLong("dt"));
				float temp = weather.getJSONObject("main").getFloat("temp");
				float rain = weather.getFloat("pop");
				float humidity = weather.getJSONObject("main").getFloat("humidity");
				float windSpeed = weather.getJSONObject("wind").getFloat("speed");
				float clouds = weather.getJSONObject("clouds").getFloat("all");
				weatherList.add(new Weather(ts, temp, rain, humidity, clouds, windSpeed, location));
			}
		}

		return weatherList;
	}


}


