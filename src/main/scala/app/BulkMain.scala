package app

import app.util.DirectoryWatcher
import com.google.inject.Guice
import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory

object BulkMain extends App {

  val logger = Logger(LoggerFactory.getLogger("BulkMain"))

  logger.info("Starting up")
  val injector = Guice.createInjector(new AddressModule)
  val app: DirectoryWatcher = injector.getInstance(classOf[DirectoryWatcher])
  app.scanDirectory()

}
