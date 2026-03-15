# weingo

体重記録・予想アプリ（Android）

## 概要

weingo は、毎日の体重を記録・管理するための Android アプリです。  
その日の「予想体重」と「確定体重」を入力し、予想がどれだけ当たったか（予想的中率）を確認できます。

## 機能

- **体重を測る**（入力タブ）
  - 今日の予想体重を入力・保存
  - 今日の確定体重を入力・保存
  - 予想体重と確定体重から予想的中率（%）を自動計算・表示
  - 的中率に応じてテキストの色が変化（110% 超：エラー色、105% 超：警告色、95% 未満：良好色）
- **グラフを見る**（グラフタブ）
  - 記録した体重データをグラフで確認

## 技術スタック

| 項目 | 内容 |
|------|------|
| プラットフォーム | Android |
| 言語 | Java |
| ビルドツール | Gradle |
| データベース | SQLite |
| 最小 SDK バージョン | 9 (Android 2.3 Gingerbread) |
| ターゲット SDK バージョン | 19 (Android 4.4 KitKat) |

> **注意**: これはレガシープロジェクトであり、SDK バージョンが古くなっています。現在の Google Play Store の要件を満たすには、minSdkVersion 21 以上・targetSdkVersion 34 以上へのアップデートが必要です。

## データベース

`weingo.db` という SQLite データベースを使用し、以下のテーブルでデータを管理します。

| カラム名 | 型 | 説明 |
|----------|----|------|
| date | INTEGER (PK) | 記録日（例: 20150101） |
| weight | TEXT | 確定体重（文字列として保存） |
| forecast | INTEGER | 予想体重 |

## ビルド方法

```bash
./gradlew assembleDebug
```

## プロジェクト構成

```
weingo/
├── main/                   # メインモジュール
│   └── src/main/
│       ├── java/me/oue/weingo/main/
│       │   ├── MainActivity.java          # タブ切り替えメイン画面
│       │   ├── RecordInputActivity.java   # 体重入力画面
│       │   ├── RecordViewActivity.java    # グラフ表示画面
│       │   └── MySQLiteOpenHelper.java    # SQLite ヘルパー
│       ├── res/                           # リソース（レイアウト・文字列など）
│       └── AndroidManifest.xml
├── build.gradle
└── settings.gradle
```
