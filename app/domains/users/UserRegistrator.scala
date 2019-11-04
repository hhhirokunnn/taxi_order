package domains.users

import models.users.UserRegisterParameter
import repositories.users.{UserInsertFragment, UserInserter, UserSelector}
import scalikejdbc.DBSession

import scala.util.Try

class UserRegistrator(parameter: UserRegisterParameter)(implicit session: DBSession) {

  def createUserFrom(): Either[UserRegistratorError, Unit] =
    ensureMailAddress() match {
      case Some(_) => Left(new DuplicatedMailAddressError(parameter.mail_address))
      case _ => insert()
    }

  private def insert() = Try {
    UserInserter.insertFrom(toFragment)
  }.toEither.left.map(new UnexpectedUserRegistrationError(_))

  private def ensureMailAddress() = {
    UserSelector.selectUserBy(parameter.mail_address)
  }

  private def toFragment = {
    UserInsertFragment(
      mail_address = parameter.mail_address,
      password = parameter.password,
      member_type = parameter.member_type,
    )
  }
}
