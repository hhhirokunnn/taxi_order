# テーブル定義

## Users

会員テーブル

| 論理名| 物理名 | type | NULL? | 備考 |
| --- | --- | --- | --- | --- |
| ID | id | int | NOT NULL | PK |
| メールアドレス | mail_address | text | NOT NULL | UNIQUE |
| 会員種別 | member_type | text | NOT NULL | check制約：passenger, crew |
| パスワード | password | text | NOT NULL | |
| 追加日時 | created_at | text | NOT NULL | デフォルト値はJST現在時刻 |
| 変更日時 | updated_at | text | NOT NULL | デフォルト値はJST現在時刻 |

## Orders

注文テーブル

| 論理名| 物理名 | type | NULL? | 備考 |
| --- | --- | --- | --- | --- |
| ID | id | int | NOT NULL | PK |
| 会員ID | user_id | int | NOT NULL | FK users(id) |
| 配車地点 | dispatch_point | text | NOT NULL | |
| 注文状態 | order_status | text | NOT NULL | デフォルト値はrequested<br>check制約：requested, accepted, dispatched, completed |
| 注文日時 | ordered_at | text | NOT NULL | デフォルト値はJST現在時刻 |
| 配車完了予測時間 | estimated_dispatched_at | text | NULL | |
| 配車完了時間 | dispatched_at | text | NULL | |
| 注文完了時間 | completed_at | text | NULL | |
| 追加日時 | created_at | text | NOT NULL | デフォルト値はJST現在時刻 |
| 変更日時 | updated_at | text | NOT NULL | デフォルト値はJST現在時刻 |
