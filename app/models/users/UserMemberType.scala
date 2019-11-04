package models.users

import play.api.libs.json.{Reads, Writes}

sealed abstract class UserMemberType(val label: String)

case object PassengerType$User extends UserMemberType("passenger")
case object CrewType$User extends UserMemberType("crew")
case object UnknownUserMemberType extends UserMemberType("unknown")

object UserMemberType {

  def fromString(label: String): UserMemberType = label match {
    case PassengerType$User.label => PassengerType$User
    case CrewType$User.label => CrewType$User
    case UnknownUserMemberType.label => UnknownUserMemberType
    case _ => UnknownUserMemberType
  }

  implicit val writes: Writes[UserMemberType] = Writes.of[String].contramap(_.label)
  implicit val reads: Reads[UserMemberType] = Reads.of[String].map(UserMemberType.fromString)
}
