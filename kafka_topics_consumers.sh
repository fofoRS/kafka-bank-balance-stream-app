./bin/kafka-topics.sh --zookeeper zookeeper:2181 --create --topic transaction \
--partitions 1 --replication-factor 1

./bin/kafka-topics.sh --zookeeper zookeeper:2181 --create --topic accountBalanceAggregate \
--partitions 1 --replication-factor 1  --config cleanup.policy=compact


# launch a Kafka consumer
./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 \
    --topic accountBalanceAggregate \
    --from-beginning \
    --formatter kafka.tools.DefaultMessageFormatter \
    --property print.key=true \
    --property print.value=true \
    --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \
    --property value.deserializer=org.apache.kafka.common.serialization.StringDeserializer


./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 \
    --topic transaction \
    --from-beginning \
    --formatter kafka.tools.DefaultMessageFormatter \
    --property print.key=true \
    --property print.value=true \
    --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \
    --property value.deserializer=org.apache.kafka.common.serialization.StringDeserializer