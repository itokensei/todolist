package controllers

import play.api.db.Database
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import javax.inject._
import java.sql.Connection

import models.Task

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject() (db: Database, val controllerComponents: ControllerComponents)
  extends BaseController with play.api.i18n.I18nSupport {

  def index(): Action[AnyContent] = Action {
    Redirect(routes.HomeController.tasks())
  }

  def tasks(): Action[AnyContent] = Action { implicit request =>
    Ok(views.html.index(db.withConnection(implicit c => Task.all), taskForm))
  }

  def newTask(): Action[AnyContent] = Action { implicit request =>
    db.withConnection { implicit connection: Connection =>
      taskForm.bindFromRequest.fold(
        errors => BadRequest(views.html.index(Task.all, errors)),
        label => {
          Task.create(label)
          Redirect(routes.HomeController.tasks())
        }
      )
    }
  }

  def deleteTask(id: Long): Action[AnyContent] = Action { implicit request =>
    db.withConnection { implicit connection: Connection =>
      Task.delete(id)
      Redirect(routes.HomeController.tasks())
    }
  }

  val taskForm: Form[String] = Form(
    "label" -> nonEmptyText
  )
}
