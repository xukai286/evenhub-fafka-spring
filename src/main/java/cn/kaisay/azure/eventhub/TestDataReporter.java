package cn.kaisay.azure.eventhub;

//Copyright (c) Microsoft Corporation. All rights reserved.
//Licensed under the MIT License.
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.core.log.LogAccessor;

import java.sql.Timestamp;

public class TestDataReporter implements Runnable {

    protected final LogAccessor logger = new LogAccessor(LogFactory.getLog(this.getClass())); // NOSONAR

    private static final int NUM_MESSAGES = 100;
    private final String TOPIC;

    private Producer<Long, String> producer;

    public TestDataReporter(final Producer<Long, String> producer, String TOPIC) {
        this.producer = producer;
        this.TOPIC = TOPIC;
        logger.trace("A TRACE Message");
        logger.debug("A DEBUG Message");
        logger.info("An INFO Message");
        logger.warn("A WARN Message");
        logger.error("An ERROR Message");

    }

    @Override
    public void run() {
        long time = System.currentTimeMillis();
        for(int i = 0, index=1; i < NUM_MESSAGES; i++) {

            System.out.println("Test Data #" + i + " from thread #" + Thread.currentThread().getId());

            final ProducerRecord<Long, String> record = new ProducerRecord<Long, String>(TOPIC, time++, "Test Data #" + i);
            producer.send(record, new Callback() {
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception != null) {
                        System.out.println(exception);
                        System.exit(1);
                    }
                }
            });
        }
        System.out.println("Finished sending " + NUM_MESSAGES + " messages from thread #" + Thread.currentThread().getId() + "!");
    }
}