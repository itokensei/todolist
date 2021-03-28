package models

case class Task(id: Long, label: String)

object Task {

  import anorm._
  import anorm.SqlParser._

  val task = {
    get[Long]("id") ~
      get[String]("label") map { case id ~ label =>
        Task(id, label)
      }
  }

  import play.api.db._
  import play.api.Play.current

  def all(): List[Task] = db.withConnection { implicit c =>
    SQL("select * from task").as(task *)
  }

  def create(label: String) {}

  def delete(id: Long) {}

}
