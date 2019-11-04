package domains.orders

import models.orders.OrderAcceptParameter
import repositories.orders.{OrderAcceptFragment, OrderRecord, OrderSelector, OrderUpdater}
import scalikejdbc.DBSession

import scala.util.Try

class OrderRenewaler(order_id: Int)(implicit session: DBSession) {

  def makeAcceptFrom(param: OrderAcceptParameter): Either[OrderRenewalerError, Unit] = for {
    order <- findRequestedOrder()
    _ <- ensureCrewId(order, param.crew_id)
    _ <- updateRequestedOrder(param)
  } yield {}

  def makeDispatched(crew_id: Int): Either[OrderRenewalerError, Unit] = for {
    order <- findAcceptedOrder()
    _ <- ensureCrewId(order, crew_id)
    _ <- updateAcceptedOrder(crew_id)
  } yield {}

  def makeCompleted(crew_id: Int): Either[OrderRenewalerError, Unit] = for {
    order <- findDispatchedOrder()
    _ <- ensureCrewId(order, crew_id)
    _ <- updateDispatchedOrder(crew_id)
  } yield {}

  private def ensureCrewId(order: OrderRecord, crew_id: Int) = {
    order.crew_id match {
      case Some(id) if (id == crew_id) => Right({})
      case _ => Left(new NoFoundOrderError(order_id))
    }
  }

  private def updateRequestedOrder(parameter: OrderAcceptParameter): Either[OrderRenewalerError, Unit] = Try {
    val fragment = toFragment(parameter)
    new OrderUpdater(order_id).makeAcceptFrom(fragment)
  }.toEither.left.map(new UnexpectedOrderRenewalError(_))

  private def toFragment(parameter: OrderAcceptParameter) = {
    OrderAcceptFragment(
      crew_id = parameter.crew_id,
      estimated_dispatched_at = parameter.estimated_dispatched_at,
    )
  }

  private def updateAcceptedOrder(crew_id: Int): Either[OrderRenewalerError, Unit] = Try {
    new OrderUpdater(order_id).makeDispatched(crew_id)
  }.toEither.left.map(new UnexpectedOrderRenewalError(_))

  private def updateDispatchedOrder(crew_id: Int): Either[OrderRenewalerError, Unit] = Try {
    new OrderUpdater(order_id).makeCompleted(crew_id)
  }.toEither.left.map(new UnexpectedOrderRenewalError(_))

  private def findRequestedOrder(): Either[OrderRenewalerError, OrderRecord] = Try {
    new OrderSelector().selectRequestedOrderBy(order_id)
  }.toEither match {
    case Right(Some(order)) => Right(order)
    case Right(_) => Left(new NoFoundOrderError(order_id))
    case Left(e) => Left(new UnexpectedOrderRenewalError(e))
  }

  private def findAcceptedOrder(): Either[OrderRenewalerError, OrderRecord] = Try {
    new OrderSelector().selectAcceptedOrderBy(order_id)
  }.toEither match {
    case Right(Some(order)) => Right(order)
    case Right(_) => Left(new NoFoundOrderError(order_id))
    case Left(e) => Left(new UnexpectedOrderRenewalError(e))
  }

  private def findDispatchedOrder(): Either[OrderRenewalerError, OrderRecord] = Try {
    new OrderSelector().selectDispatchedOrderBy(order_id)
  }.toEither match {
    case Right(Some(order)) => Right(order)
    case Right(_) => Left(new NoFoundOrderError(order_id))
    case Left(e) => Left(new UnexpectedOrderRenewalError(e))
  }
}
