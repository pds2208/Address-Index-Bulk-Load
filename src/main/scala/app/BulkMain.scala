package app

import app.util.DirectoryWatcher
import com.google.inject.Guice
import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory

class BulkMain {
}

object BulkMain {

  val logger = Logger(LoggerFactory.getLogger("BulkMain"))

  def main(args: Array[String]): Unit = {
    logger.info("Starting up")
    val injector = Guice.createInjector(new AddressModule)
    val app : DirectoryWatcher = injector.getInstance(classOf[DirectoryWatcher])
    app.scanDirectory
  }

}
