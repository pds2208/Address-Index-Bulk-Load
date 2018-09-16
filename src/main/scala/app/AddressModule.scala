package app

import app.configuration.impl.{CSVDirectoryPath, CSVFileArrived}
import app.configuration.{DirectoryPath, FileArrived}
import app.producer.SendMessage
import app.producer.impl.SendToRabbit
import app.validation.Validation
import app.validation.impl.CSVValidation
import com.google.inject.AbstractModule

class AddressModule extends AbstractModule {

  override protected def configure(): Unit = {

    bind(classOf[SendMessage]).to(classOf[SendToRabbit]).asEagerSingleton()
    bind(classOf[FileArrived]).to(classOf[CSVFileArrived]).asEagerSingleton()
    bind(classOf[DirectoryPath]).to(classOf[CSVDirectoryPath]).asEagerSingleton()
    bind(classOf[Validation]).to(classOf[CSVValidation]).asEagerSingleton()

  }
}