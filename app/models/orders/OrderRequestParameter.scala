package models.orders

import play.api.libs.json.{Format, Json}

/**
 * 注文するためのリクエストパラメータ用のクラス
 * @param dispatch_point 迎車地点
 */
case class OrderRequestParameter(
  dispatch_point: String,
)

object OrderRequestParameter {

  implicit val format: Format[OrderRequestParameter] = Json.format[OrderRequestParameter]
}
