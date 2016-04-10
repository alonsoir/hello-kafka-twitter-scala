package example
package consumer

import example.utils.KafkaConfig
import kafka.consumer.{ Consumer => KafkaConsumer }
import kafka.consumer._
import kafka.serializer._
import scala.collection.JavaConversions._
import kafka.api._

abstract class Consumer(topics: List[String]) {

  protected val kafkaConfig = KafkaConfig()
  protected val config = new ConsumerConfig(kafkaConfig)

  def read(): Iterable[String]
}