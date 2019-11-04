package repositories.orders

import models.orders.OrderStatus
import repositories.core.TaxiOrderSyntaxSupport
import scalikejdbc._

case class OrderRecord(
  id: Int,
  user_id: Int,
  dispatch_point: String,
  order_status: OrderStatus,
  ordered_at: String,
  estimated_dispatched_at: Option[String],
  dispatched_at: Option[String],
  completed_at: Option[String],
  created_at: String,
  updated_at: String,
)

object OrderRecord extends TaxiOrderSyntaxSupport[OrderRecord]("orders") {

  val o: SyntaxProvider[OrderRecord] = OrderRecord.syntax("o")

  val * : WrappedResultSet => OrderRecord = set =>
    OrderRecord(
      id = set.int(o.resultName.id),
      user_id = set.int(o.resultName.user_id),
      dispatch_point = set.string(o.resultName.dispatch_point),
      order_status = OrderStatus.fromString(set.string(o.resultName.order_status)),
      ordered_at = set.string(o.resultName.ordered_at),
      estimated_dispatched_at = set.stringOpt(o.resultName.estimated_dispatched_at),
      dispatched_at = set.stringOpt(o.resultName.dispatched_at),
      completed_at = set.stringOpt(o.resultName.completed_at),
      created_at = set.string(o.resultName.created_at),
      updated_at = set.string(o.resultName.updated_at),
    )
}