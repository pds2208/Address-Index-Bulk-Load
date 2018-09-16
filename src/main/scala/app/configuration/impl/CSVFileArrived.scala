package app.configuration.impl

import java.nio.file.Path

import app.configuration.FileArrived
import app.validation.Validation
import com.google.inject.Inject

class CSVFileArrived @Inject() (validation: Validation) extends FileArrived {
  override def callback (path: Path): Unit = {
    validation.fileArrived(path)
  }
}