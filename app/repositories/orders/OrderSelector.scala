package repositories.orders

import scalikejdbc.{DBSession, select, withSQL}
import OrderRecord.o
import models.orders.OrderStatus

class OrderSelector(implicit session: DBSession) {

  def selectRequestedOrderBy(order_id: Int): Option[OrderRecord] = {
    withSQL {
      select.from(OrderRecord as o)
        .where
        .eq(o.id, order_id)
        .and
        .eq(o.order_status, OrderStatus.Requested.label)
        .orderBy(o.ordered_at)
        .desc
    }.map(OrderRecord.*).first.apply()
  }

  def selectAcceptedOrderBy(order_id: Int): Option[OrderRecord] = {
    withSQL {
      select.from(OrderRecord as o)
        .where
        .eq(o.id, order_id)
        .and
        .eq(o.order_status, OrderStatus.Accepted.label)
        .orderBy(o.ordered_at)
        .desc
    }.map(OrderRecord.*).first.apply()
  }

  def selectDispatchedOrderBy(order_id: Int): Option[OrderRecord] = {
    withSQL {
      select.from(OrderRecord as o)
        .where
        .eq(o.id, order_id)
        .and
        .eq(o.order_status, OrderStatus.Dispatched.label)
        .orderBy(o.ordered_at)
        .desc
    }.map(OrderRecord.*).first.apply()
  }

  def selectRequestingOrderBy(passenger_id: Int): Option[OrderRecord] = {
    withSQL {
      select.from(OrderRecord as o)
        .where
        .eq(o.passenger_id, passenger_id)
        .and
        .eq(o.order_status, OrderStatus.Requested.label)
        .or
        .eq(o.order_status, OrderStatus.Accepted.label)
        .or
        .eq(o.order_status, OrderStatus.Dispatched.label)
        .orderBy(o.ordered_at)
        .desc
    }.map(OrderRecord.*).first.apply()
  }

  def selectAll(): Seq[OrderRecord] = {
    withSQL {
      select.from(OrderRecord as o)
        .orderBy(o.ordered_at)
        .desc
    }.map(OrderRecord.*).list.apply()
  }
}
