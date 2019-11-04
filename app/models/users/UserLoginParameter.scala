package models.users

import play.api.libs.json.{Format, Json}

case class UserLoginParameter(
  mail_address: String,
  password: String,
)

object UserLoginParameter {

  implicit val format: Format[UserLoginParameter] = Json.format[UserLoginParameter]
}