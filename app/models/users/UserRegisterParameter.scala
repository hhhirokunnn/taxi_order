package models.users

import play.api.libs.json.{Format, Json}

case class UserRegisterParameter(
  mail_address: String,
  password: String,
  member_type: UserMemberType,
)

object UserRegisterParameter {

  implicit val format: Format[UserRegisterParameter] = Json.format[UserRegisterParameter]
}
