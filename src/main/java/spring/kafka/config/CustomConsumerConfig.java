package spring.kafka.config;

import java.util.HashMap;
import java.util.List;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@EnableKafka
@Configuration
public class CustomConsumerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private List<String> bootstrapServers;

	@Bean
	public ConsumerFactory<Object, Object> consumerFactory(){

		HashMap<String, Object> configs = new HashMap<>();

		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
		configs.put(ConsumerConfig.CLIENT_ID_CONFIG, "consumer");
		configs.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-group-id");
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

		//Mutual auth over SSL
		configs.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
		configs.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, this.getClass().getClassLoader().getResource("client-truststore/ServerCertificate.jks").getFile().substring(1));
		configs.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, "client");
		configs.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, this.getClass().getClassLoader().getResource("client-keystore/ClientKeyStore.jks").getFile().substring(1));
		configs.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, "client");
		configs.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, "client");

		return new DefaultKafkaConsumerFactory<>(configs);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<Object, Object> listenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<Object, Object> ret = 
				new ConcurrentKafkaListenerContainerFactory<Object, Object>();

		ret.setConsumerFactory(this.consumerFactory());
		ret.setConcurrency(3);

		return ret;

	}

}
