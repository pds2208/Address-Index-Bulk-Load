package app.producer.impl

import java.nio.file.Path

import app.producer.FileArrived
import app.validation.Validation
import com.google.inject.Inject

class CSVFileArrived @Inject() (validation: Validation) extends FileArrived {
  override def callback (path: Path): Unit = {
    validation.fileArrived(path)
  }
}
