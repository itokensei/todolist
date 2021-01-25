package models
import anorm._
import anorm.SqlParser._
import play.api.db._

import java.sql.Connection
case class Task(id: Long, label: String)

object Task {
  val task = get[Long]("id") ~ get[String]("label") map {
      case id ~ label => Task(id, label)
    }

  def all (implicit connection :Connection) = SQL("select * from task").as(task.*)

  def create(label: String)(implicit c :Connection) =
    SQL("insert into task (label) values ({label})")
      .on('label -> label).executeUpdate()


  def delete(id: Long)(implicit c :Connection) =
    SQL("delete from task where id = {id}")
      .on('id -> id).executeUpdate()

}

