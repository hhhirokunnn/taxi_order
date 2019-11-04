package models.orders

import play.api.libs.json.{Reads, Writes}
import scalikejdbc.ParameterBinderFactory

sealed abstract class OrderStatus(val label: String)

object OrderStatus {

  case object Requested extends OrderStatus("requested")

  case object Accepted extends OrderStatus("accepted")

  case object Dispatched extends OrderStatus("dispatched")

  case object Completed extends OrderStatus("completed")

  case object Unknown extends OrderStatus("unknown")

  def fromString(label: String): OrderStatus = label match {
    case Requested.label => Requested
    case Accepted.label => Accepted
    case Dispatched.label => Dispatched
    case Completed.label => Completed
    case Unknown.label => Unknown
    case _ => Unknown
  }

  implicit val writes: Writes[OrderStatus] = Writes.of[String].contramap(_.label)
  implicit val reads: Reads[OrderStatus] = Reads.of[String].map(OrderStatus.fromString)

  implicit val orderStatus: ParameterBinderFactory[OrderStatus] =
    ParameterBinderFactory(
      orderStatus => _.setString(_, orderStatus.label)
    )
}
