package util

import com.rabbitmq.client.{Channel, Connection, ConnectionFactory}
import com.google.inject.Inject
import config.BulkConfig

case class RabbitConnection @Inject()(config: BulkConfig) {

  val userName: String = config.userName
  val password: String = config.password
  val virtualHost: String = config.virtualHost
  val hostName: String = config.hostName
  val port: Int = config.port
  val exchangeName: String = config.exchangeName
  val queueName: String = config.queueName
  val routingKey: String = config.routingKey

  private def connection : Connection = {

    val factory = new ConnectionFactory
    factory.setUsername(userName)
    factory.setPassword(password)
    factory.setVirtualHost(virtualHost)
    factory.setHost(hostName)
    factory.setPort(port)

    factory.newConnection
  }

  def channel: Channel = {
    val channel = connection.createChannel()

    channel.exchangeDeclare(exchangeName, "direct", true)
    channel.queueDeclare(queueName, true, false, false, null)
    channel.queueBind(queueName, exchangeName, routingKey)
    channel
  }

  def publish(message: String) : Unit = {
    val messageBodyBytes = message.getBytes
    channel.basicPublish(exchangeName, routingKey, null, messageBodyBytes)
  }


}
