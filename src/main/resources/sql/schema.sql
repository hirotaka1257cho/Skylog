-- usersテーブル
CREATE TABLE users(
id SERIAL PRIMARY KEY,
name VARCHAR(30) NOT NULL,
email VARCHAR(255) UNIQUE NOT NULL,
password VARCHAR(255) NOT NULL,
created_at TIMESTAMP NOT NULL,
updated_at TIMESTAMP);

-- memosテーブル
CREATE TABLE memos(
id SERIAL PRIMARY KEY,
dates DATE NOT NULL,
texts TEXT NOT NULL,
city TEXT NOT NULL,
weather_snapshot TEXT,
temperature_snapshot NUMERIC(4, 2),
user_id INTEGER REFERENCES users(id),
created_at TIMESTAMP NOT NULL,
updated_at TIMESTAMP);
