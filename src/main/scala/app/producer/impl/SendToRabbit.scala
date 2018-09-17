package app.producer.impl

import app.producer.{AddressLine, SendMessage}
import app.util.RabbitConnection
import com.google.inject.Inject
import com.typesafe.scalalogging.Logger
import org.json4s._
import org.json4s.native.Serialization.write
import org.slf4j.LoggerFactory

class SendToRabbit@Inject()(rabbit: RabbitConnection) extends SendMessage {

  val logger = Logger(LoggerFactory.getLogger("ToRabbit"))
  case class Address (addresses: List[AddressLine])

  override def send(address: List[AddressLine]): Unit = {

    implicit val formats: DefaultFormats.type = DefaultFormats

    rabbit.publish(write(Address(address)))
    logger.info("Message sent")

  }

}
