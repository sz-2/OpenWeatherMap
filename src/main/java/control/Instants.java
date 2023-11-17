package control;

import java.time.*;
import java.util.ArrayList;
import java.util.List;


public class Instants {
	public static List<Instant> getInstants(int numberOfDays, int hour) {
		List<Instant> instants = new ArrayList<>();
		LocalDateTime currentDateTime = LocalDateTime.now();
		currentDateTime = currentDateTime.withHour(hour).withMinute(0).withSecond(0).withNano(0);

		for (int i = 0; i < numberOfDays; i++) {
			Instant instant = currentDateTime.toInstant(ZoneOffset.UTC);
			instants.add(instant);
			currentDateTime = currentDateTime.plusDays(1);
		}

		return instants;
	}
}

