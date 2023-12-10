import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class TopicEventSubscriber implements Subscriber {
	private final String brokerURL;
	private final String topicName;
	private final String clientID;
	private final FileEventStoreBuilder fileEventStore;

	public TopicEventSubscriber(String brokerURL, String topicName, String clientID, FileEventStoreBuilder fileEventStore) {
		this.brokerURL = brokerURL;
		this.topicName = topicName;
		this.clientID = clientID;
		this.fileEventStore = fileEventStore;
	}

	public void startListening() {
		try {
			TopicSubscriber subscriber = this.createConnection();
			MessageListener messageListener = messageListener();
			subscriber.setMessageListener(messageListener);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}

	private TopicSubscriber createConnection() {
		TopicSubscriber subscriber = null;
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(this.brokerURL);
			Connection connection = connectionFactory.createConnection();
			connection.setClientID(clientID);
			connection.start();
			Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			Topic topic = session.createTopic(this.topicName);
			subscriber = session.createDurableSubscriber(topic, subscriptionName());
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return subscriber;
	}

	private MessageListener messageListener() {
		return message -> {
			try {
				if (message instanceof TextMessage textMessage) {
					String eventData = textMessage.getText();
					this.fileEventStore.saveEvent(eventData);
					message.acknowledge();
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		};
	}

	private String subscriptionName() {
		return String.format("%s.%s", this.topicName, this.clientID);
	}
}