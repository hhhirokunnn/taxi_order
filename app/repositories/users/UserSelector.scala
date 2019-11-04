package repositories.users

import scalikejdbc.{DBSession, NamedDB, select, withSQL}
import scalikejdbc.config.DBs
import UserRecord.u
import models.users.UserMemberType.{Crew, Passenger}
import models.users.UserLoginParameter

object UserSelector {

  def main(args: Array[String]): Unit = {

    DBs.setupAll()

    val Some(user) = NamedDB(Symbol("taxi_order")) localTx { implicit session =>
      selectPassengerBy(1)
    }

    println(user)
  }

  def selectUserBy(parameter: UserLoginParameter)(implicit session: DBSession): Option[UserRecord] = {
    withSQL {
      select.from(UserRecord as u)
        .where
        .eq(u.mail_address, parameter.mail_address)
        .and
        .eq(u.password, parameter.password)
    }.map(UserRecord.*).first.apply()
  }

  def selectUserBy(mailAddress: String)(implicit session: DBSession): Option[UserRecord] = {
    withSQL {
      select.from(UserRecord as u)
        .where
        .eq(u.mail_address, mailAddress)
    }.map(UserRecord.*).first.apply()
  }

  def selectPassengerBy(id: Int)(implicit session: DBSession): Option[UserRecord] = {
    withSQL {
      select.from(UserRecord as u)
        .where
        .eq(u.id, id)
        .and
        .eq(u.member_type, Passenger.label)
    }.map(UserRecord.*).first.apply()
  }

  def selectCrewBy(id: Int)(implicit session: DBSession): Option[UserRecord] = {
    withSQL {
      select.from(UserRecord as u)
        .where
        .eq(u.id, id)
        .and
        .eq(u.member_type, Crew.label)
    }.map(UserRecord.*).first.apply()
  }
}
