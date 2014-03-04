package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._

import models.Task

object Application extends Controller {

  def index = Action {
    //Ok(views.html.index("Your new application is ready."))
    //Ok("Hello world")
    Redirect(routes.Application.tasks)
  }
  /*
  - this method returns an Action that will handle the request
  - an Action must return a Result that represents the HTTP response to send back
  - 200 OK response in this case, with html content
  - Play templates are compiled to standard Scala functions
  */

  /*
  A Form object encapsulates an HTML form definition, including validation constraints.
  */
  val taskForm = Form(
  	"label" -> nonEmptyText
  )


  def tasks = Action {
  	Ok(views.html.index(Task.all(), taskForm))
  }

  def newTask = Action { implicit request =>
  	taskForm.bindFromRequest.fold(
  		errors => BadRequest(views.html.index(Task.all(), errors)),
  		label => {
  			Task.create(label)
  			Redirect(routes.Application.tasks)
  		}
  	)
  }

  def deleteTask(id: Long) = Action {
  	Task.delete(id)
  	Redirect(routes.Application.tasks)
  }

}