package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Form
import play.api.data.Forms._
import models.Task

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController with play.api.i18n.I18nSupport{

  def index() = Action { implicit request: Request[AnyContent] =>
    Redirect(routes.HomeController.tasks())
  }

  val taskForm = Form(
    "label" -> nonEmptyText
  )
  def tasks = Action{
    Ok(views.html.index(Task.all(),taskForm))
  }
  def newTask = TODO
  def deleteTask(id : Long) = TODO
}
