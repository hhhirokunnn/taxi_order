# API Specs

## POST /users/register

会員登録を行うAPI

### Request

#### Body
```
mail_address
    name: メールアドレス
    description: 既に登録されているメールアドレスでの新規登録はできません
    type: string
    maxLegth: 100
    nullable: false
                
password
    name: パスワード
    type: string
    maxLegth: 100
    nullable: false

member_type
    name: 会員種別
    descripion: 乗客か運転手かの情報
    type: string
    enum: [ passenger, crew ]
    maxLegth: 100
    nullable: false
```


Example
```
Content-Type: application/json

{
    "mail_address": "user@example.com",
    "password" : "Password",
    "member_type": "passeger"
}
```

### Response

#### 会員登録成功

code
```
200
```

#### 無効なリクエスト

code
```
400
```

## POST /users/login

会員のログインを行うAPI

### Request

#### Body
```
mail_address
    name: メールアドレス
    type: string
    maxLegth: 100
    nullable: false
                
password
    name: パスワード
    type: string
    maxLegth: 100
    nullable: false
```


Example
```
Content-Type: application/json

{
    "mail_address": "user@example.com",
    "password" : "Password"
}
```

### Response

#### ログイン成功

code
```
200
```

#### 無効なリクエスト

code
```
400
```

## POST /users/logout

会員のログアウトを行うAPI

### Request

#### Header

CookieにSessionTokenとして暗号化されたuserIdをもつ

### Response

#### ログアウト成功

code
```
200
```

#### ログイン前のユーザからのリクエスト

code
```
405
```

## POST /orders

乗客が特定の住所からタクシーの配車を要請するAPI

- リクエストしたユーザの会員種別が `passenger` の場合のみ処理が成功します
- 配車未完了の注文がある場合は注文できません

### Request

#### Header

CookieにSessionTokenとして暗号化されたuserIdをもつ

#### Body

```
dispatch_point
    name: 配車地点
    type: string
    maxLegth: 100
    nullable: false
```


Example
```
Content-Type: application/json

{
    "dispatch_point": "東京都新宿区西新宿２丁目８−１"
}
```

### Response

#### 配車要請処理成功

code
```
200
```

#### 無効なリクエスト

code
```
400
```

#### 会員種別が `passenger` 以外からのクライアントからのリクエスト

code
```
405
```

## GET /orders/list

注文を全件取得するAPI

- リクエストしたユーザの会員種別が `crew` の場合のみ処理が成功します
- 特定の乗客や運転手に関する要請ではなく、サービス全体の配車要請リストを応答します
- タクシー配車が完了した要請と待機中の要請が全てリストに含まれます
- 配車待機中なのか、配車完了した要請なのか、リストに表示します
- 配車要請時間と配車完了時間をリストに表示します
- 要請は、注文日時が最新のものからリストに表示されます

### Request

#### Header

CookieにSessionTokenとして暗号化されたuserIdをもつ

### Response

#### 注文の全件取得の成功

code
```
200
```

#### Body
```
order_id
    name: 注文ID
    type: int
    nullable: false
                
dispatch_point
    name: 配車地点
    type: string
    maxLegth: 100
    nullable: false
                
order_status
    name: 注文状態
    type: string
    enum: [ requested, accepted, completed ]
    maxLegth: 100
    nullable: false
                
ordered_at
    name: 注文時間
    type: strig
    maxLegth: 100
    nullable: false

dispatch_estimate_time
    name: 配車完了予定時間
    type: strig
    maxLegth: 100
    nullable: true

dispatched_at
    name: 配車完了時間
    type: strig
    maxLegth: 100
    nullable: true

updated_at
    name: 注文の最新更新日時
    description: 楽観ロック用の更新日時
    type: strig
    maxLegth: 100
    nullable: false
```


Example
```
Content-Type: application/json

{
    "results": [
        "order_id": 1,
        "dispatch_point": "東京都新宿区西新宿２丁目８−１",
        "order_status": "requested",
        "ordered_at": "2019-11-01"T01:50:11",
        "estimated_dispatched_at": "2019-11-01"T01:50:11+09:00",
        "dispatched_at": "2019-11-01"T01:50:11+09:00",
        "updated_at": "2019-11-01"T01:50:11+09:00"
    ]
}
```

