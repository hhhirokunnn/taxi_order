package repositories.internal

import scalikejdbc.interpolation.SQLSyntax
import scalikejdbc.interpolation.Implicits._

/**
 * SQLiteの時刻の扱いを共通化するためのオブジェクト
 */
object SqlDateTime {
  def now: SQLSyntax = sqls"DATETIME('now', '+9 hours')"
}
