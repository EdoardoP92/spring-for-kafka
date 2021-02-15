package spring.kafka.config;

import java.util.HashMap;
import java.util.List;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class CustomProducerConfig {
	
	@Value("${spring.kafka.bootstrap-servers}")
	private List<String> bootstrapServers;
	
	@Bean
	public ProducerFactory<Object, Object> producerFactory(){
		
		HashMap<String, Object> configs = new HashMap<>();
		
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
		configs.put(ProducerConfig.CLIENT_ID_CONFIG, "producer");
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		//Mutual auth over SSL
		configs.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
		configs.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, this.getClass().getClassLoader().getResource("client-truststore/ServerCertificate.jks").getFile().substring(1));
		configs.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, "client");
		configs.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, this.getClass().getClassLoader().getResource("client-keystore/ClientKeyStore.jks").getFile().substring(1));
		configs.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, "client");
		configs.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, "client");
		
		return new DefaultKafkaProducerFactory<>(configs);
	}
	
	@Bean
	public KafkaTemplate<Object, Object> kafkaTemplate(){
		
		return new KafkaTemplate<>(this.producerFactory());
	}

}
