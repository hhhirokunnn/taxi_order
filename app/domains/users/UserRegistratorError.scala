package domains.users

sealed trait UserRegistratorError extends Exception

class DuplicatedMailAddressError(mailAddress: String) extends UserRegistratorError

class UnexpectedUserRegistrationError(exception: Throwable) extends UserRegistratorError
