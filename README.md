# 概要

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

---

## セットアップ手順

### 1. 必要な環境

- Java 21
- PostgreSQL
- Maven

### 2. データベースの作成

```sql
CREATE DATABASE weather;
```

### 3. テーブルの作成

`src/main/resources/sql/schema.sql` をPostgreSQLで実行してください。

```bash
psql -U postgres -d weather -f src/main/resources/sql/schema.sql
```

### 4. 環境変数の設定

以下の環境変数を設定してください。

```bash
export WEATHER_API_KEY=取得したOpenWeatherMapのAPIキー
export DB_PASS=PostgreSQLのパスワード  # デフォルト: postgres
```

OpenWeatherMap APIキーは https://openweathermap.org/ で無料登録後に取得できます。

### 5. アプリの起動

```bash
./mvnw spring-boot:run
```

起動後、ブラウザで http://localhost:8080/users/login にアクセスしてください。

---

## API仕様書

アプリ起動後、以下のURLでSwagger UIを確認できます。

```
http://localhost:8080/swagger-ui/index.html
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
