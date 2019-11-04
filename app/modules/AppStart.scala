package modules

import javax.inject.{Inject, Singleton}
import play.api.inject.ApplicationLifecycle
import scalikejdbc.config.DBs

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AppStart @Inject()(lifecycle: ApplicationLifecycle)(
  implicit ec: ExecutionContext
) {

  DBs.setupAll()

  lifecycle.addStopHook { () =>
    Future(DBs.closeAll())
  }
}