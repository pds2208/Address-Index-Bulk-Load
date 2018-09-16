package app.configuration.impl

import java.nio.file.{FileSystems, Path}

import app.configuration.DirectoryPath

class CSVDirectoryPath extends DirectoryPath {
  override def directory: Path = {
    FileSystems.getDefault.getPath("/tmp")
  }
}