package repositories.orders

import scalikejdbc.{DBSession, update, withSQL}
import OrderRecord.o
import models.orders.OrderStatus.{Accepted, Completed, Dispatched}
import repositories.internal.SqlDateTime

class OrderUpdater(orderId: Int) {

  def orderAcceptFrom(fragment: OrderAcceptFragment)(implicit session: DBSession): Unit = {
    withSQL {
      update(OrderRecord as o)
        .set(
          o.crew_id -> fragment.crew_id,
          o.estimated_dispatched_at -> fragment.estimated_dispatched_at,
          o.order_status -> Accepted.label,
        )
        .where
        .eq(o.id, orderId)
    }.update().apply()
  }

  def orderDispatched(crew_id: Int)(implicit session: DBSession): Unit = {
    withSQL {
      update(OrderRecord as o)
        .set(
          o.dispatched_at -> SqlDateTime.now,
          o.order_status -> Dispatched.label,
        )
        .where
        .eq(o.id, orderId)
        .and
        .eq(o.crew_id, crew_id)
    }.update().apply()
  }

  def orderCompleted(crew_id: Int)(implicit session: DBSession): Unit = {
    withSQL {
      update(OrderRecord as o)
        .set(
          o.completed_at -> SqlDateTime.now,
          o.order_status -> Completed.label,
        )
        .where
        .eq(o.id, orderId)
        .and
        .eq(o.crew_id, crew_id)
    }.update().apply()
  }
}
