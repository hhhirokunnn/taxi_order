package repositories.internal

import scalikejdbc.interpolation.SQLSyntax
import scalikejdbc.interpolation.Implicits._

object SqlDateTime {
  def now: SQLSyntax = sqls"DATETIME('now', '+9 hours')"
}
