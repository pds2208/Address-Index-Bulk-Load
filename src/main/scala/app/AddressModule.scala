package app

import app.producer.{FileArrived, SendMessage}
import app.producer.impl.{CSVFileArrived, SendToRabbit}
import app.validation.Validation
import app.validation.impl.CSVValidation
import com.google.inject.AbstractModule

class AddressModule extends AbstractModule {

  override protected def configure(): Unit = {

    bind(classOf[SendMessage]).to(classOf[SendToRabbit]).asEagerSingleton()
    bind(classOf[FileArrived]).to(classOf[CSVFileArrived]).asEagerSingleton()
    bind(classOf[Validation]).to(classOf[CSVValidation]).asEagerSingleton()

  }
}
