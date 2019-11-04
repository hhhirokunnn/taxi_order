package repositories.users

import models.users.UserMemberType
import repositories.core.TaxiOrderSyntaxSupport
import scalikejdbc._

case class UserRecord(
  id: Int,
  mail_address: String,
  member_type: UserMemberType,
  password: String,
  created_at: String,
  updated_at: String,
)

object UserRecord extends TaxiOrderSyntaxSupport[UserRecord]("users") {

  val u: SyntaxProvider[UserRecord] = UserRecord.syntax("u")

  val * : WrappedResultSet => UserRecord = set =>
    UserRecord(
      id = set.int(u.resultName.id),
      mail_address = set.string(u.resultName.mail_address),
      member_type = UserMemberType.fromString(set.string(u.resultName.member_type)),
      password = set.string(u.resultName.password),
      created_at = set.string(u.resultName.created_at),
      updated_at = set.string(u.resultName.updated_at),
    )
}
