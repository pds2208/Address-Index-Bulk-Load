package app.util

import java.io.IOException
import java.nio.file._

import app.validation.Validation
import app.configuration.{DirectoryPath, FileArrived}
import com.google.inject.Inject
import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory

import scala.collection.JavaConverters._
import scala.util.control.Breaks._

class DirectoryWatcher @Inject()(path: DirectoryPath,
                                 fileArrivedCallback: FileArrived) {

  val logger = Logger(LoggerFactory.getLogger("DirectoryWatcher"))

  def scanDirectory(): Unit = {
    logger.info("Starting directory watcher ...")

    try {
      val watchService = path.directory.getFileSystem.newWatchService
      path.directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
        StandardWatchEventKinds.ENTRY_MODIFY)

      breakable {
        while (true) {
          val watchKey = watchService.take()

          watchKey.pollEvents().asScala.foreach(e => {
            if (e.kind.equals(StandardWatchEventKinds.OVERFLOW)) breakable { break }
            dispatchEvent(e, fileArrivedCallback.callback)
          })

          if (!watchKey.reset()) {
            logger.info("DirectoryWatcher is shutting down")
            watchKey.cancel()
            watchService.close()
            break
          }
        }
      }
    } catch {
      case ie: InterruptedException => logger.warn(s"InterruptedException: $ie")
      case ioe: IOException => logger.warn(s"IOException: $ioe")
      case e: Exception => logger.warn(s"Exception: $e")
    }
  }

  def dispatchEvent(event: WatchEvent[_], callback: Path => Unit) : Unit = {
    val kind = event.kind
    val event_path: Path = event.context().asInstanceOf[Path]

    logger.info(s"File: [$event_path] received.")

    if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {
      callback(event_path)
    }

    if (kind.equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
      logger.info("Hmm, file mofified. Should this happen?")
      callback(event_path)
    }

  }
}
