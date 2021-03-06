package util.config

import com.typesafe.config.ConfigFactory

case class RabbitConfig() {

  def conf: Config = ConfigFactory.load()
  val userName: String = conf.getString("bulk.csv.rabbit.userName")
  val password: String = conf.getString("bulk.csv.rabbit.password")
  val virtualHost: String = conf.getString("bulk.csv.rabbit.virtualHost")
  val hostName: String = conf.getString("bulk.csv.rabbit.hostName")
  val port: Int = conf.getInt("bulk.csv.rabbit.port")

  val exchangeName: String = conf.getString("bulk.csv.rabbit.exchangeName")
  val queueName: String = conf.getString("bulk.csv.rabbit.queueName")
  val routingKey: String = conf.getString("bulk.csv.rabbit.routingKey")
}
