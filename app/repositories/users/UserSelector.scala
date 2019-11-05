package repositories.users

import scalikejdbc.{DBSession, select, withSQL}
import UserRecord.u
import models.users.UserMemberType.{Crew, Passenger}
import models.users.UserLoginParameter

object UserSelector {

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
