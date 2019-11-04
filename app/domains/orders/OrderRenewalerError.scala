package domains.orders

sealed trait OrderRenewalerError extends Exception

class NoFoundOrderError(order_id: Int) extends OrderRenewalerError

class OptimisticLock(order_id: Int) extends OrderRenewalerError

class UnexpectedOrderRenewalError(exception: Throwable) extends OrderRenewalerError
