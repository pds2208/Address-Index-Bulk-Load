import play.api._
import play.api.ApplicationLoader.Context
import play.api.routing.Router
import play.filters.HttpFiltersComponents

class RestApplicationLoader extends ApplicationLoader {
  def load(context: Context): Application = {
    new MyComponents(context).application
  }
}

class MyComponents(context: Context)  extends BuiltInComponentsFromContext(context) with HttpFiltersComponents {
  lazy val router: Router = Router.empty // fix this
}
