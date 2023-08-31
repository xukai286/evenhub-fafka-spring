package cn.kaisay.azure.eventhub;

import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogAccessor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

public class Listener {

    protected final LogAccessor logger = new LogAccessor(LogFactory.getLog(this.getClass())); // NOSONAR

    @KafkaListener(id = "listen2", topics = "topic1")
    public void listen1(@Header(KafkaHeaders.OFFSET)Long offset, @Header(KafkaHeaders.RECEIVED_PARTITION)int  partition,
                        String in, ConsumerRecordMetadata meta) {
        logger.info("Offset is :"+offset+" Partition is ："+ partition +" Date is: "+in);
    }

}