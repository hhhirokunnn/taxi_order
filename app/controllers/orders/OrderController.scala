package controllers.orders

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
  val config: Configuration
) extends AbstractController(cc) {

  def create(): Action[OrderRequestParameter] = Action(parse.json[OrderRequestParameter]) { implicit request =>
    NamedDB(Symbol("taxi_order")) localTx { implicit session =>
      new OrderRegistrator(1, request.body).createOrder()
    } match {
      case Right(_) => Ok("")
      case Left(_) => BadRequest("")
    }
  }

  def list(): Action[AnyContent] = Action { implicit request =>
    NamedDB(Symbol("taxi_order")) readOnly { implicit session =>
      new OrderFinder().findAll()
    } match {
      case Right(orders) => Ok(Json.toJson(Results(orders)))
      case Left(_) => BadRequest("")
    }
  }

  def fetchRequesting(): Action[AnyContent] = Action { implicit request =>
    NamedDB(Symbol("taxi_order")) readOnly { implicit session =>
      new OrderFinder().findRequestingOrderBy(1)
    } match {
      case Right(order) => Ok(Json.toJson(order))
      case Left(_) => BadRequest("")
    }
  }

  def orderAccept(order_id: Int): Action[OrderAcceptParameter] = Action(parse.json[OrderAcceptParameter]) { implicit request =>
    NamedDB(Symbol("taxi_order")) localTx { implicit session =>
      new OrderRenewaler(order_id).makeAcceptFrom(request.body)
    } match {
      case Right(_) => Ok("")
      case Left(_) => BadRequest("")
    }
  }

  def orderDispatched(order_id: Int): Action[AnyContent] = Action { implicit request =>
    NamedDB(Symbol("taxi_order")) localTx { implicit session =>
      new OrderRenewaler(order_id).makeDispatched(1)
    } match {
      case Right(_) => Ok("")
      case Left(_) => BadRequest("")
    }
  }

  def orderCompleted(order_id: Int): Action[AnyContent] = Action { implicit request =>
    NamedDB(Symbol("taxi_order")) localTx { implicit session =>
      new OrderRenewaler(order_id).makeCompleted(1)
    } match {
      case Right(_) => Ok("")
      case Left(_) => BadRequest("")
    }
  }
}
