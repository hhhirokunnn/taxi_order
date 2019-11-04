package repositories.users

import scalikejdbc.{DBSession, NamedDB, withSQL, insert}

object UserInserter {

  def insertFrom(fragment: UserInsertFragment)(implicit session: DBSession): Unit =
    NamedDB(Symbol("taxi_order")) localTx { implicit session =>
      withSQL {
        insert
          .into(UserRecord)
          .namedValues(fragment.toMap)
      }.update.apply()
    }
}
