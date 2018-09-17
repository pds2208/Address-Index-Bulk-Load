package validation.impl

import java.nio.file.{Path, Paths}

import com.google.inject.Inject
import com.typesafe.scalalogging.Logger
import config.BulkConfig
import org.slf4j.LoggerFactory
import producer.{AddressLine, SendMessage}
import validation.Validation

import scala.io.Source

class  CSVValidation @Inject()(config: BulkConfig, rabbit: SendMessage) extends Validation {

  val logger = Logger(LoggerFactory.getLogger("CSV-Validation"))

  override def fileArrived(file: Path): Unit = {
    val s = Paths.get(config.directoryPath, file.toString).toString
    parseFile(s) match {
      case Some(items) => rabbit.send(items)
      case None => logger.warn(s"File: $s is invalid")
    }

  }

  /**
    * Validate the input file. We expect a CSV file with an ID, Address as the first line followed by values
    *
    * @param file the name of the file to validate
    * @return a List containing the parsed items in AddressLine format - empty if validation failed
    */
  def parseFile(file: String): Option[List[AddressLine]] = {

    val items : List[String] = Source.fromFile(file).getLines.toList

    try {

      // Check first line and then skip it
      val v = items.headOption

      v match {
        case Some(_) =>
          val Array(id, addr) = items.head.split(",").map(_.trim)
          if (id == "ID" && addr == "ADDRESS") {
            val addressItems: List[AddressLine] = items.drop(1).map(a => {
              val Array(i, add) = a.split(",").map(_.trim)
              AddressLine(i, add)
            })
            Some(addressItems)
          } else None

        case None =>
          None

      }
    } catch{
      case _: Exception => None
    }

  }
}
