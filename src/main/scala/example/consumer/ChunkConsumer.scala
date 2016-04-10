package example.consumer

import kafka.api.FetchRequestBuilder
import kafka.consumer.SimpleConsumer

case class ChunkConsumer(topics: List[String], partition: Int = 0, offset: Long = 0L, fetchSize: Int = 100) extends Consumer(topics){

  private val clientId = kafkaConfig.getCustomString("consumer.clientId")

  val simpleConsumer = new SimpleConsumer(
    kafkaConfig.getCustomString("consumer.host"),
    kafkaConfig.getCustomInt("consumer.port"),
    kafkaConfig.getCustomInt("consumer.timeOut"),
    kafkaConfig.getCustomInt("consumer.bufferSize"),
    clientId)

  def read(): Iterable[String] = {

    //println(simpleConsumer.toString)
    val fetchRequest = new FetchRequestBuilder().clientId(clientId)
    for(topic <- topics) {
      fetchRequest.addFetch(topic, partition, offset, fetchSize)
    }

    val fetchResponse = simpleConsumer.fetch(fetchRequest.build())

    fetchResponse.data.values.flatMap { topic =>
      topic.messages.toList.map { mao =>
        val payload =  mao.message.payload

        //ugliest part of the code. Thanks to kafka
        val data = Array.fill[Byte](payload.limit)(0)
        payload.get(data)
        new String(data)
      }
    }
  }
}
