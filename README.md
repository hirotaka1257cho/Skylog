# 天気メモアプリ

天気情報と日記を紐づけて記録できる天気メモWebアプリです。
都市名を入力するとOpenWeatherMap APIからリアルタイムで天気・気温を自動取得し、その日のメモと一緒に記録できます。

---

## 機能一覧

- ユーザー登録・ログイン・ログアウト（Spring Security）
- メモのCRUD（一覧・登録・詳細・編集・削除）
- 外部API（OpenWeatherMap）による天気・気温の自動取得
- ユーザーごとのデータ管理（自分のメモのみ操作可能）
- REST APIエンドポイント（GET / POST / PUT / DELETE）
- Swagger UIによるAPI仕様書の自動生成
- 入力バリデーション

---

## 技術スタック

| カテゴリ | 技術 |
|---|---|
| バックエンド | Java 21 / Spring Boot |
| 認証・認可 | Spring Security |
| DBアクセス | Spring JDBC（JdbcTemplate） |
| フロントエンド | Thymeleaf |
| データベース | PostgreSQL |
| 外部API連携 | RestTemplate（OpenWeatherMap API） |
| テスト | JUnit / Mockito |
| API仕様書 | Springdoc-openapi（Swagger UI） |
| コンテナ | Docker / Docker Compose |
| Webサーバー | Nginx（リバースプロキシ） |
| インフラ | AWS EC2 |
| CI/CD | GitHub Actions |

---

## 構成

アプリはDocker Composeで3つのコンテナを起動して動作します。
外部からのアクセスはNginx（80番）が受け、Spring Bootアプリへ転送します。アプリとDBのポートはNginx経由でのみ利用する想定です。

```
ブラウザ
   │  http://localhost （EC2では http://<パブリックIP>）
   ▼
┌─────────────────────────────┐
│  nginx コンテナ              │
│  - 80番で待ち受け            │
│  - リバースプロキシ          │
└───────────────┬─────────────┘
                │ app:8080 へ転送
                ▼
┌─────────────────────────────┐
│  app コンテナ（Spring Boot） │
│  - 画面表示・REST API        │
│  - 8080番で待ち受け          │
└───────────────┬─────────────┘
                │ db:5432 で接続
                ▼
┌─────────────────────────────┐
│  db コンテナ（PostgreSQL）   │
│  - 起動時に schema.sql を実行│
│  - データはボリュームに保存  │
└─────────────────────────────┘
```

各コンテナは `restart: always` を設定しているため、EC2の再起動後も自動で立ち上がります。

---

## ローカルでの起動（Docker）

### 1. 必要な環境

- Docker / Docker Compose

Java や PostgreSQL を個別にインストールする必要はありません。すべてコンテナ内で動作します。

### 2. 環境変数の設定

プロジェクト直下に `.env` ファイルを作成し、OpenWeatherMap の APIキーを設定します。

```
WEATHER_API_KEY=取得したOpenWeatherMapのAPIキー
```

APIキーは [OpenWeatherMap](https://openweathermap.org/) で無料登録後に取得できます。

### 3. 起動

```bash
docker compose up --build
```

起動後、ブラウザで http://localhost/users/login にアクセスしてください。

停止する場合は `Ctrl + C`、バックグラウンドで起動する場合は `-d` を付けます。

```bash
docker compose up --build -d
```

---

## AWS EC2 へのデプロイ

> **現在はコスト削減のため EC2 インスタンスを削除しており、公開していません。** 以下は公開していたときの手順です。

EC2（Amazon Linux）上に Docker をインストールし、ローカルと同じ `docker compose` でアプリを起動していました。

### 環境構築

リポジトリ直下の `setup.sh` を実行すると、Docker・Git・Docker Compose などEC2に必要なツールが一括でインストールされます。

```bash
bash setup.sh
```

### 起動

EC2上でリポジトリを取得し、`.env` を作成してから起動します。

```bash
git clone https://github.com/hirotaka1257cho/Skylog.git
cd Skylog
# .env を作成（WEATHER_API_KEY を記載）
docker compose up --build -d
```

ブラウザで `http://<EC2のパブリックIP>` にアクセスして利用していました。

セキュリティグループでは、HTTP（80番）と SSH（22番、自動デプロイ用）を許可していました。

---

## 自動デプロイ（CI/CD）

EC2 で公開していた間は、`main` ブランチへ push すると GitHub Actions が自動で EC2 に SSH 接続し、最新コードの取得とコンテナの再起動を行っていました。

```
main へ push
   ▼
GitHub Actions 起動
   ▼
EC2 に SSH 接続
   ▼
git pull → docker compose up --build -d
```

ワークフローの定義は `.github/workflows/deploy.yml` にあります。
EC2 を削除したため、現在は push では動かず、GitHub の Actions 画面から手動で実行する設定（`workflow_dispatch`）にしています。

---

## API仕様書

アプリ起動後、以下のURLでSwagger UIを確認できます。

```
http://localhost/swagger-ui/index.html
```

---

## 画面構成

| URL | 説明 |
|---|---|
| `/users/register` | ユーザー登録 |
| `/users/login` | ログイン |
| `/memos` | メモ一覧 |
| `/memos/create` | メモ登録 |
| `/memos/{id}` | メモ詳細 |
| `/memos/{id}/edit` | メモ編集 |

---

## REST API エンドポイント

| メソッド | URL | 説明 |
|---|---|---|
| GET | `/api/memos` | メモ一覧取得 |
| GET | `/api/memos/{id}` | メモ詳細取得 |
| POST | `/api/memos` | メモ登録 |
| PUT | `/api/memos/{id}` | メモ更新 |
| DELETE | `/api/memos/{id}` | メモ削除 |
