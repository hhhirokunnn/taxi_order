package repositories.users

import scalikejdbc.{DBSession, NamedDB, select, withSQL}
import scalikejdbc.config.DBs
import UserRecord.u

object UserSelector {

  def main(args: Array[String]): Unit = {

    DBs.setupAll()

    val Some(user) = NamedDB(Symbol("taxi_order")) localTx { implicit session =>
      selectById(1)
    }

    println(user)
  }

  def selectById(id: Int)(implicit session: DBSession): Option[UserRecord] = {
    withSQL {
      select.from(UserRecord as u).where.eq(u.id, id)
    }.map(UserRecord.*).first.apply()
  }
}
