package control;


import com.google.gson.*;
import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import model.Weather;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.List;

public class JMSWeatherSender implements WeatherStore{

	private Connection connection;

	private Session session;
	private MessageProducer producer;

	private String brokerURL;
	private String destinationName;

	public JMSWeatherSender(String brokerURL, String destinationName) {
		this.brokerURL = brokerURL;
		this.destinationName = destinationName;
	}

	@Override
	public void saveWeathers(List<Weather> weathers) {
		for (Weather weather : weathers){
			prepareProducer();
			sendEvent(WeatherToJson(weather));
			close();
		}
	}

	// Custom serializer for Instant class
	private static class InstantSerializer implements JsonSerializer<Instant> {
		@Override
		public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
			return new JsonPrimitive(instant.toString());
		}
	}

	public void prepareProducer() {
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(this.brokerURL);
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createTopic(this.destinationName);
			producer = session.createProducer(destination);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void sendEvent(String eventData) {
		try {
			this.prepareProducer();
			TextMessage eventMessage = session.createTextMessage(eventData);
			producer.send(eventMessage);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			producer.close();
			session.close();
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public String WeatherToJson(Weather weather){
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(Instant.class, new InstantSerializer())
				.create();
		return gson.toJson(weather);

	}
}


