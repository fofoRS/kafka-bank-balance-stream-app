# create transaction topic, used to publish the back transactions
./bin/kafka-topics.sh --zookeeper zookeeper:2181 --create --topic transaction \
--partitions 1 --replication-factor 1

# create accountBalanceAggregate to publish the aggregate back balance,
# use cleaup.policy=compact to increase space usage and keep most recent result.

./bin/kafka-topics.sh --zookeeper zookeeper:2181 --create --topic accountBalanceAggregate \
--partitions 1 --replication-factor 1  --config cleanup.policy=compact

# kafka consumer for transaction
./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 \
    --topic transaction \
    --from-beginning \
    --formatter kafka.tools.DefaultMessageFormatter \
    --property print.key=true \
    --property print.value=true \
    --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \
    --property value.deserializer=org.apache.kafka.common.serialization.StringDeserializer

# kafka consumer for accountBalanceAggregate
./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 \
    --topic accountBalanceAggregate \
    --from-beginning \
    --formatter kafka.tools.DefaultMessageFormatter \
    --property print.key=true \
    --property print.value=true \
    --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \
    --property value.deserializer=org.apache.kafka.common.serialization.StringDeserializer