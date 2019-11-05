package modules

import com.google.inject.AbstractModule

/**
 * コントローラでDBのセットアップを共通化するためのモジュール
 */
class StartModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[AppStart]).asEagerSingleton()
  }
}
