package models

import play.api.libs.json.{JsPath, Writes}

/**
 * 配列のレスポンス用のクラス
 * @param results 配列のキー
 * @tparam A
 */
case class Results[A](
  results: Seq[A]
)

object Results {
  implicit def resultsWrites[A: Writes]: Writes[Results[A]] = {
    (JsPath \ "results").write[Seq[A]].contramap(_.results)
  }
}
