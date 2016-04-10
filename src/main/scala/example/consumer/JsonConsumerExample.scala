package example.consumer

import play.api.libs.json._

object JsonConsumerExample {
  def main(args: Array[String]): Unit = {
    val topicName =
      if(args.length == 0) "jsonTopic"
      else args(0)

    val consumer = SingleTopicConsumer(topicName)

    implicit val reads = Json.reads[Message]

    consumer.read().foreach(println)
  }
}

case class Message(userId: Int, source: String)