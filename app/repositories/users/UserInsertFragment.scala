package repositories.users

import scalikejdbc.ParameterBinder
import scalikejdbc.interpolation.SQLSyntax
import UserRecord.column
import models.users.UserMemberType

case class UserInsertFragment(
  mail_address: String,
  password: String,
  member_type: UserMemberType
) {

  def toMap: Map[SQLSyntax, ParameterBinder] =
    Map(
      column.mail_address -> mail_address,
      column.password -> password,
      column.member_type -> member_type.label
    )
}
