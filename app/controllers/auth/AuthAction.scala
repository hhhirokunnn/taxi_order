package controllers.auth

import javax.inject._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

/**
 * ログインしたユーザの認証ロジック
 * @param parser
 * @param ec
 */
// https://www.playframework.com/documentation/2.7.x/ScalaActionsComposition#Custom-action-builders
class AuthAction @Inject()(parser: BodyParsers.Default)(implicit ec: ExecutionContext)
  extends ActionBuilderImpl(parser) {
  override def invokeBlock[A](
    request: Request[A],
    block: (Request[A]) => Future[Result]): Future[Result] =
    block(request)

  override def composeAction[A](action: Action[A]): Authenticator[A] =
    Authenticator(action)
}

case class Authenticator[A](action: Action[A]) extends Action[A] {

  def apply(request: Request[A]): Future[Result] =
    request.session
      .get(Authenticator.SessionTokenId)
      .fold(Future.successful(Results.Unauthorized(""))) { _ =>
        action(request)
      }

  override def parser: BodyParser[A] = action.parser

  override def executionContext: ExecutionContext = action.executionContext
}

object Authenticator {
  val SessionTokenId: String = "UserId"
}

