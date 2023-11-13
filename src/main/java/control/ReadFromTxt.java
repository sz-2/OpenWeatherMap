package control;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFromTxt {

	public static String getApiKey(String path) {
		String apiKey = null;
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			apiKey = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return apiKey;
	}
}







