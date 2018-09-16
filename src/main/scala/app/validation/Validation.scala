package app.validation

import java.nio.file.Path

trait Validation {
  def fileArrived(file: Path): Unit
}
