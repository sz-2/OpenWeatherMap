import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class FileEventStoreBuilder implements EventStoreBuilder{

	private final String filePath;

	public FileEventStoreBuilder(String filePath) {

		this.filePath = filePath;
	}

	public void save(String content) {
		ssEvent(content);
		tsEvent(content);
		String filePath = getPath(this.filePath, ssEvent(content), tsEvent(content));
		File folder = new File(filePath).getParentFile();
		if (!folder.exists()) {
			if (folder.mkdirs()) {
				System.out.println("Folder created: " + folder.getAbsolutePath());
			} else {
				System.err.println("Failed to create folder: " + folder.getAbsolutePath());
			}
		}

		try (FileWriter writer = new FileWriter(filePath, true)) {
			writer.write(content);
			writer.write(System.lineSeparator());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String ssEvent(String message){
		return new JSONObject(message).getString("ss");
	}

	public String tsEvent(String message) {
		String ts = new JSONObject(message).getString("ts");
		Instant instant = Instant.parse(ts);
		LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
		return dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	}

	public String getPath(String filePath, String ss, String ts){
		return String.format("%s/eventstore/prediction.Weather/%s/%s.events", filePath, ss, ts);
	}

	// File fileOf(String)
}


