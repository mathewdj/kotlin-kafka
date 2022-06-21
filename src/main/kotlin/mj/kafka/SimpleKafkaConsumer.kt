package mj.kafka

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.InetAddress
import java.time.Duration
import java.util.*

class SimpleKafkaConsumer {
    fun consumeKafka() {
        val config = KafkaProperties.config()
        val consumer = KafkaConsumer<String, String>(config)
        consumer.subscribe(listOf("crumpler"))
        val records = consumer.poll(Duration.ofSeconds(5))

        logger.info("records.count: ${records.count()}")
        records.forEach {
            logger.info("value:${it.value()}")
        }

        // keep consuming from start each time
        //consumer.commitSync()
        consumer.close()
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(SimpleKafkaConsumer::class.java)
    }
}


fun main() {
    SimpleKafkaConsumer().consumeKafka()
}

