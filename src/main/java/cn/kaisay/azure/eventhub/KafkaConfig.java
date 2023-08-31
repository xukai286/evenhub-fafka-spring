package cn.kaisay.azure.eventhub;

import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.security.auth.SecurityProtocol;
import org.apache.kafka.common.security.plain.internals.PlainSaslServer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.log.LogAccessor;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.RecordInterceptor;
import org.springframework.kafka.support.KafkaHeaders;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    protected final LogAccessor logger = new LogAccessor(LogFactory.getLog(this.getClass())); // NOSONAR
    @Bean
    public RecordInterceptor<Long, String> recordInterceptor() {
        return (record,consumer)-> {
//            int delivery = ByteBuffer.wrap(record.headers().lastHeader(KafkaHeaders.DELIVERY_ATTEMPT).value()).getInt();
//            logger.info("delivery " + record);
//            logger.info("consumer " + consumer);
            return record;
        };
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<Long, String>
    kafkaListenerContainerFactory(ConsumerFactory<Long, String> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Long, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setRecordInterceptor(recordInterceptor());
        return factory;
    }

    @Bean
    public ConsumerFactory<Long, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerProps());
    }

    private Map<String, Object> consumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SecurityProtocol.SASL_SSL.name);
        props.put(SaslConfigs.SASL_MECHANISM, PlainSaslServer.PLAIN_MECHANISM);
        props.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"$ConnectionString\" password=\"Endpoint=sb://kaisay1.servicebus.chinacloudapi.cn/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=21BohSL6Hy652YFSnsUMNXw6i+zDPsznp+AEhKZaszY=\";");
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kaisay1.servicebus.chinacloudapi.cn:9093");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // ...
        return props;
    }

    @Bean
    public Sender sender(KafkaTemplate<Long, String> template) {
        return new Sender(template);
    }

    @Bean
    public Listener listener() {
        return new Listener();
    }

    @Bean
    public ProducerFactory<Long, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(senderProps());
    }

    private Map<String, Object> senderProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SecurityProtocol.SASL_SSL.name);
        props.put(SaslConfigs.SASL_MECHANISM, PlainSaslServer.PLAIN_MECHANISM);
        props.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"$ConnectionString\" password=\"Endpoint=sb://kaisay1.servicebus.chinacloudapi.cn/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=21BohSL6Hy652YFSnsUMNXw6i+zDPsznp+AEhKZaszY=\";");
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kaisay1.servicebus.chinacloudapi.cn:9093");
        props.put(ProducerConfig.LINGER_MS_CONFIG, 10);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        //...
        return props;
    }

    @Bean
    public KafkaTemplate<Long, String> kafkaTemplate(ProducerFactory<Long, String> producerFactory) {
        return new KafkaTemplate<Long, String>(producerFactory);
    }

}