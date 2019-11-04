package models.orders

import play.api.libs.json.{Json, Writes}

case class OrderAcceptParameter(
  crew_id: Int,
  estimated_dispatched_at: String,
  updated_at: String,
)

object OrderAcceptParameter {
  implicit val writes: Writes[OrderAcceptParameter] = Json.writes[OrderAcceptParameter]
}
