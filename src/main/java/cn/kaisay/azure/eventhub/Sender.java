package cn.kaisay.azure.eventhub;

import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.log.LogAccessor;
import org.springframework.kafka.core.KafkaTemplate;

public class Sender {

    protected final LogAccessor logger = new LogAccessor(LogFactory.getLog(this.getClass())); // NOSONAR
    private final KafkaTemplate<Long, String> template;

    public Sender(KafkaTemplate<Long, String> template) {
        this.template = template;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(KafkaConfig.class);
        context.getBean(Sender.class).send("test", 42);
        context.getBean(Sender.class).send("test1", 421);
        context.getBean(Sender.class).send("test2", 422);
        context.getBean(Sender.class).send("test3", 423);
        context.getBean(Sender.class).send("test4", 424);
    }

    public void send(String toSend, long key) {
        this.template.send("topic1", key, toSend);
    }

}