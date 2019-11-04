package repositories.core

import scalikejdbc.SQLSyntaxSupport

/**
 * ScalikeJDBCの諸設定を共通化するためのクラス
 */
class TaxiOrderSyntaxSupport[A](table: String)
  extends SQLSyntaxSupport[A] {
  override val useSnakeCaseColumnName = false
  override val connectionPoolName = Symbol("taxi_order")
  override val tableName = table

  type Syntax = scalikejdbc.SyntaxProvider[A]
}