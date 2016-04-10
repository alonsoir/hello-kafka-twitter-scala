package example.consumer

object ConsumerStreamExample {
  def main(args: Array[String]): Unit = {
    val topicNames = if(args.length == 0) {
      "testTopic"
    } else {
      args.head
    }
    println("topicNames is " + topicNames)
    val consumer = SingleTopicConsumer(topicNames)

    consumer.read().foreach(println)
  }
}
