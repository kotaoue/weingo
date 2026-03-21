# weingo

[![Vercel](https://img.shields.io/badge/vercel-deployed-brightgreen?logo=vercel)](https://weingo.vercel.app/)

体重記録・予想アプリ

## 概要

weingo は、毎日の体重を記録・管理するアプリです。  
その日の「予想体重」と「確定体重」を入力し、予想がどれだけ当たったか（予想的中率）を確認できます。

**Web 版**（`web/`）はモバイルフレンドリーな Next.js アプリです。  
Google Fitness API から実測体重を取得し、前回計測値からの差分で予想体重を入力・判定します。  
Vercel へそのままデプロイできます。

Try it live at **[https://weingo.vercel.app/](https://weingo.vercel.app/)** — no installation needed.

## 機能

- Google アカウントでサインイン
- Google Fitness API から最新の体重データを自動取得
- 前回計測値からの差分（±kg）で予想体重を入力
- 予想体重 vs 実測値の的中率を自動計算・カラー表示
- 過去 30 日間の体重履歴を表示
- モバイルフレンドリー UI
- Vercel へそのままデプロイ可能

## 技術スタック（Web 版）

| 項目 | 内容 |
|------|------|
| フレームワーク | Next.js 15 (App Router) |
| 言語 | TypeScript |
| スタイリング | Tailwind CSS |
| 認証 | NextAuth.js (Google OAuth2) |
| データソース | Google Fitness REST API |
| デプロイ先 | Vercel |

## セットアップ手順

#### 1. Google Cloud Console の設定

1. [Google Cloud Console](https://console.cloud.google.com/) でプロジェクトを作成
2. **Fitness API** を有効化
3. **OAuth 2.0 クライアント ID** を作成（アプリケーションの種類: ウェブ アプリケーション）
4. 承認済みのリダイレクト URI に以下を追加:
   - `http://localhost:3000/api/auth/callback/google`（ローカル開発）
   - `https://your-app.vercel.app/api/auth/callback/google`（Vercel デプロイ）

#### 2. ローカル開発

```bash
cd web
cp .env.local.example .env.local
# .env.local に GOOGLE_CLIENT_ID, GOOGLE_CLIENT_SECRET, NEXTAUTH_SECRET, NEXTAUTH_URL を設定

npm install
npm run dev
```

ブラウザで http://localhost:3000 を開く。

#### 3. Vercel へのデプロイ

```bash
# Vercel CLI を使用する場合
npm i -g vercel
cd web
vercel

# または GitHub リポジトリを Vercel に連携してデプロイ
```

Vercel の環境変数に以下を設定:

| 変数名 | 説明 |
|--------|------|
| `GOOGLE_CLIENT_ID` | Google OAuth クライアント ID |
| `GOOGLE_CLIENT_SECRET` | Google OAuth クライアントシークレット |
| `NEXTAUTH_SECRET` | セッション暗号化キー（`openssl rand -base64 32` で生成） |
| `NEXTAUTH_URL` | デプロイ先 URL（例: `https://your-app.vercel.app`） |

## プロジェクト構成

```
weingo/
├── web/                    # Web アプリ（Next.js + Vercel）
│   ├── src/
│   │   ├── app/            # Next.js App Router
│   │   │   ├── api/auth/   # NextAuth.js (Google OAuth2)
│   │   │   ├── api/fitness/# Google Fitness API proxy
│   │   │   ├── layout.tsx
│   │   │   └── page.tsx
│   │   ├── components/
│   │   │   ├── WeightTracker.tsx  # 体重入力・判定画面
│   │   │   └── SignIn.tsx         # サインイン画面
│   │   └── lib/
│   │       ├── authOptions.ts     # NextAuth 設定
│   │       ├── fitness.ts         # Google Fitness API クライアント
│   │       └── weight.ts          # 的中率計算ロジック
│   ├── .env.local.example  # 環境変数のテンプレート
│   └── vercel.json         # Vercel デプロイ設定
```
