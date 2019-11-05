package domains.orders

import models.orders.{OrderRequestParameter, OrderStatus}
import repositories.orders.{OrderInserter, OrderRequestFragment, OrderSelector}
import scalikejdbc.DBSession

import scala.util.Try

class OrderRegistrator(passenger_id: Int, parameter: OrderRequestParameter)(implicit session: DBSession) {

  def register(): Either[OrderRegisterError, Unit] =
    ensureRequestedOrder() match {
      case Some(_) => Left(new OverLimitRequestedOrderError(passenger_id))
      case _ => insert()
    }

  private def insert() = Try {
    OrderInserter.insertFrom(toFragment)
  }.toEither.left.map(new UnexpectedOrderRegisterError(_))

  private def ensurePassenger() = {
    new OrderSelector().selectRequestingOrderBy(passenger_id)
  }

  private def ensureRequestedOrder() = {
    new OrderSelector().selectRequestingOrderBy(passenger_id)
  }

  private def toFragment = {
    OrderRequestFragment(
      passenger_id = passenger_id,
      dispatch_point = parameter.dispatch_point,
      order_status = OrderStatus.Requested
    )
  }
}
