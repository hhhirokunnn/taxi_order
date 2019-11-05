package controllers.auth

import javax.inject._
import play.api.mvc._
import repositories.users.UserSelector
import scalikejdbc.NamedDB

import scala.concurrent.{ExecutionContext, Future}

/**
 * ログインしたユーザ(運転手)の認証ロジック
 * @param parser
 * @param ec
 */
class CrewAuthAction @Inject()(parser: BodyParsers.Default)(implicit ec: ExecutionContext)
  extends ActionBuilderImpl(parser) {
  override def invokeBlock[A](
    request: Request[A],
    block: (Request[A]) => Future[Result]): Future[Result] = {
    block(request)
  }

  override def composeAction[A](action: Action[A]) = CrewAuthenticator(action)
}

case class CrewAuthenticator[A](action: Action[A]) extends Action[A] {

  def apply(request: Request[A]): Future[Result] = {
    val memberId: Option[String] = request.session.get(Authenticator.SessionTokenId)

    memberId.flatMap { id =>
      NamedDB(Symbol("taxi_order")) readOnly { implicit session =>
        UserSelector.selectCrewBy(id.toInt)
      }
    }.fold(Future.successful(Results.Unauthorized("")))(_ => action(request))
  }

  override def parser: BodyParser[A] = action.parser

  override def executionContext: ExecutionContext = action.executionContext
}
