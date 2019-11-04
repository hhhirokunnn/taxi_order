package domains.orders

sealed trait OrderFinderError extends Exception

class UnexpectedOrderFinderErrorError(exception: Throwable) extends OrderFinderError
