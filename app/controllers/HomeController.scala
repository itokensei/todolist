package controllers

import models.Task
import play.api.data.Form
import play.api.data.Forms._
import play.api.db.Database
import play.api.mvc._

import java.sql.Connection
import javax.inject._


/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(db:Database,val controllerComponents: ControllerComponents) extends BaseController with play.api.i18n.I18nSupport{

  def index() = Action { implicit request: Request[AnyContent] =>
    Redirect(routes.HomeController.tasks())
  }

  val taskForm: Form[String] = Form(
    "label" -> nonEmptyText
  )
  def tasks = Action{ implicit request =>
    Ok(views.html.index(db.withConnection { implicit c => Task.all},taskForm))
  }
  def newTask = Action { implicit request =>
    db.withConnection { implicit c: Connection =>
      taskForm.bindFromRequest().fold(
        errors => BadRequest(views.html.index(Task.all, errors)),
        (label: String) => {
          Task.create(label)
          Redirect(routes.HomeController.tasks)
        }
      )
    }
  }
  def deleteTask(id : Long): Action[AnyContent] = Action{
    db.withConnection{ implicit c =>
      Task.delete(id)
      Redirect(routes.HomeController.tasks)
    }
  }
}