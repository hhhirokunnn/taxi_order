package domains.orders

sealed trait OrderFindError extends Exception

class UnexpectedOrderFindError(exception: Throwable) extends OrderFindError
