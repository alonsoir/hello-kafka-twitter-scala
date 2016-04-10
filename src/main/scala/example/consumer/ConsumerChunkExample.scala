package example.consumer

object ConsumerChunkExample {
  def main(args: Array[String]): Unit = {
    val topicNames = if(args.length == 0) {
      List("testTopic")
    } else {
      args.toList
    }

    val consumer = ChunkConsumer(topicNames)

    val readResponse = consumer.read()
    println(readResponse)
  }
}
