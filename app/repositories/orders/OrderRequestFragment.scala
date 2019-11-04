package repositories.orders

import models.orders.OrderStatus
import repositories.users.UserRecord.column
import scalikejdbc.ParameterBinder
import scalikejdbc.interpolation.SQLSyntax

case class OrderRequestFragment(
  passenger_id: Int,
  dispatch_point: String,
  order_status: OrderStatus,
  ordered_at: String,
) {

  def toMap: Map[SQLSyntax, ParameterBinder] =
    Map(
      column.passenger_id -> passenger_id,
      column.dispatch_point -> dispatch_point,
      column.order_status -> order_status,
      column.ordered_at -> ordered_at,
    )
}
