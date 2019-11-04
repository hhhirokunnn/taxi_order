package models.users

import play.api.libs.json.{Json, Writes}

case class UserLoginParameter(
  mail_address: String,
  password: String,
)

object UserLoginParameter {

  implicit val writes: Writes[UserLoginParameter] = Json.writes[UserLoginParameter]
}