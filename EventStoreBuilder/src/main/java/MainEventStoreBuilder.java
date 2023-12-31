public class MainEventStoreBuilder {
	public static void main(String[] args) {
		String brokerURL = args[0];
		String topicName = args[1];
		String clientID = args[2];
		String filePath = args[3];

		FileEventStoreBuilder writeEvent = new FileEventStoreBuilder(filePath);
		TopicEventSubscriber eventConsumer = new TopicEventSubscriber(brokerURL, topicName, clientID, writeEvent);
		eventConsumer.startListening();
	}
}