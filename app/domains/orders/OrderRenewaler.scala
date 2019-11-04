package domains.orders

import models.orders.OrderStatus.{Accepted, Dispatched}
import models.orders.OrderAcceptParameter
import repositories.orders.{OrderAcceptFragment, OrderRecord, OrderSelector, OrderUpdater}
import scalikejdbc.DBSession

import scala.util.Try

class OrderRenewaler(order_id: Int)(implicit session: DBSession) {

  def makeAcceptFrom(param: OrderAcceptParameter, crew_id: Int): Either[OrderRenewalerError, Unit] = {
    val selector = new OrderSelector()
    for {
      order <- find(selector.selectRequestedOrderBy)
      _ <- ensureUpdatedAt(order, order.updated_at)
      _ <- update(param, crew_id)
    } yield {}
  }

  def makeDispatched(crew_id: Int): Either[OrderRenewalerError, Unit] = {
    val selector = new OrderSelector()
    updateToNextStatus(crew_id, selector.selectAcceptedOrderBy)
  }

  def makeCompleted(crew_id: Int): Either[OrderRenewalerError, Unit] = {
    val selector = new OrderSelector()
    updateToNextStatus(crew_id, selector.selectDispatchedOrderBy)
  }

  private def updateToNextStatus(crew_id: Int, finder: Int => Option[OrderRecord]) = {
    for {
      order <- find(finder)
      _ <- ensureCrewId(order, crew_id)
      _ <- update(crew_id, order)
    } yield {}
  }

  private def ensureCrewId(order: OrderRecord, crew_id: Int) = {
    order.crew_id match {
      case Some(id) if (id == crew_id) => Right({})
      case _ => Left(new NoFoundOrderError(order_id))
    }
  }

  private def ensureUpdatedAt(orderRecord: OrderRecord, date: String) = {
    if (orderRecord.updated_at == date) Right({}) else Left(new OptimisticLock(orderRecord.id))
  }

  private def toFragment(parameter: OrderAcceptParameter, crew_id: Int) =
    OrderAcceptFragment(
      crew_id = crew_id,
      estimated_dispatched_at = parameter.estimated_dispatched_at,
    )

  private def update(parameter: OrderAcceptParameter, crew_id: Int): Either[OrderRenewalerError, Unit] = Try {
    val fragment = toFragment(parameter, crew_id)
    new OrderUpdater(order_id).makeAcceptFrom(fragment)
  }.toEither.left.map(new UnexpectedOrderRenewalError(_))

  private def update(crew_id: Int, order: OrderRecord): Either[OrderRenewalerError, Unit] = Try {
    val updater = new OrderUpdater(order_id)
    order.order_status match {
      case Accepted => updater.makeDispatched(crew_id)
      case Dispatched => updater.makeCompleted(crew_id)
    }
  }.toEither.left.map(new UnexpectedOrderRenewalError(_))

  private def find(finder: Int => Option[OrderRecord]): Either[OrderRenewalerError, OrderRecord] = Try {
    finder(order_id)
  }.toEither match {
    case Right(Some(order)) => Right(order)
    case Right(_) => Left(new NoFoundOrderError(order_id))
    case Left(e) => Left(new UnexpectedOrderRenewalError(e))
  }
}
