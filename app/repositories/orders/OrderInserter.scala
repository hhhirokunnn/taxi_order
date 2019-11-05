package repositories.orders

import scalikejdbc.{DBSession, NamedDB, withSQL, insert}

object OrderInserter {

  def insertFrom(fragment: OrderRequestFragment)(implicit session: DBSession): Unit =
    NamedDB(Symbol("taxi_order")) autoCommit { implicit session =>
      withSQL {
        insert
          .into(OrderRecord)
          .namedValues(fragment.toMap)
      }.update.apply()
    }
}
