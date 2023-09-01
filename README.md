# Spring-Kafka 连接 Azure EventHub
Eventhub 为standard tier，kafka client连接需要启用SASL
# Kafka 工具类配置
* version kafka_2.13-3.4.1
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

执行脚本
>./kafka-consumer-groups.sh --bootstrap-server kaisay1.servicebus.chinacloudapi.cn:9093 --command-config client-ssl.properties --describe --group listen2 --timeout 100000

Output:
![screenshot](231107.png)
