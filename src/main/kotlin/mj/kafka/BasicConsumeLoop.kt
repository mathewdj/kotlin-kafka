package mj.kafka

import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

class BasicConsumeLoop(
    private val consumer: KafkaConsumer<String, String>,
    private val topics: List<String>,
    private val shutdown: AtomicBoolean = AtomicBoolean(false),
    private val shutdownLatch: CountDownLatch = CountDownLatch(1),
) : Runnable {

    private fun process(records: ConsumerRecords<String, String>?) {
        records?.forEach {
            logger.info(it.value())
        }
    }

    override fun run() {
        try {
            consumer.subscribe(topics)

            while(!shutdown.get()) {
                val records = consumer.poll(Duration.ofSeconds(10))
                process(records)
            }
        } finally {
            consumer.close()
            shutdownLatch.countDown()
        }
    }

    fun shutdown() {
        shutdown.set(true)
        shutdownLatch.await()
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(BasicConsumeLoop::class.java)
    }
}

fun main() {
    val executor = Executors.newSingleThreadExecutor()

    val config = KafkaProperties.config()
    val kafkaConsumer = KafkaConsumer<String, String>(config)
    val basicConsumeLoop = BasicConsumeLoop(kafkaConsumer, listOf(KafkaProperties.topic))

    executor.execute(basicConsumeLoop)
}
