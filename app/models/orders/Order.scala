package models.orders

import play.api.libs.json.{Json, Writes}

/**
 * 注文のレスポンス用のクラス
 * @param order_id 注文ID
 * @param dispatch_point 迎車地点
 * @param order_status 注文状態
 * @param ordered_at 注文時間
 * @param estimated_dispatched_at 到着予想時間
 * @param dispatched_at 到着時間
 * @param updated_at 注文状態更新時間(楽観ロック用)
 */
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
