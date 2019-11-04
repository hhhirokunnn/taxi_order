package domains.orders

sealed trait OrderRegisterError extends Exception

class OverLimitRequestedOrderError(passenger_id: Int) extends OrderRegisterError

class UnexpectedOrderRegisterError(exception: Throwable) extends OrderRegisterError
