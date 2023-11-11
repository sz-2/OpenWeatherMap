package control;

public class Url {

	public static String getUrl(String url,String apiKey,Double latitude, Double longitude){
		return String.format("%s?lat=%s&lon=%s&appid=%s&units=metric",url, latitude, longitude, apiKey);
	}
}
