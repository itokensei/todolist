package controllers

import play.api.db.Database
import play.api.mvc._
import javax.inject._


/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(db:Database,val controllerComponents: ControllerComponents) extends BaseController with play.api.i18n.I18nSupport{
  def index() = Action { implicit request =>
    Ok(views.html.index("hello world!"))
  }
}