package domains.users

import models.users.UserLoginParameter
import repositories.users.{UserRecord, UserSelector}
import scalikejdbc.DBSession

import scala.util.Try

class UserFinder(implicit session: DBSession) {

  def findBy(parameter: UserLoginParameter): Either[UserFindError, Option[UserRecord]] = Try {
    UserSelector.selectUserBy(parameter)
  }.toEither.left.map(new UnexpectedUserFindError(_))

  def findPassengerBy(passengerId: Int): Either[UserFindError, Option[UserRecord]] = Try {
    UserSelector.selectPassengerBy(passengerId)
  }.toEither.left.map(new UnexpectedUserFindError(_))

  def findCrewBy(crewId: Int): Either[UserFindError, Option[UserRecord]] = Try {
    UserSelector.selectCrewBy(crewId)
  }.toEither.left.map(new UnexpectedUserFindError(_))
}
