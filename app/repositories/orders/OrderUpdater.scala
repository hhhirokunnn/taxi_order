package repositories.orders

import scalikejdbc.{DBSession, update, withSQL}
import OrderRecord.column
import models.orders.OrderStatus.{Accepted, Completed, Dispatched}
import repositories.internal.SqlDateTime

class OrderUpdater(orderId: Int) {

  def makeAcceptFrom(fragment: OrderAcceptFragment)(implicit session: DBSession): Unit = {
    withSQL {
      update(OrderRecord)
        .set(
          column.crew_id -> fragment.crew_id,
          column.estimated_dispatched_at -> fragment.estimated_dispatched_at,
          column.order_status -> Accepted.label,
        )
        .where
        .eq(column.id, orderId)
    }.update().apply()
  }

  def makeDispatched(crew_id: Int)(implicit session: DBSession): Unit = {
    withSQL {
      update(OrderRecord)
        .set(
          column.dispatched_at -> SqlDateTime.now,
          column.order_status -> Dispatched.label,
        )
        .where
        .eq(column.id, orderId)
        .and
        .eq(column.crew_id, crew_id)
    }.update().apply()
  }

  def makeCompleted(crew_id: Int)(implicit session: DBSession): Unit = {
    withSQL {
      update(OrderRecord)
        .set(
          column.completed_at -> SqlDateTime.now,
          column.order_status -> Completed.label,
        )
        .where
        .eq(column.id, orderId)
        .and
        .eq(column.crew_id, crew_id)
    }.update().apply()
  }
}
