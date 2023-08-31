# evenhub-fafka-spring
Eventhub 为standard tier，kafka client连接需要启用SASL
# Kafka 工具类配置

* 添加JVM参数  -Djava.security.auth.login.config=kafka_client_jaas.conf
* Kafka的官方文档没有说明格式实际测试应该为

kafka_client_jaas.conf：

    KafkaClient {
        org.apache.kafka.common.security.plain.PlainLoginModule required
        username="$ConnectionString"
        password="Endpoint=sb://kaisay1.servicebus.chinacloudapi.cn/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=21BohSL6Hy652YFSnsUMNXw6i+zDPsznp+AEhKZaszY=";
    };


client-ssl.properties：

    security.protocol=SASL_SSL
    sasl.mechanism=PLAIN

![screenshot](231107.png)
