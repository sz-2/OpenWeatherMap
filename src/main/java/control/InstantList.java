package control;

import java.time.*;
import java.util.ArrayList;
import java.util.List;


public class InstantList {
	public static List<Instant> getInstants(int numberOfDays, int hour) {
		List<Instant> instantList = new ArrayList<>();
		LocalDateTime currentDateTime = LocalDateTime.now();
		currentDateTime = currentDateTime.withHour(hour).withMinute(0).withSecond(0).withNano(0);

		for (int i = 0; i < numberOfDays; i++) {
			Instant instant = currentDateTime.toInstant(ZoneOffset.UTC);
			instantList.add(instant);
			currentDateTime = currentDateTime.plusDays(1);
		}

		return instantList;
	}
}

