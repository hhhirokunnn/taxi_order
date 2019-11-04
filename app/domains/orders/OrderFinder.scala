package domains.orders

import models.orders.{Order, OrderStatus}
import repositories.orders.{OrderRecord, OrderSelector}
import scalikejdbc.DBSession

import scala.util.Try

class OrderFinder(implicit session: DBSession) {

  def findBy(order_id: Int): Either[OrderFindError, Option[Order]] =
    Try {
      new OrderSelector().selectRequestingOrderBy(order_id).map(toResponse)
    }.toEither.left.map(new UnexpectedOrderFindError(_))

  def findRequestedOrderBy(order_id: Int): Either[OrderFindError, Option[Order]] =
    Try {
      new OrderSelector().selectRequestedOrderBy(order_id).map(toResponse)
    }.toEither.left.map(new UnexpectedOrderFindError(_))

  def findAcceptedOrderBy(order_id: Int): Either[OrderFindError, Option[Order]] =
    Try {
      new OrderSelector().selectAcceptedOrderBy(order_id).map(toResponse)
    }.toEither.left.map(new UnexpectedOrderFindError(_))

  def findDispatchedOrderBy(order_id: Int): Either[OrderFindError, Option[Order]] =
    Try {
      new OrderSelector().selectDispatchedOrderBy(order_id).map(toResponse)
    }.toEither.left.map(new UnexpectedOrderFindError(_))

  def findCompletedOrderBy(order_id: Int): Either[OrderFindError, Option[Order]] =
    Try {
      new OrderSelector().selectRequestingOrderBy(order_id).map(toResponse)
    }.toEither.left.map(new UnexpectedOrderFindError(_))

  def findRequestingOrderBy(passenger_id: Int): Either[OrderFindError, Option[Order]] =
    Try {
      new OrderSelector().selectRequestingOrderBy(passenger_id).map(toResponse)
    }.toEither.left.map(new UnexpectedOrderFindError(_))


  def findAll(): Either[OrderFindError, Seq[Order]] = Try {
    new OrderSelector().selectAll().map(toResponse)
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
