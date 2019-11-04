package models.orders

import play.api.libs.json.{Format, Json}

case class OrderAcceptParameter(
  crew_id: Int,
  estimated_dispatched_at: String,
  updated_at: String,
)

object OrderAcceptParameter {
  implicit val format: Format[OrderAcceptParameter] = Json.format[OrderAcceptParameter]
}
