package spring.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class CustomProducer {
	
	@Autowired
	private KafkaTemplate<Object, Object> kafkaTemplate;
	
	public void sendMessage(String topic, Object key, Object data) {
		
		ListenableFuture<SendResult<Object, Object>> future = this.kafkaTemplate.send(topic, key, data);
		
		future.addCallback(new ListenableFutureCallback<SendResult<Object,Object>>() {

			@Override
			public void onSuccess(SendResult<Object, Object> result) {
				
				System.out.println("********** PRODUCER **********");
				System.out.println("Success on send callback");
				System.out.println(result.getProducerRecord().value());
				
			}

			@Override
			public void onFailure(Throwable ex) {
				
				System.out.println("********** PRODUCER **********");
				System.out.println("Failure on send callback");
				System.out.println(ex);
				throw new RuntimeException(ex);
				
			}
			
		});
	}
}
