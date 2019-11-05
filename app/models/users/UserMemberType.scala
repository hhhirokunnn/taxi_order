package models.users

import play.api.libs.json.{Reads, Writes}
import scalikejdbc.ParameterBinderFactory

/**
 * 会員の種別を表現するためのクラス
 * @param label 種別
 */
sealed abstract class UserMemberType(val label: String)

object UserMemberType {

  case object Passenger extends UserMemberType("passenger")

  case object Crew extends UserMemberType("crew")

  case object Unknown extends UserMemberType("unknown")

  def fromString(label: String): UserMemberType = label match {
    case Passenger.label => Passenger
    case Crew.label => Crew
    case Unknown.label => Unknown
    case _ => Unknown
  }

  implicit val writes: Writes[UserMemberType] = Writes.of[String].contramap(_.label)
  implicit val reads: Reads[UserMemberType] = Reads.of[String].map(UserMemberType.fromString)

  implicit val memberType: ParameterBinderFactory[UserMemberType] =
    ParameterBinderFactory(
      memberType => _.setString(_, memberType.label)
    )
}
