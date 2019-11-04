package models.orders

import play.api.libs.json.{Json, Writes}

case class Order(
  order_id: Int,
  dispatch_point: String,
  order_status: OrderStatus,
  ordered_at: String,
  estimated_dispatched_at: Option[String],
  dispatched_at: Option[String],
  updated_at: String,
)

object Order {
  implicit val writes: Writes[Order] = Json.writes[Order]
}
