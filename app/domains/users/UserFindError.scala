package domains.users

sealed trait UserFindError extends Exception

class UnexpectedUserFindError(exception: Throwable) extends UserFindError
