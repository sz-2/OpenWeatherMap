
import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;


public class EventStore {

	private Connection connection;
	private Session session;
	private String brokerURL;
	private String topicName;
	private String clientId;


	public EventStore(String brokerURL, String topicName, String clientId) {
		this.brokerURL = brokerURL;
		this.topicName = topicName;
		this.clientId = clientId;
	}

	public void getEventFromBroker() {
		try {
			// Create a connection
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
			connection = connectionFactory.createConnection();
			connection.setClientID(clientId);
			connection.start();
			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			Topic topic = session.createTopic(topicName);
			TopicSubscriber subscriber = session.createDurableSubscriber(topic, "YourSubscriptionName");
			MessageListener messageListener = message -> {
				try {
					if (message instanceof TextMessage) {
						TextMessage textMessage = (TextMessage) message;
						String eventData = textMessage.getText();
						System.out.println("Received event: " + eventData);

						// Acknowledge the message
						message.acknowledge();
					}
				} catch (JMSException e) {
					e.printStackTrace();
				}
			};

			subscriber.setMessageListener(messageListener);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	private void close() {
		try {
			if (session != null) {
				session.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String brokerURL = "tcp://localhost:61616";
		String topicName = "WeatherTopic";
		String clientId = "YourClientId";
		//String subscriptionName = "YourSubscriptionName";

		EventStore eventConsumer = new EventStore(brokerURL, topicName, clientId);
		eventConsumer.getEventFromBroker();
	}
}
