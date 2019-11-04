package domains.orders

import models.orders.{Order, OrderStatus}
import repositories.orders.{OrderRecord, OrderSelector}
import scalikejdbc.DBSession

import scala.util.Try

class OrderFinder(implicit session: DBSession) {

  def findRequestedOrder(passenger_id: Int): Either[OrderFinderError, Option[Order]] =
    Try {
      new OrderSelector().selectRequestedOrderBy(passenger_id).map(toResponse)
    }.toEither.left.map(new UnexpectedOrderFinderErrorError(_))

  def findAll(): Either[OrderFinderError, Seq[Order]] = Try {
    new OrderSelector().selectAll().map(toResponse)
  }.toEither.left.map(new UnexpectedOrderFinderErrorError(_))

  private def toResponse(record: OrderRecord): Order = {
    Order(
      order_id = record.id,
      dispatch_point = record.dispatch_point,
      order_status = record.order_status,
      ordered_at = record.ordered_at,
      estimated_dispatched_at = record.estimated_dispatched_at,
      dispatched_at = record.dispatched_at,
      updated_at = record.updated_at,
    )
  }
}
