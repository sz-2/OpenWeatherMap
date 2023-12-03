package control;

import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import model.Weather;
import java.util.List;

public class JMSWeatherStore implements WeatherStore{

	private Connection connection;

	private Session session;
	private MessageProducer producer;

	private String brokerURL;
	private String destinationName;

	public JMSWeatherStore(String brokerURL, String destinationName) {
		this.brokerURL = brokerURL;
		this.destinationName = destinationName;
	}

	@Override
	public void saveWeathers(List<Weather> weathers) {
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

	public static void main(String[] args) {
		String brokerURL = "tcp://localhost:61616";
		String destinationName = "WeatherTopic";

		JMSWeatherStore eventProducer = new JMSWeatherStore(brokerURL, destinationName);
		eventProducer.sendEvent("Event data goes here3.");
		eventProducer.close();
	}
}
