import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class FileEventStoreBuilder implements EventStoreBuilder {
	private final String directory;

	public FileEventStoreBuilder(String directory) {
		this.directory = directory;
	}

	public void saveEvent(String event) {
		String filePath = this.pathBuilder(event);
		File folder = new File(filePath).getParentFile();
		if (!folder.exists()) {
			if (folder.mkdirs()) {
				System.out.println("The events will be stored in the following folder: " + folder.getAbsolutePath());
			} else {
				System.err.println("Failed to create folder: " + folder.getAbsolutePath());
			}
		}
		this.writeEventToFile(event, filePath);
	}

	private void writeEventToFile(String event, String filePath) {
		try (FileWriter writer = new FileWriter(filePath, true)) {
			writer.write(event);
			writer.write(System.lineSeparator());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getSS(String message) {
		return new JSONObject(message).getString("ss");
	}

	private String getTS(String message) {
		String ts = new JSONObject(message).getString("ts");
		Instant instant = Instant.parse(ts);
		LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
		return dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	}

	private String pathBuilder(String event) {
		return String.format("%s/eventstore/prediction.Weather/%s/%s.events", this.directory, this.getSS(event), this.getTS(event));
	}
}


