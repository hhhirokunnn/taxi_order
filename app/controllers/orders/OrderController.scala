package controllers.orders

import controllers.auth.{AuthAction, Authenticator, CrewAuthAction, PassengerAuthAction}
import domains.orders.{OrderFinder, OrderRegistrator, OrderRenewaler}
import javax.inject.{Inject, Singleton}
import models.Results
import models.orders.{OrderAcceptParameter, OrderRequestParameter}
import play.api.Configuration
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import scalikejdbc.NamedDB

@Singleton
class OrderController @Inject()(
  cc: ControllerComponents,
  authAction: AuthAction,
  crewAuthAction: CrewAuthAction,
  passengerAuthAction: PassengerAuthAction,
  val config: Configuration
) extends AbstractController(cc) {

  /**
   * 乗客が注文を行うAPI
   */
  def create(): Action[OrderRequestParameter] = passengerAuthAction(parse.json[OrderRequestParameter]) { implicit request =>
    NamedDB(Symbol("taxi_order")) autoCommit { implicit session =>
      val Some(id) = request.session.get(Authenticator.SessionTokenId).map(_.toInt)
      new OrderRegistrator(id, request.body).register()
    } match {
      case Right(_) => Ok("")
      case Left(_) => BadRequest("")
    }
  }

  /**
   * 運転手が注文を確認するAPI
   */
  def fetchAll(): Action[AnyContent] = crewAuthAction { implicit request =>
    NamedDB(Symbol("taxi_order")) readOnly { implicit session =>
      new OrderFinder().findAll()
    } match {
      case Right(orders) => Ok(Json.toJson(Results(orders)))
      case Left(_) => BadRequest("")
    }
  }

  /**
   * 乗客が自身のactiveな注文を確認するAPI
   */
  def fetchRequesting(): Action[AnyContent] = passengerAuthAction { implicit request =>
    NamedDB(Symbol("taxi_order")) readOnly { implicit session =>
      val Some(id) = request.session.get(Authenticator.SessionTokenId).map(_.toInt)
      new OrderFinder().findRequestingOrderBy(id)
    } match {
      case Right(order) => Ok(Json.toJson(order))
      case Left(_) => BadRequest("")
    }
  }

  /**
   * 運転手が乗客の注文を受け付けるためのAPI
   */
  def updateToAccept(order_id: Int): Action[OrderAcceptParameter] = crewAuthAction(parse.json[OrderAcceptParameter]) { implicit request =>
    NamedDB(Symbol("taxi_order")) autoCommit { implicit session =>
      val Some(id) = request.session.get(Authenticator.SessionTokenId).map(_.toInt)
      new OrderRenewaler(order_id).makeAcceptFrom(request.body, id)
    } match {
      case Right(_) => Ok("")
      case Left(_) => BadRequest("")
    }
  }

  /**
   * 運転手が到着したことを報告するAPI
   */
  def updateToDispatched(order_id: Int): Action[AnyContent] = crewAuthAction { implicit request =>
    NamedDB(Symbol("taxi_order")) autoCommit { implicit session =>
      val Some(id) = request.session.get(Authenticator.SessionTokenId).map(_.toInt)
      new OrderRenewaler(order_id).makeDispatched(id)
    } match {
      case Right(_) => Ok("")
      case Left(_) => BadRequest("")
    }
  }

  /**
   * 運転手が注文が完了したことを報告するAPI
   */
  def updateToCompleted(order_id: Int): Action[AnyContent] = crewAuthAction { implicit request =>
    NamedDB(Symbol("taxi_order")) autoCommit { implicit session =>
      val Some(id) = request.session.get(Authenticator.SessionTokenId).map(_.toInt)
      new OrderRenewaler(order_id).makeCompleted(id)
    } match {
      case Right(_) => Ok("")
      case Left(_) => BadRequest("")
    }
  }
}
