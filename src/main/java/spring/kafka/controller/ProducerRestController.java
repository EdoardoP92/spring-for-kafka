package spring.kafka.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.kafka.producer.CustomProducer;

@RestController
@RequestMapping("/")
public class ProducerRestController {

	@Autowired
	private CustomProducer producer;
	
	@GetMapping("/send/message/{topic}")
	public String sendMessage(@PathVariable String topic, @RequestBody Map<Object, Object> message) {
		
		String messageKey = message.get("key") == null ? "defaultKey" : String.valueOf(message.get("key"));
		
		this.producer.sendMessage(topic, messageKey, message);
		
		return "Message sent";
	}
}
