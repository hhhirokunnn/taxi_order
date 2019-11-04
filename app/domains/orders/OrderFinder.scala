package domains.orders

import models.orders.Order
import repositories.orders.{OrderRecord, OrderSelector}
import scalikejdbc.DBSession

import scala.util.Try

class OrderFinder(implicit session: DBSession) {

  def findAll(): Either[OrderFindError, Seq[Order]] = Try {
    new OrderSelector().selectAll().map(toResponse)
  }.toEither.left.map(new UnexpectedOrderFindError(_))

  def findBy(order_id: Int): Either[OrderFindError, Option[Order]] = {
    val selector = new OrderSelector()
    find(selector.selectRequestingOrderBy, order_id)
  }

  def findRequestedOrderBy(order_id: Int): Either[OrderFindError, Option[Order]] = {
    val selector = new OrderSelector()
    find(selector.selectRequestedOrderBy, order_id)
  }

  def findAcceptedOrderBy(order_id: Int): Either[OrderFindError, Option[Order]] = {
    val selector = new OrderSelector()
    find(selector.selectAcceptedOrderBy, order_id)
  }

  def findDispatchedOrderBy(order_id: Int): Either[OrderFindError, Option[Order]] = {
    val selector = new OrderSelector()
    find(selector.selectDispatchedOrderBy, order_id)
  }

  def findCompletedOrderBy(order_id: Int): Either[OrderFindError, Option[Order]] = {
    val selector = new OrderSelector()
    find(selector.selectRequestedOrderBy, order_id)
  }

  def findRequestingOrderBy(passenger_id: Int): Either[OrderFindError, Option[Order]] = {
    val selector = new OrderSelector()
    find(selector.selectRequestingOrderBy, passenger_id)
  }

  private def find(select: Int => Option[OrderRecord], id: Int) = Try {
    select(id).map(toResponse)
  }.toEither.left.map(new UnexpectedOrderFindError(_))

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
