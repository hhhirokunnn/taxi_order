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
    }.map(OrderRecord.*).first.apply()
  }

  def selectAcceptedOrderBy(order_id: Int): Option[OrderRecord] = {
    withSQL {
      select.from(OrderRecord as o)
        .where
        .eq(o.id, order_id)
        .and
        .eq(o.order_status, OrderStatus.Accepted.label)
    }.map(OrderRecord.*).first.apply()
  }

  def selectDispatchedOrderBy(order_id: Int): Option[OrderRecord] = {
    withSQL {
      select.from(OrderRecord as o)
        .where
        .eq(o.id, order_id)
        .and
        .eq(o.order_status, OrderStatus.Dispatched.label)
    }.map(OrderRecord.*).first.apply()
  }

  def selectRequestingOrderBy(passenger_id: Int): Option[OrderRecord] = {
    withSQL {
      select.from(OrderRecord as o)
        .where
        .eq(o.passenger_id, passenger_id)
        .and
        .eq(o.order_status, OrderStatus.Requested.label)
    }.map(OrderRecord.*).first.apply()
  }

  def selectAll(): Seq[OrderRecord] = {
    withSQL {
      select.from(OrderRecord as o)
    }.map(OrderRecord.*).list.apply()
  }
}
