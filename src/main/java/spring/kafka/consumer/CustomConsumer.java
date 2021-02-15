package spring.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class CustomConsumer {
	
//	@KafkaListener(topics = "#{T(spring.kafka.properties.PropertyManager).getProperty('spring.kafka.topic')}", groupId = "consumer-group-id")
	@KafkaListener(topics = "${spring.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
	public void consumeMessage(@Payload ConsumerRecord<Object, Object> message) {
		
		System.out.println("********** CONSUMER **********");
		System.out.println("Message key: "+message.key());
		System.out.println("Message value: "+message.value());
		System.out.println("Message timestamp: "+message.timestamp());
	}
	
}