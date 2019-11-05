package models.users

import play.api.libs.json.{Format, Json}

/**
 * 会員登録するためのリクエストパラメータ用のクラス
 * @param mail_address メールアドレス
 * @param password パスワード
 * @param member_type 会員種別
 */
case class UserRegisterParameter(
  mail_address: String,
  password: String,
  member_type: UserMemberType,
)

object UserRegisterParameter {

  implicit val format: Format[UserRegisterParameter] = Json.format[UserRegisterParameter]
}
