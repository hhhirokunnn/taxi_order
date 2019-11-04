package controllers.orders

import domains.orders.{OrderFinder, OrderRegistrator}
import javax.inject.{Inject, Singleton}
import models.Results
import models.orders.OrderRequestParameter
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

  def fetchRequested(): Action[AnyContent] = Action { implicit request =>
    NamedDB(Symbol("taxi_order")) readOnly { implicit session =>
      new OrderFinder().findRequestedOrder(1)
    } match {
      case Right(order) => Ok(Json.toJson(order))
      case Left(_) => BadRequest("")
    }
  }
}
