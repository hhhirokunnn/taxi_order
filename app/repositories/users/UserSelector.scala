package repositories.users

import scalikejdbc.DB
import scalikejdbc.config.DBs
import scalikejdbc.interpolation.Implicits._

object UserSelector {

  def main(args: Array[String]): Unit = {

    DBs.setupAll()

    val Some(greeting) = DB localTx { implicit session =>
      sql" SELECT 'HELLO TaxiOrder!' as res".map(_.string("res")).first.apply()
    }

    println(greeting)
  }
}
