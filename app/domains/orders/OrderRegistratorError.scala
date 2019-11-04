package domains.orders

sealed trait OrderRegistratorError extends Exception

class OverLimitRequestedOrderError(passenger_id: Int) extends OrderRegistratorError

class UnexpectedOrderRegistratorError(exception: Throwable) extends OrderRegistratorError
