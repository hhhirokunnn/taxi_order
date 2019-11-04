package controllers.users

import domains.users.UserRegistrator
import javax.inject.{Inject, Singleton}
import models.users.{UserLoginParameter, UserRegisterParameter}
import play.api.Configuration
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request}
import scalikejdbc.NamedDB

@Singleton
class UserController @Inject()(
  cc: ControllerComponents,
  val config: Configuration
) extends AbstractController(cc) {

  def register(): Action[UserRegisterParameter] = Action(parse.json[UserRegisterParameter]) { implicit request =>
    NamedDB(Symbol("taxi_order")) localTx { implicit session =>
      new UserRegistrator(request.body).register()
    } match {
      case Right(_) => Ok("")
      case Left(_) => BadRequest("")
    }
  }

  def login(): Action[UserLoginParameter] = Action(parse.json[UserLoginParameter]) { implicit request =>
    Ok("HELLO")
  }

  def logout(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok("HELLO")
  }
}