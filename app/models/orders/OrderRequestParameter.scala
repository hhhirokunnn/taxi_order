package models.orders

import play.api.libs.json.{Json, Writes}

case class OrderRequestParameter(
  dispatch_point: String,
)

object OrderRequestParameter {

  implicit val writes: Writes[OrderRequestParameter] = Json.writes[OrderRequestParameter]
}
