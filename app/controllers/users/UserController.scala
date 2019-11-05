package controllers.users

import controllers.auth.{AuthAction, Authenticator}
import domains.users.{UserFinder, UserRegistrator}
import javax.inject.{Inject, Singleton}
import models.users.{UserLoginParameter, UserRegisterParameter}
import play.api.Configuration
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request}
import scalikejdbc.NamedDB
import scalikejdbc.config.DBs

@Singleton
class UserController @Inject()(
  cc: ControllerComponents,
  authAction: AuthAction,
  val config: Configuration
) extends AbstractController(cc) {

  def register(): Action[UserRegisterParameter] = Action(parse.json[UserRegisterParameter]) { implicit request =>
    NamedDB(Symbol("taxi_order")) autoCommit { implicit session =>
      new UserRegistrator(request.body).register()
    } match {
      case Right(_) => Ok("")
      case Left(_) => BadRequest("")
    }
  }

  def login(): Action[UserLoginParameter] = Action(parse.json[UserLoginParameter]) { implicit request =>
    NamedDB(Symbol("taxi_order")) autoCommit { implicit session =>
      new UserFinder().findBy(request.body)
    } match {
      case Right(Some(user)) => Ok("").withSession(
        Authenticator.SessionTokenId -> user.id.toString
      ).addingToSession(
        Authenticator.SessionTokenType -> user.member_type.label
      )
      case _ => BadRequest("")
    }
  }

  def logout(): Action[AnyContent] = authAction { implicit request: Request[AnyContent] =>
    Ok("").withNewSession
  }
}