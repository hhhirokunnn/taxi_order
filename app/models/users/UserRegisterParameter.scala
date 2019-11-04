package models.users

import models.users.UserRegisterParameter.MemberType
import play.api.libs.json.{Json, Writes}

case class UserRegisterParameter(
  mail_address: String,
  password: String,
  member_type: MemberType,
)

object UserRegisterParameter {

  implicit val writes: Writes[UserRegisterParameter] = Json.writes[UserRegisterParameter]

  sealed abstract class MemberType(val label: String)

  case object PassengerType extends MemberType("passenger")

  case object CrewType extends MemberType("crew")

  object MemberType {
    implicit val writes: Writes[MemberType] = Writes.of[String].contramap(_.label)
  }
}
