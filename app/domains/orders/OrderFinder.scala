package domains.orders

import models.orders.Order
import repositories.orders.{OrderRecord, OrderSelector}
import scalikejdbc.DBSession

import scala.util.Try

class OrderFinder(implicit session: DBSession) {

  /**
   * 全注文を取得するドメインロジック
   */
  def findAll(): Either[OrderFindError, Seq[Order]] = Try {
    new OrderSelector().selectAll().map(toResponse)
  }.toEither.left.map(new UnexpectedOrderFindError(_))

  /**
   * 配車回答連絡待ちの注文を取得するドメインロジック
   * @param order_id 注文ID
   */
  def findRequestedOrderBy(order_id: Int): Either[OrderFindError, Option[Order]] = {
    val selector = new OrderSelector()
    find(selector.selectRequestedOrderBy, order_id)
  }

  /**
   * 配車回答済みで配車完了待ちの注文を取得するドメインロジック
   * @param order_id 注文ID
   */
  def findAcceptedOrderBy(order_id: Int): Either[OrderFindError, Option[Order]] = {
    val selector = new OrderSelector()
    find(selector.selectAcceptedOrderBy, order_id)
  }

  /**
   * 配車完了済みの注文を取得するドメインロジック
   * @param order_id 注文ID
   */
  def findDispatchedOrderBy(order_id: Int): Either[OrderFindError, Option[Order]] = {
    val selector = new OrderSelector()
    find(selector.selectDispatchedOrderBy, order_id)
  }

  /**
   * 営業が終了した注文を取得するドメインロジック
   * @param order_id 注文ID
   */
  def findCompletedOrderBy(order_id: Int): Either[OrderFindError, Option[Order]] = {
    val selector = new OrderSelector()
    find(selector.selectRequestedOrderBy, order_id)
  }

  /**
   * 乗客が自身の注文を取得するドメインロジック
   * @param passenger_id ユーザ(乗客)ID
   */
  def findRequestingOrderBy(passenger_id: Int): Either[OrderFindError, Option[Order]] = {
    val selector = new OrderSelector()
    find(selector.selectRequestingOrderBy, passenger_id)
  }

  private def find(select: Int => Option[OrderRecord], id: Int) = Try {
    select(id).map(toResponse)
  }.toEither.left.map(new UnexpectedOrderFindError(_))


  private def toResponse(record: OrderRecord): Order = {
    Order(
      order_id = record.id,
      dispatch_point = record.dispatch_point,
      order_status = record.order_status,
      ordered_at = record.ordered_at,
      estimated_dispatched_at = record.estimated_dispatched_at,
      dispatched_at = record.dispatched_at,
      updated_at = record.updated_at,
    )
  }
}