#### 無効なリクエスト

code
```
400
```

#### 会員種別が `crew` 以外からのクライアントからのリクエスト

code
```
405
```

## GET /orders/own_requests

自身が要請した注文を取得するAPI

- リクエストしたユーザの会員種別が `passennger` の場合のみ処理が成功します
- 自分が要請した件にタクシーが配車されているかどうか確認できます

### Request

#### Header

CookieにSessionTokenとして暗号化されたuserIdをもつ

### Response

#### 自身が要請した注文を取得成功

code
```
200
```

#### Body
```
order_id
    name: 注文ID
    type: int
    nullable: false
                
dispatch_point
    name: 配車地点
    type: string
    maxLegth: 100
    nullable: false

order_status
    name: 注文状態
    type: string
    enum: [ requested, accepted, completed ]
    maxLegth: 100
    nullable: false

ordered_at
    name: 注文時間
    type: strig
    maxLegth: 100
    nullable: false

estimated_dispatched_at
    name: 配車完了予定時間
    type: strig
    maxLegth: 100
    nullable: true
```


Example
```
Content-Type: application/json

{
    "order_id": 1,
    "dispatch_point": "東京都新宿区西新宿２丁目８−１",
    "order_status": "requested",
    "ordered_at": "2019-11-01"T01:50:11+09:00",
    "estimated_dispatched_at": "2019-11-01"T01:50:11+09:00"
}
```

#### パラメータが無効なリクエスト

code
```
400
```

#### 会員種別が `passenger` 以外からのクライアントからのリクエスト

code
```
405
```

## PUT /orders/:order_id/accept

運転手が特定の要請に対して自分が対応すると要請するAPI

- リクエストしたユーザの会員種別が `crew` の場合のみ処理が成功します
- 対象の注文の `order_status` が `requested` である時のみ処理が成功します
- 一つの配車要請には、最大1名の運転手が配車されます

### Request

#### Header

CookieにSessionTokenとして暗号化されたuserIdをもつ

#### Path

```
order_id
    name: 注文ID
```

#### Body

```
estimated_dispatched_at
    name: 配車完了予定時間
    type: strig
    maxLegth: 100
    nullable: false

updated_at
    name: 注文の最新更新日時
    description: 楽観ロック用の更新日時
    type: strig
    maxLegth: 100
    nullable: false
```

Example

```
Content-Type: application/json

{
    "estimated_dispatched_at": "2019-11-01"T01:50:11+09:00",
    "updated_at": "2019-11-01"T01:50:11+09:00"
}
```

### Response

#### 配車回答処理成功

code
```
200
```

#### パラメータが無効なリクエスト

code
```
400
```

#### 会員種別が `crew` 以外からのクライアントからのリクエスト

code
```
405
```

## PUT /orders/:order_id/dispatched

配車が完了したことを報告するAPI

- リクエストしたユーザの会員種別が `crew` の場合のみ処理が成功します
- 対象の注文の `order_status` が `accepted` である時のみ処理が成功します

### Request

#### Header

CookieにSessionTokenとして暗号化されたuserIdをもつ

#### Path

```
order_id
    name: 注文ID
```

### Response

#### 配車完了処理成功

code
```
200
```

#### パラメータが無効なリクエスト

code
```
400
```

#### 会員種別が `crew` 以外からのクライアントからのリクエスト

code
```
405
```

## PUT /orders/:order_id/complete

配車が完了したことを報告するAPI

- リクエストしたユーザの会員種別が `crew` の場合のみ処理が成功します
- 対象の注文の `order_status` が `dispatched` である時のみ処理が成功します

### Request

#### Header

CookieにSessionTokenとして暗号化されたuserIdをもつ

#### Path

```
order_id
    name: 注文ID
```

### Response

#### 到着完了処理成功

code
```
200
```

#### パラメータが無効なリクエスト

code
```
400
```

#### 会員種別が `crew` 以外からのクライアントからのリクエスト

code
```
405
```
