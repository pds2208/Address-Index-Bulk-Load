package util

import java.io.IOException
import java.nio.file._

import com.google.inject.Inject
import com.typesafe.scalalogging.Logger
import config.BulkConfig
import org.slf4j.LoggerFactory
import producer.FileArrived

import scala.collection.JavaConverters._
import scala.util.control.Breaks._

class DirectoryWatcher @Inject()(config: BulkConfig, fileArrivedCallback: FileArrived) {

  val logger = Logger(LoggerFactory.getLogger("DirectoryWatcher"))

  import java.nio.file.FileSystems
  import java.nio.file.Path

  val path: Path = FileSystems.getDefault.getPath(config.directoryPath)

  def scanDirectory(): Unit = {
    logger.info(s"Starting directory watcher on directory $path ...")

    try {
      val watchService = path.getFileSystem.newWatchService
      path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
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
