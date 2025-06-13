# stuinfoを動作させるための準備

## 👉 stuinfoを動作させるのに必要なデータベースおよびテーブルの作成

stuinfoのDAOを動作させるためには、`mysql`コンテナに`database`データベースを作成して、そこに必要なテーブルを作成する必要があります。

まず、[`mysql`コンテナのMySQLサーバに **`root`ユーザとして** ログイン](../../README.md#️-cliで操作する場合)してください。
`root`ユーザのパスワードは`password`です。

```sh
mysql -h mysql -u root -p
```

MySQLサーバに`root`ユーザとしてログインできたら、次のSQL文を実行して、データベースを作成します。

```sql
-- データベース`database`を作成する。
create database `database`;

-- `mysql`ユーザーが`database`データベースを操作できるようにする。
grant all privileges on `database`.* to `mysql`@`%`;
```

次に、[`mysql`コンテナのMySQLサーバの`database`データベースに`mysql`ユーザとしてログイン](../../README.md#️-cliで操作する場合)して、[setup/init.sql](../../setup/init.sql)に記されているSQL文を実行して、必要なテーブルを作成します。

```sql
-- `root`ユーザとしてログインしているMySQLから一度ログアウトする。
exit
```

```sh
# `database`データベースに`mysql`ユーザーとしてログインする。
# `mysql`ユーザのパスワードは`password`です。
mysql -h mysql -D database -u mysql -p
```

```sql
-- MySQLサーバに`mysql`ユーザとしてログインできたら、次のSQL文を実行する。

-- `students`テーブルが既に存在している場合は削除する。
drop table if exists students;

-- `students`テーブルを作成する。
create table students (
  `id` varchar(8) primary key,
  `name` varchar(64) not null
);
```

### 👉 stuinfoからデータベース管理システムへの接続情報の保存場所

データベース管理システムへの接続情報は、[`src/main/resources/dataSource.properties`](../../src/main/resources/dataSource.properties)に保存されています。

```properties
jdbcUrl=jdbc:mysql://mysql:3306/database?characterEncoding=utf8&allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Tokyo&rewriteBatchedStatements=true
username=mysql
password=password
```

`jdbcUrl`プロパティで、接続先のホスト名、ポート番号、データベース名を設定します。`jdbc:mysql://<ホスト名>:<ポート番号>/<データベース名>`から始まります。

ユーザー名とパスワードはそれぞれ`username`プロパティと`password`プロパティで設定します。
