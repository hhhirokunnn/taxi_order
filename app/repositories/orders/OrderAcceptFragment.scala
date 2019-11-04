package repositories.orders

import scalikejdbc.ParameterBinder
import scalikejdbc.interpolation.SQLSyntax
import OrderRecord.column

case class OrderAcceptFragment(
  crew_id: Int,
  estimated_dispatched_at: String,
) {

  def toMap: Map[SQLSyntax, ParameterBinder] =
    Map(
      column.crew_id -> crew_id,
      column.estimated_dispatched_at -> estimated_dispatched_at,
    )
}
