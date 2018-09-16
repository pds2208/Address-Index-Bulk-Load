package app.producer.impl

import app.producer.{AddressLine, SendMessage}
import com.typesafe.scalalogging.Logger
import org.json4s._
import org.json4s.native.Serialization.writePretty
import org.slf4j.LoggerFactory

class SendToRabbit extends SendMessage {

  val logger = Logger(LoggerFactory.getLogger("ToRabbit"))
  case class Address (addresses: List[AddressLine])

  override def send(address: List[AddressLine]): Unit = {

    implicit val formats: DefaultFormats.type = DefaultFormats

    println(writePretty(Address(address)))

  }

}
