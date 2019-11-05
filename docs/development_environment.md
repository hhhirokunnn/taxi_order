# 技術

- 言語
    Scala 2.13.0

- フレームワーク
    PlayFramework 2.7
    
- DB
    SQLite3

# 環境構築手順

## javaインストール

Scalaを動かすにはjavaをインストールする必要があります。
今回は `java11` を入れます。
javaのバージョン管理には `jEnv` を使いました。
`jEnv` は `homebrew` でインストールすることができます。


## sbtインストール

このプロジェクトでは `sbt` をつかってビルドしています。

## dbインストール

`SQLite3` をインストールしてください。

## プロジェクトをクローン

プロジェクトを `git clone` してください。

## プロジェクトのビルド

`git clone`後はプロジェクト直下でビルドを行ってください。
初回のビルドは通信状況によってライブラリのインストール失敗することもあるので、失敗しても何度かビルドにトライしてくてださい。

```
$ cd order_taxi/
$ pwd
> ${プロジェクトをクローンしたディレクトリのパス}/order_taxi
$ sbt run
> --- (Running the application, auto-reloading is enabled) ---
>   
>   [info] p.c.s.AkkaHttpServer - Listening for HTTP on /0:0:0:0:0:0:0:0:9000
>   
>   (Server started, use Enter to stop and go back to the console...)
```

上記のメッセージが表示されたらbuildに成功です。
