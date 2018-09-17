package producer

import java.nio.file.Path

trait FileArrived {
  def callback (path: Path) : Unit
}
