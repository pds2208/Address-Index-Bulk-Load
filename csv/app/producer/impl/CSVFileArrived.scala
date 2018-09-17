package producer.impl

import java.nio.file.Path

import producer.FileArrived
import validation.Validation
import com.google.inject.Inject

class CSVFileArrived @Inject() (validation: Validation) extends FileArrived {
  override def callback (path: Path): Unit = {
    validation.fileArrived(path)
  }
}
