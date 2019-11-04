package models.users

import play.api.libs.json.{Json, Writes}

case class UserRegisterParameter(
  mail_address: String,
  password: String,
  member_type: UserMemberType,
)

object UserRegisterParameter {

  implicit val writes: Writes[UserRegisterParameter] = Json.writes[UserRegisterParameter]
}
