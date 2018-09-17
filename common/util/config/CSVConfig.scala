package util.config

import com.typesafe.config.ConfigFactory

case class CSVConfig()  {

  def conf : Config = ConfigFactory.load()

  val directoryPath: String = conf.getString("bulk.csv.directoryPath")

}
