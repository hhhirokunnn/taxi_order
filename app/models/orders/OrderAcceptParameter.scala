package models.orders

import play.api.libs.json.{Format, Json}

/**
 * 注文に配車回答するためのリクエストパラメータ用のクラス
 * @param estimated_dispatched_at 到着予想時間
 * @param updated_at 注文状態の更新時間(楽観ロック用)
 */
case class OrderAcceptParameter(
  estimated_dispatched_at: String,
  updated_at: String,
)

object OrderAcceptParameter {
  implicit val format: Format[OrderAcceptParameter] = Json.format[OrderAcceptParameter]
}
