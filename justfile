KAFKA_VERSION := "2.13-3.2.0"

kafka-download-cli:
    wget -nc "https://dlcdn.apache.org/kafka/3.2.0/kafka_{{KAFKA_VERSION}}.tgz"
    tar -xf kafka_{{KAFKA_VERSION}}.tgz
    mv kafka_{{KAFKA_VERSION}} kafka_bin