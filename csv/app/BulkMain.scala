
import com.google.inject.Guice
import com.typesafe.scalalogging.Logger
import modules.AddressModule
import org.slf4j.LoggerFactory
import util.DirectoryWatcher

object BulkMain extends App {

  val logger = Logger(LoggerFactory.getLogger("BulkMain"))

  logger.info("Starting up")
  val injector = Guice.createInjector(new AddressModule)
  val app: DirectoryWatcher = injector.getInstance(classOf[DirectoryWatcher])
  app.scanDirectory()

}
