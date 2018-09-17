package modules

import com.google.inject.AbstractModule
import producer.{FileArrived, SendMessage}
import producer.impl.{CSVFileArrived, SendToRabbit}
import validation.Validation
import validation.impl.CSVValidation

class AddressModule extends AbstractModule {

  override protected def configure(): Unit = {

    bind(classOf[SendMessage]).to(classOf[SendToRabbit]).asEagerSingleton()
    bind(classOf[FileArrived]).to(classOf[CSVFileArrived]).asEagerSingleton()
    bind(classOf[Validation]).to(classOf[CSVValidation]).asEagerSingleton()

  }
}
