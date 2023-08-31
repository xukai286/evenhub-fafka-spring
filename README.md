# evenhub-fafka-spring
Eventhub 为standard tier，kafka client连接需要启用SASL
# Kafka 工具类配置

添加JVM参数  -Djava.security.auth.login.config=kafka_client_jaas.conf
Kafka的官方文档没有说明格式实际测试应该为

kafka_client_jaas.conf：

KafkaClient {
org.apache.kafka.common.security.plain.PlainLoginModule required
username="$ConnectionString"
password="Endpoint=sb://kaisay1.servicebus.chinacloudapi.cn/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=21BohSL6Hy652YFSnsUMNXw6i+zDPsznp+AEhKZaszY=";
};


client-ssl.properties：

security.protocol=SASL_SSL
sasl.mechanism=PLAIN


 31/08/2023   22:52.22   /home/mobaxterm/MyDocuments/kafka_2.13-3.4.1/bin  ./kafka-consumer-groups.sh --bootstrap-server kaisay1.servicebus.chinacloudapi.cn:9093 --command-config client-ssl.properties --describe --group listen2 --timeout 100000

GROUP           TOPIC           PARTITION  CURRENT-OFFSET  LOG-END-OFFSET  LAG             CONSUMER-ID                                                                                         HOST            CLIENT-ID
listen2         topic1          0          150             150             0               kaisay1.servicebus.chinacloudapi.cn:c:listen2:I:consumer-listen2-1-e96085abb33e4191b06116f7db18cec4 0.0.0.0         consumer-listen2-1
listen2         topic1          1          85              85              0               kaisay1.servicebus.chinacloudapi.cn:c:listen2:I:consumer-listen2-1-e96085abb33e4191b06116f7db18cec4 0.0.0.0         consumer-listen2-1
listen2         topic1          2          111             111             0               kaisay1.servicebus.chinacloudapi.cn:c:listen2:I:consumer-listen2-1-e96085abb33e4191b06116f7db18cec4 0.0.0.0         consumer-listen2-1
