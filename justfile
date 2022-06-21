KAFKA_VERSION := "2.13-3.2.0"
TOPIC_NAME := "crumpler"
KAFKA := "kafka_bin/bin/"

kafka-download-cli:
    wget -nc "https://dlcdn.apache.org/kafka/3.2.0/kafka_{{KAFKA_VERSION}}.tgz"
    tar -xf kafka_{{KAFKA_VERSION}}.tgz
    mv kafka_{{KAFKA_VERSION}} kafka_bin

create-topic:
    {{KAFKA}}kafka-topics.sh \
      --bootstrap-server localhost:9092 \
      --create \
      --topic {{TOPIC_NAME}} \
      --partitions 3

create-event:
    {{KAFKA}}kafka-console-producer.sh \
      --bootstrap-server localhost:9092 \
      --topic {{TOPIC_NAME}}

consume-event:
    {{KAFKA}}kafka-console-consumer.sh \
       --bootstrap-server localhost:9092 \
       --topic {{TOPIC_NAME}} \
       --from-beginning \
       --property key.separator="-" \
       --property print.key=true \
       --property print.value=true \
       --property  key.deserialzer=org.apache.kafka.common.serialization.StringDeserializer \
       --property value.deserializer=org.apache.kafka.common.serialization.StringDeserializer