package example.consumer

import kafka.consumer.{Consumer => KafkaConsumer, ConsumerIterator, Whitelist}
import kafka.serializer.{DefaultDecoder, Decoder}
import scala.collection.JavaConversions._

case class StreamConsumer(topics: List[String]) extends Consumer(topics) {
  //topics to listen
  private val filterSpec = new Whitelist(topics.mkString(","))

  protected val keyDecoder: Decoder[Array[Byte]] = new DefaultDecoder()
  protected val valueDecoder: Decoder[Array[Byte]] = new DefaultDecoder()

  private lazy val consumer = KafkaConsumer.create(config)
  private lazy val stream = consumer.createMessageStreamsByFilter(filterSpec, 1, keyDecoder, valueDecoder).get(0)

  def read(): Stream[String] = Stream.cons(new String(stream.head.message()), read())
}

object StreamConsumer {
  def apply(topics: List[String], kDecoder: Decoder[Array[Byte]], vDecoder: Decoder[Array[Byte]]) = new StreamConsumer(topics) {
    override val keyDecoder = kDecoder
    override val valueDecoder = vDecoder
  }
}

case class SingleTopicConsumer(topic: String) extends Consumer(List(topic)) {
  private lazy val consumer = KafkaConsumer.create(config)
  val threadNum = 1

  private lazy val consumerMap = consumer.createMessageStreams(Map(topic -> threadNum))
  private lazy val stream = consumerMap.getOrElse(topic, List()).head

  override def read(): Stream[String] = Stream.cons(new String(stream.head.message()), read())
}