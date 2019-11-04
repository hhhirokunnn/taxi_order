# テーブル定義

## Users

会員テーブル

| 論理名| 物理名 | type | NULLABLE | 備考 |
| --- | --- | --- | --- | --- |
| ID | id | int |  | PK |
| メールアドレス | mail_address | text | FALSE | UNIQUE |
| 会員種別 | member_type | text | FALSE | check制約：passenger, crew |
| パスワード | password | text | FALSE | |
| 追加日時 | created_at | text | FALSE | デフォルト値はJST現在時刻 |
| 変更日時 | updated_at | text | FALSE | デフォルト値はJST現在時刻 |

## Orders

注文テーブル

| 論理名| 物理名 | type | NULLABLE | 備考 |
| --- | --- | --- | --- | --- |
| ID | id | int | FALSE | PK |
| 乗客ID | passenger_id | int | FALSE | FK users(id) |
| 運転手ID | crew_id | int | TRUE | |
| 配車地点 | dispatch_point | text | FALSE | |
| 注文状態 | order_status | text | FALSE | デフォルト値はrequested<br>check制約：requested, accepted, dispatched, completed |
| 注文日時 | ordered_at | text | FALSE | デフォルト値はJST現在時刻 |
| 配車完了予測時間 | estimated_dispatched_at | text | TRUE | |
| 配車完了時間 | dispatched_at | text | TRUE | |
| 注文完了時間 | completed_at | text | TRUE | |
| 追加日時 | created_at | text | FALSE | デフォルト値はJST現在時刻 |
| 変更日時 | updated_at | text | FALSE | デフォルト値はJST現在時刻 |
