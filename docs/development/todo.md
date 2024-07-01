# ✅ Webアプリケーション開発課題 やることリスト

## Quick Links

- [必要なプログラムのファイルを明らかにする](#必要なプログラムのファイルを明らかにする)
- [データベースの準備](#データベースの準備)
- [エンティティ](#エンティティ)
- [DAO](#dao)
- [サーブレット（コントローラ）](#サーブレットコントローラ)
- [コントロール](#コントロール)
- [JSP](#jspビュー)

## 読み方

- `⭐️`の数：⭐️の数（1つ〜3つ）は必要性と難易度に基づいています。⭐️が少ないほど必要かつ簡単な事柄であり、⭐️が多いほどあまり必要でなく高度な事柄です。
- 実装の順番：
    - JSP、コントローラ、コントロール、……と外側から内側に向かって実装する方と、内側から外側に向かって実装する方に二分されることが多いです。情報システム設計の受講生では外側から内側に向かって実装する方が多いです。
    - いずれにしても、サーブレットやコントロール、JSPの実装にあたっては、「どのJSPに対応する画面から、どのサーブレットを呼び出せるようにするのかを決めること」「サーブレットが受け付けるパラメータ、コントロールが受け付ける変数、ユーザがJSPの`form`要素から入力できる内容、JSPの`a`要素のURLに含まれるクエリパラメータの間に過不足がないようにすること」「コントロールが返す処理結果、JSP上に表示させる処理結果の間に過不足がないようにすること」が必要です。

## 必要なプログラムのファイルを明らかにする

- ⭐️ 設計工程で作成したクラス図を基に、エンティティを作成・実装する。
- ⭐️ 実装したエンティティごとにDAOを作成・実装する。
- ⭐️ 設計工程で作成したユースケースごと、または、ユースケース記述で言及されている画面・操作ごとに、サーブレット（コントローラ）とコントロールを作成・実装する。
- ⭐️ 設計工程で作成したユースケース記述で言及されている画面ごとに、JSPを作成・実装する。JSPは`src/webapp/WEB-INF`以下に配置する。
- ⭐️ サーブレットやコントロール、JSPの実装にあたっては、
    - どのJSPに対応する画面から、どのサーブレットを呼び出せるようにするのかを決める。
    - サーブレットが受け付けるパラメータ、コントロールが受け付ける変数、ユーザがJSPの`form`要素から入力できる内容、JSPの`a`要素のURLに含まれるクエリパラメータの間に過不足がないようにする。
    - コントロールが返す処理結果、JSP上に表示させる処理結果の間に過不足がないようにする。
    - そのためのメモを作成するのもオススメです。
- ⭐️⭐️ 必要に応じてCSSやJavaScriptを作成・実装する。CSSやJavaScriptは`src/webapp`以下に配置する。`src/webapp/WEB-INF`以下に配置することはできない。

## データベースの準備

- ⭐️ `mysql`コンテナにデータベースを作成する。
    - [`mysql`コンテナのMySQLサーバに **`root`ユーザとして** ログイン](../README.md#cliで操作する場合)して、次のSQL文を実行して、データベースを作成する。データベース名は自分で決める（ここでは`database`としている）。
        ```sql
        -- データベース`database`を作成する。
        create database `database`;

        -- `mysql`ユーザーが`database`データベースを操作できるようにする。
        grant all privileges on `database`.* to `mysql`@`%`;
        ```
- ⭐️ 作成したデータベースにテーブルを作成する。
    - [`mysql`コンテナのMySQLサーバの`database`データベースに`mysql`ユーザとしてログイン](../README.md#cliで操作する場合)して、テーブルを作成するためのSQL文を実行する。
    - テーブルを作成するためのSQL文は保存しておくとよい。
- ⭐️ `src/main/resources/dataSource.properties`を修正する。
    - `jdbcUrl`プロパティのデータベース名を、実際に使用するデータベース名に変更する。

## エンティティ

- ⭐️ クラス図内で挙げたフィールドと、それに対応するゲッター、セッター、各フィールドを初期化するコンストラクタを必要に応じて実装する。
    - 💡 Language Support for Java拡張機能がインストールされているVSCodeでは、クラス宣言部分で`Ctrl+.`（macOSでは`⌘.`）を押すと、ゲッター、セッター、コンストラクタを生成させることができます。
- ⭐️⭐️⭐️ セッターやコンストラクタでフィールドに新しい値を割り当てる前に、新しい値が要件を満たしているかどうかを調べ、満たしていない場合は例外を投げるようにすることで、アプリケーションの完全性を高めることができる。

## DAO

- ⭐️ 実装したエンティティと1対1で対応するように実装するとよい。
- ⭐️ データベース管理システムとの通信に必要な`java.sql.Connection`オブジェクトを取得したり、接続を閉じる処理を簡単に記述したりするために、次のようにフィールドやコンストラクタを定義しておくとよい。（これ以降の説明は下記のフィールドおよびコンストラクタを定義していると仮定しています）
    ```java
    public class ExampleDao {
      private final DataSource dataSorce;
      private final ConnectionCloser connectionCloser;

      public ExampleDao() {
        // MySQLサーバにアクセスするためには`DataSource.getConnection`メソッドで`Connection`オブジェクトを得る必要があるので、
        // `dataSource`フィールドに`DataSource`型のオブジェクトを割り当てておく。
        // `DataSource`型のオブジェクトは`DataSourceHolder.dataSource`フィールドから得られる。
        this.dataSorce = new DataSourceHolder().dataSource;

        // `ConnectionCloser`型のオブジェクトは、MySQLサーバにアクセスするための`Connection`オブジェクトを閉じるための`closeConnection`メソッドを持つ。
        // このクラスの各メソッドで`ConnectionCloser.closeConnection`メソッドを利用できるように、`connectionCloser`フィールドに`ConnectionCloser`型のオブジェクトを割り当てておく。
        this.connectionCloser = new ConnectionCloser();
      }
    }
    ```
- 参照系のメソッドについての事柄
    - ⭐️ 1件参照に用いるメソッドでは、参照対象のオブジェクトのIDなどを引数として受け付けるようにする。
    - ⭐️ 検索に用いるメソッドでは、検索キーワードなどを引数として受け付けるようにする。
    - ⭐️ 結果はエンティティオブジェクトまたはエンティティオブジェクトの`List`として返すようにする。
    - ⭐️ 参照系のメソッドの処理は次の順番で進める。
        1. `java.sql.Connection`型のコネクションオブジェクトを割り当てるための変数（変数名を`connection`とする）を宣言する。
        2. 変数`connection`に`this.dataSorce.getConnection()`の戻り値を割り当てる。
        3. `connection.prepareStatement(<SQL文>)`を呼び出して、変数（変数名を`preparedStatement`とする）に割り当てる。
            - SQL文のうち、受け取った引数によって変更したい部分は`?`にしておく。
        4. `preparedStatement`のセッターメソッドに受け取った引数を渡して呼び出し、`?`を穴埋めしていく。
            - 例：最初（1番目）の`?`に`String`型の引数`id`を割り当てるには、`preparedStatement.setString(1, id)`とする。
        5. `preparedStatement.executeQuery()`を呼び出して、SQL文を実行する。実行結果は変数（変数名を`resultSet`とする）に割り当てる。
        6. SQL文の実行結果からエンティティオブジェクトまたはエンティティオブジェクトの`List`を作成する。
            - 1件参照の場合
                1. 一度だけ`resultSet.next()`を呼び出す。
                2. `resultSet`のゲッターメソッドにカラム名を渡して呼び出し、その戻り値をエンティティクラスのコンストラクタに渡して、エンティティをインスタンス化する。
                ```java
                resultSet.next();
                ExampleEntity exampleEntity = new ExampleEntity(resultSet.getString("id"), resultSet.getString("name"));
                ```
            - 複数件参照の場合
                1. 空の`List`（`LinkedList`や`ArrayList`）をインスタンス化して変数（変数名を`list`とする）に割り当てる。
                2. `while`文の条件式で`resultSet.next()`を呼び出す。
                3. `while`文で繰り返される部分に次の処理を記述する。
                    1. `resultSet`のゲッターメソッドにカラム名を渡して呼び出し、その戻り値をエンティティクラスのコンストラクタに渡して、エンティティをインスタンス化する。
                    2. `list.add(<エンティティオブジェクト>)`を呼び出し、エンティティオブジェクトをリストに追加する。
                ```java
                List<ExampleEntity> list = new LinkedList<>();
                while (resultSet.next()) {
                  ExampleEntity exampleEntity = new ExampleEntity(resultSet.getString("id"), resultSet.getString("name"));
                  list.add(exampleEntity);
                }
                ```
        7. エンティティオブジェクトまたは`List`を返す。
- 作成・更新系のメソッドについての事柄
    - ⭐️ エンティティオブジェクトを引数として受け付けるようにする。
    - ⭐️ 作成・更新系のメソッドの処理は次の順番で進める。
        1. `java.sql.Connection`型のコネクションオブジェクトを割り当てるための変数（変数名を`connection`とする）を宣言する。
        2. 変数`connection`に`this.dataSorce.getConnection()`の戻り値を割り当てる。
        3. `connection.prepareStatement(<SQL文>)`を呼び出して、変数（変数名を`preparedStatement`とする）に割り当てる。
            - SQL文のうち、受け取った引数によって変更したい部分は`?`にしておく。
        4. `preparedStatement`のセッターメソッドに、引数として受け取ったエンティティオブジェクトの各フィールドの値を渡して呼び出し、`?`を穴埋めしていく。
            - 例：最初（1番目）の`?`に`String`型を返すゲッター`getId()`の戻り値を割り当てるには、`preparedStatement.setString(1, <引数の名前>.getId())`とする。
        5. `preparedStatement.executeUpdate()`を呼び出して、SQL文を実行する。
- 削除系のメソッドについての事柄
    - ⭐️ 削除対象のオブジェクトのIDを引数として受け付けるようにする。
    - ⭐️ 削除系のメソッドの処理は次の順番で進める。
        1. `java.sql.Connection`型のコネクションオブジェクトを割り当てるための変数（変数名を`connection`とする）を宣言する。
        2. 変数`connection`に`this.dataSorce.getConnection()`の戻り値を割り当てる。
        3. `connection.prepareStatement(<SQL文>)`を呼び出して、変数（変数名を`preparedStatement`とする）に割り当てる。
            - SQL文のうち、受け取った引数によって変更したい部分は`?`にしておく。
        4. `preparedStatement`のセッターメソッドに、引数として受け取った削除対象のオブジェクトのIDを渡して呼び出し、`?`を穴埋めしていく。
            - 例：最初（1番目）の`?`に`String`型の引数`id`を割り当てるには、`preparedStatement.setString(1, id)`とする。
        5. `preparedStatement.executeUpdate()`を呼び出して、SQL文を実行する。
- ⭐️ 値を返したり例外を投げたりする前に必ずデータベース管理システムとの接続を閉じる。
    - 例：
        ```java
        Connection connection = null;
        try {
          connection = this.dataSource.getConnection();
          // ここに処理の続きを書く。
        } catch (SQLException sqlException) {
          // 処理中に例外が投げられた場合は、ここで例外の処理を行う。
        } finally {
          this.connectionCloser.closeConnection(connection);
        }
        ```
- ⭐️⭐️⭐️ 例外の種類に応じてエラーメッセージを出し分ける。
    - 例えば`PRIMARY KEY`制約を持つカラムの値が他のレコードと重複するようなSQL文を実行すると、投げられる例外オブジェクトの型は、`java.sql.SQLException`型のサブクラスである`java.sql.SQLIntegrityConstraintViolationException`型になる。例外のオブジェクトの型を区別することで、エラーメッセージを出し分けることができる。

## サーブレット（コントローラ）

- ⭐️ コントロールと1対1で対応するように実装するとよい。
- ⭐️ サーブレットクラスの要件を満たす。
    - `HttpServlet`を継承する。
    - `public`修飾子を付ける。
    - `@WebServlet`アノテーションを付けて、`value`属性でどのパスに対するリクエストを処理するのかを指定する。
    - 下の例のような引数を取り、戻り値のない、`ServletException`および`IOException`を投げる可能性のある`doGet`メソッドまたは`doPost`メソッドを実装する。
    - サーブレットクラスとしての最低限の要件を満たしている例
        ```java
        @WebServlet(value = { "/example" })
        public class ExampleServlet extends HttpServlet {

          @Override
          protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            req.setCharacterEncoding("UTF-8");
          }

          @Override
          protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {}

        }
        ```
- ⭐️ 参照系のユースケースに対応するサーブレットには`doGet`メソッドを、作成・更新・削除系のユースケースに対応するサーブレットには`doPost`メソッドを実装する。
- ⭐️ `doGet`メソッドまたは`doPost`メソッドの処理は次の順番で進める。
    1. 最初に`req.setCharacterEncoding("UTF-8")`を呼び出す。
    2. HTTPリクエストに含まれるパラメータは`req.getParameter(<String型 パラメータ名>)`の戻り値から取得できる。
        - ⭐️⭐️ チェックボックスでチェックされた項目をすべて取得する場合は`req.getParameterValues(<String型 パラメータ名>)`を用いる。
    3. 取得したパラメータをコントロールに渡して呼び出し、処理結果を取得する。
    4. 処理結果に応じて、JSPにフォワードするか、リダイレクトを返す。
        - ⭐️ GETリクエストを処理する`doGet`メソッドでは、JSPにフォワードするのが一般的。
            - JSPに処理結果等のオブジェクトを渡したい場合は、`req.setAttribute(<String型 キー>, <値>)`を呼び出す。
            - `req.getRequestDispatcher("<jspファイルへのパス>").forward(req, resp)`を呼び出して、JSPにフォワードする。
        - ⭐️⭐️ POSTリクエストを処理する`doPost`メソッドでは、処理結果を表示するJSPにフォワードするサーブレットに対応するURLへのリダイレクトを返すのが一般的。
            - `req.sendRedirect(<String型 リダイレクト先のURL>)`を呼び出して、リダイレクトを返す。
                - 例：`@WebServlet(value = { "/example/path" })`が付いたサーブレットに対応するURLにリダイレクトする場合は、次のように記述する。
                    - `req.sendRedirect(req.getContextPath() + "/example/path")`
                    - `req.sendRedirect("/systemdesign2024/example/path")`（コンテキストパスが`/systemdesign2024`の場合）
            - リダイレクト先に文字列を渡したい場合は、リダイレクト先のURLにクエリパラメータを付ける。
                - クエリパラメータの値に日本語を用いる場合は、値の部分を`URLEncoder.encode(<String型 値>, "UTF-8")`の戻り値にする。
                - 例：`req.sendRedirect(req.getContextPath() + "/example/path?messageFromPrev=" + URLEncoder.encode("OK牧場！", "UTF-8"))`
- ⭐️ サーブレットが受け付けるパラメータと、サーブレットに対応するコントロールが受け付ける引数の間には、過不足がないようにする。
- ⭐️⭐️⭐️ コントロールが例外を投げる可能性がある場合は、try-catch句を用いるとよい。
    - `try`句で、コントロールを呼び出し、基本系列で表示するべき画面に対応するJSPにフォワードしたり、基本系列で表示するべき画面のJSPにフォワードするサーブレットに対応するURLへのリダイレクトを返したりする。
    - `catch`句で、例外系列で表示するべき画面に対応するJSPにフォワードしたり、例外系列で表示するべき画面のJSPにフォワードするサーブレットに対応するURLへのリダイレクトを返したりする。

## コントロール

- ⭐️ コントローラと1対1で対応するように実装するとよい。
- ⭐️ コントロールクラスは、対応するユースケースを実行するための1つのメソッドだけを持つようにするとよい。
- 参照系のユースケースに対応するコントロールについての事柄
    - ⭐️ 1件参照のユースケースでは、参照対象のオブジェクトのIDなどを引数として受け付けるようにする。
    - ⭐️ 検索のユースケースでは、検索キーワードなどを引数として受け付けるようにする。
    - ⭐️ 参照系のユースケースを実行するためのメソッドの処理は次の順番で進める。
        1. DAOをインスタンス化する。
        2. DAOのインスタンスの参照系のメソッドを呼び出す。1件参照の場合は、参照対象のオブジェクトのIDなどを引数として受け付けるようにして、それをDAOのメソッドに渡すようにする。
        3. 取得したエンティティオブジェクトや`List`を返す。
            - ⭐️⭐️⭐️ エンティティオブジェクトだけでなくメッセージなどもまとめて返したい場合は、結果を1つのオブジェクトにまとめるためのクラスをあらかじめ実装しておく。
- 作成系のユースケースに対応するコントロールについての事柄
    - ⭐️ エンティティオブジェクトの作成に必要な値を引数として受け付けるようにする。
    - ⭐️ 作成系のユースケースを実行するためのメソッドの処理は次の順番で進める。
        1. 引数を用いてエンティティをインスタンス化する。
        2. DAOをインスタンス化する。
        3. DAOのインスタンスの作成系のメソッドにエンティティオブジェクトを渡して呼び出す。
        4. 処理結果を返す。
            - ⭐️⭐️⭐️ エンティティオブジェクトやメッセージなどをまとめて返したい場合は、結果を1つのオブジェクトにまとめるためのクラスをあらかじめ実装しておく。
- 更新系のユースケースに対応するコントロールについての事柄
    - ⭐️ 更新対象のオブジェクトのIDと、更新後の値を引数として受け付けるようにする。
    - ⭐️ 更新系のユースケースを実行するためのメソッドの処理は次の順番で進める。
        1. DAOをインスタンス化する。
        2. DAOのインスタンスの1件参照系のメソッドに、引数として受け取っている更新対象のオブジェクトのIDを渡して呼び出す。
        3. 取得した更新対象のエンティティオブジェクトの更新したいフィールドに対応するセッターに、引数として受け取っている更新後の値を渡して呼び出す。
        4. DAOのインスタンスの更新系のメソッドにエンティティオブジェクトを渡して呼び出す。
        5. 処理結果を返す。
            - ⭐️⭐️⭐️ エンティティオブジェクトやメッセージなどをまとめて返したい場合は、結果を1つのオブジェクトにまとめるためのクラスをあらかじめ実装しておく。
- 削除系のユースケースに対応するコントロールについての事柄
    - ⭐️ 削除対象のオブジェクトのIDを引数として受け付けるようにする。
    - ⭐️ 削除系のユースケースを実行するためのメソッドの処理は次の順番で進める。
        1. DAOをインスタンス化する。
        2. DAOのインスタンスの削除系のメソッドに、引数として受け取っている削除対象のオブジェクトのIDを渡して呼び出す。
        3. 処理結果を返す。
            - ⭐️⭐️⭐️ エンティティオブジェクトやメッセージなどをまとめて返したい場合は、結果を1つのオブジェクトにまとめるためのクラスをあらかじめ実装しておく。
- ⭐️ コントロールが受け付ける引数と、コントロールに対応するサーブレットが受け付けるパラメータの間には、過不足がないようにする。
- ⭐️⭐️⭐️ DAOやエンティティクラスのコンストラクタが例外を投げる可能性がある場合は、try-catch句を用いるとよい。
    - `try`句で、DAOのコンストラクタやメソッド、エンティティクラスのコンストラクタを呼び出すなど、本来進めるべき処理を進める。
    - `catch`句で、例外に応じた処理結果を返したり、さらに例外を投げたりする。

## JSP（ビュー）

- ⭐️ `src/webapp/WEB-INF`以下に配置する。
- ⭐️ すべてのJSPの先頭に`<%@ page language="java" contentType="text/html; charset=UTF-8" %>`を記述する。
- ⭐️ JSTLを用いる場合は次の2行を記述する。（これ以降の説明はJSTLを用いることを仮定しています）
    ```jsp
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
    ```
- JSPの文法についての事柄
    - ⭐️ JSTLタグやHTMLの属性値、HTMLのテキストコンテンツとしてJavaの値を用いたい場合は、その部分をEL式を用いて記述する。
        - 例
            ```jsp
            <p>${1 + 1}</p>
            <p>${"文字列"}</p>
            <p>${fn:escapeXml(requestScope.example.getId())}</p>
            <a href="/systemdesign2024/example/show-one?id=${fn:escapeXml(requestScope.example.getId())}">${fn:escapeXml(requestScope.example.getId())}番の詳細情報</a>
            ```
    - ⭐️ EL式の内部でサーブレットから渡された値を得たい場合は、`requestScope.<キー>`という式を用いる。
        - サーブレットから渡された文字列をそのまま出力する：`${requestScope.<キー>}`
        - サーブレットから渡されたオブジェクトのメソッドの戻り値を出力する：`${requestScope.<キー>.getId()}`
        - サーブレットから渡された文字列を連結して出力する：`${requestScope.<キー> + requestScope.<キー>}`
    - ⭐️ EL式の内部でHTTPリクエストのパラメータを得たい場合は、`param.<パラメータ名>`という式を用いる。
        - パラメータの値をそのまま出力する：`${param.<パラメータ名>}`
    - ⭐️ HTMLの属性値、HTMLのテキストコンテンツにEL式による出力を含めたい場合は、出力する文字列全体に含まれる、HTMLとして意味を持つ特殊文字を、そうでない表現に変換（エスケープ）する。
        - `<式>`をエスケープした結果を出力する：`${fn:escapeXml(<式>)}`
        - サーブレットから渡された文字列をエスケープした結果を出力する：`${fn:escapeXml(requestScope.<キー>)}`
    - ⭐️ リストに含まれる各要素を繰り返し出力したい場合は、`<c:forEach>`タグを用いる。
        - 例
            ```jsp
            <%-- 各要素の`getName()`メソッドの戻り値の一覧 --%>
            <ul>
              <c:forEach var="item" items="${requestScope.exampleList}">
                <li>${fn:escapeXml(item.getName())}</li>
              </c:for>
            </ul>
            ```
    - ⭐️⭐️ EL式を評価した結果によってその部分の出力の有無を変えたい場合は、`<c:if>`タグを用いる。
        - 例
            ```jsp
            <c:if test="${requestScope.example == null}">
              <p>requestScope.exampleがnullの場合、この要素が表示される。</p>
            </c:if>
            <c:if test="${requestScope.example != null}">
              <p>requestScope.exampleがnullではない場合、この要素が表示される。</p>
              <p>requestScope.exampleの内容：${fn:escapeXml(requestScope.example)}</p>
            </c:if>
            ```
    - ⭐️ JSPのEL式で参照しているキーと、フォワード元のサーブレットから渡された値のキーは、一致させる。
    - ⭐️ JSPのEL式で参照しているパラメータ名と、フォワード元のサーブレットが受け付けるパラメータ名は、一致させる。
- リンクについての事柄
    - ⭐️ 別のURLへのハイパーリンクを記述したい場合は、`a`要素を用いる。リンク先のURLは`a`要素の`href`属性で指定する。
        - 例
            ```jsp
            <a href="${fn:escapeXml(pageContext.servletContext.getContextPath())}/example/show-all">一覧を表示</a>
            ```
            ```jsp
            <a href="/systemdesign2024/example/show-all">一覧を表示</a>
            ```
    - ⭐️ `href`属性やリンクテキストにもEL式を使用することができる。
        ```jsp
        <ul>
          <c:forEach var="item" items="${requestScope.exampleList}">
            <li>
              <a href="/${fn:escapeXml(pageContext.servletContext.getContextPath())}/example/show-one?id=${fn:escapeXml(item.getId())}">${fn:escapeXml(item.getName())}さんの詳細情報を表示</a>
            </li>
          </c:for>
        </ul>
        ```
        ```jsp
        <ul>
          <c:forEach var="item" items="${requestScope.exampleList}">
            <li>
              <a href="/systemdesign2024/example/show-one?id=${fn:escapeXml(item.getId())}">${fn:escapeXml(item.getName())}さんの詳細情報を表示</a>
            </li>
          </c:for>
        </ul>
        ```
    - ⭐️ `href`属性のURLに含めるクエリパラメータと、リンク先のURLに対応するサーブレットが受け付けるパラメータには、過不足がないようにする。
- フォームについての事柄
    - ⭐️ 別のURLに対応するサーブレットに入力欄に入力された内容を送信させたい場合は、`form`要素を用いる。
    - ⭐️ HTTPリクエストの送信先のURLは`action`属性で指定する。
    - ⭐️ その`form`要素から送信するHTTPリクエストのメソッドは`method`属性で指定する。
    - ⭐️ 入力欄を設けたい場合は`input`要素を用いる。
        - その入力欄に対応するパラメータの名前は`name`属性で指定できる。例えば`name="id"`と指定すると、その`input`要素の入力値は`id`パラメータの値になる。
        - ⭐️⭐️ `input`要素にラベルを付けたい場合は`input`要素の外側を`label`要素で囲み、`input`要素の前後にラベル用のテキストを記述するとよい。
    - ⭐️ 送信ボタンを設けたい場合は`button`要素を、`type`属性に`submit`を指定して用いる。
    - ⭐️ 用意する`input`要素と、送信先のURLに対応するサーブレットが受け付けるパラメータには、過不足がないようにする。また、`input`要素の`name`属性の値と、サーブレットが受け付けるパラメータの名前は、一致させる。
    - 例
        ```jsp
        <form action="${fn:escapeXml(pageContext.servletContext.getContextPath())}/example/create" method="post">
          <ul>
            <li>
              <label>
                学籍番号：<input type="text" name="id">
              </label>
            </li>
            <li>
              <label>
                名前：<input type="text" name="name">
              </label>
            </li>
          </ul>
          <button type="submit">登録</button>
        </form>
        ```
        ```jsp
        <form action="/systemdesign2024/example/create" method="post">
          <ul>
            <li>
              <label>
                学籍番号：<input type="text" name="id">
              </label>
            </li>
            <li>
              <label>
                名前：<input type="text" name="name">
              </label>
            </li>
          </ul>
          <button type="submit">登録</button>
        </form>
        ```
    - ⭐️⭐️ 1つの項目だけを選択できるラジオボタンを設ける場合は、`input`要素の`type`属性に`radio`を指定して、`value`属性も指定する。選択された項目の`name`属性の値と`value`属性の値のペアがパラメータとして送信される。
        ```jsp
        <li>
          券種：
          <label>
            <input type="radio" name="ticketType" value="adult">大人
          </label>
          <label>
            <input type="radio" name="ticketType" value="child">小人
          </label>
        </li>
        <li>
          性別：
          <label>
            <input type="radio" name="sex" value="male">男性
          </label>
          <label>
            <input type="radio" name="sex" value="female">女性
          </label>
        </li>
        ```
    - ⭐️⭐️ 複数項目を選択できるチェックボックスを設ける場合は、`input`要素の`type`属性に`checkbox`を指定して、`value`属性も指定する。選択された項目の`name`属性の値と`value`属性の値のペアがパラメータとして送信される。
        ```jsp
        <li>
          カスタマイズ：
          <label>
            <input type="checkbox" name="customizations" value="chocolateSauce">チョコレートソース追加
          </label>
          <label>
            <input type="checkbox" name="customizations" value="chocolateChips">チョコレートチップ追加
          </label>
          <label>
            <input type="checkbox" name="customizations" value="espressoShot">エスプレッソショット追加
          </label>
          <label>
            <input type="checkbox" name="customizations" value="coffee">コーヒー増量
          </label>
        </li>
        ```
