package controllers

import com.typesafe.scalalogging.Logger
import javax.inject._
import org.slf4j.LoggerFactory
import play.api.mvc._

@Singleton
class BatchController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  val logger = Logger(LoggerFactory.getLogger("BatchController"))

  /**
    * a POST route which will process all `BulkQuery` items in the `BulkBody`
    *
    * @return reduced information on found addresses (uprn, formatted address)
    */
  def bulk(limitperaddress: Option[String],
           startDate: Option[String] = None, endDate: Option[String] = None,
           historical: Option[String] = None, matchthreshold: Option[String] = None
          ) = Action { request: Request[AnyContent] =>

    //logger.info(s"#bulkQuery with ${request.body.addresses.size} items")
    Ok("Successfully sent request")
  }

  /**
    * a POST route which will process all `BulkQuery` items in the `BulkBody`
    * this version is slower and more memory-consuming
    *
    * @return all the information on found addresses (uprn, formatted address, found address json object)
    */
  def bulkFull(limitperaddress: Option[String],
               startDate: Option[String] = None, endDate: Option[String] = None,
               historical: Option[String] = None, matchthreshold: Option[String] = None
              ) = Action { request: Request[AnyContent] =>

    //logger.info(s"#bulkFullQuery with ${request.body.addresses.size} items")
    Ok("Successfully sent request")

  }

}

