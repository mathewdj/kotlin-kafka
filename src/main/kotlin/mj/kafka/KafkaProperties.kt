package mj.kafka

import java.net.InetAddress
import java.util.*

object KafkaProperties {
    fun config(): Properties {
        val config = Properties()
        config["client.id"] = InetAddress.getLocalHost().hostName
        config["bootstrap.servers"] = "localhost:9092"
        config["auto.offset.reset"] = "earliest"
        config["enable.auto.commit"] = "false"
        config["key.deserializer"] = "org.apache.kafka.common.serialization.StringDeserializer"
        config["value.deserializer"] = "org.apache.kafka.common.serialization.StringDeserializer"
        config["group.id"] = "bar"

        return config
    }

    const val topic = "crumpler"
}