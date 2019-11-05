package models.users

import play.api.libs.json.{Format, Json}

/**
 * ログインのリクエストパラメータ用のクラス
 * @param mail_address メールアドレス
 * @param password パスワード
 */
case class UserLoginParameter(
  mail_address: String,
  password: String,
)

object UserLoginParameter {

  implicit val format: Format[UserLoginParameter] = Json.format[UserLoginParameter]
}