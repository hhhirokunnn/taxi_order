package models.orders

import play.api.libs.json.{Format, Json}

case class OrderRequestParameter(
  dispatch_point: String,
)

object OrderRequestParameter {

  implicit val format: Format[OrderRequestParameter] = Json.format[OrderRequestParameter]
}
