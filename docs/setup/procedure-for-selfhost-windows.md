# 【🪟Windowsセルフホスト用】開発環境の構築・使用方法

## 🧭 Quick Links

- [作業を再開する](#-作業を再開するには)
- [作業を終了する](#-作業を終了するには)

## 👉 開発環境の構築手順

大まかな手順は次の通りです。

1. 必要なソフトウェアをインストールする。
    - **Visual Studio Code（VSCode）**: 開発コンテナーに接続できるエディタ。
    - **Git**: 開発環境のテンプレートの取得に用いるツール。
    - **Windows用Linuxサブシステム（WSL）**: Windows上でLinuxを実行するための仕組み。
    - **Docker Desktop**: WSL上で動作するコンテナー実行環境およびそのフロントエンド。
2. コンテナー実行環境を設定および起動する。
3. 開発に必要な一切がインストールされたコンテナーを開発コンテナーとして作成する。
4. 作成した開発コンテナーにVSCodeを接続する。

### 1. 必要なソフトウェアのインストール

> [!NOTE]
> 2024年度以降に櫨山研究室に配属された学生はこの手順を行う必要はありません。

1. ターミナルまたはWindows PowerShellを起動します。
    - タスクバーの`スタート`を右クリック→`ターミナル`または`Windows PowerShell`をクリックします。
2. 次のスクリプトをPowerShellで実行してください。下記内容をコピーして、PowerShellのウィンドウを右クリックして貼り付けることもできます。すでにこれらのソフトウェアをインストールしている場合は、適宜スクリプトの内容を変更して実行してください。
    ```ps1
    # 1行ずつ貼り付けてください
    # VSCode, Git, Docker Desktop をインストール
    winget install -e --id Microsoft.VisualStudioCode
    winget install -e --id Git.Git
    winget install -e --id Docker.DockerDesktop
    # WSLをインストール
    wsl --install --no-distribution
    ```
3. インストールが完了したら（完了すると最後の行の左端が`PS C:\Users\user>`のようなプロンプトに戻ります）、インストールしたアプリが正しく動作することを確認します。
    - VSCode: `スタート`→`すべてのアプリ`内に`Visual Studio Code`が追加されていて、起動できることを確認します。
    - Git: 新しいPowerShellのウィンドウを開き、次のコマンドをPowerShellで実行します。正しく動作する場合は`git version 2.54.0`のように表示されます。
        ```ps1
        git -v
        ```
    - Docker Desktop: 新しいPowerShellのウィンドウを開き、次のコマンドをPowerShellで実行します。正しく動作する場合は`Docker version 29.5.2, build v29.5.2`のように表示されます。
        ```ps1
        docker -v
        ```

### 2. Docker Desktopを起動・設定する

1. Docker DesktopはWSL（Windows Subsystem for Linux）を使用します。WSLがPCのメモリ等を消費しすぎないように、WSLを設定します。
    1. WSL Settingsを起動します。タスクバーの`スタート`→`すべてのアプリ`→`WSL Settings`をクリックします。
    2. 左側メニューの`メモリとプロセッサ`をクリックします。
    3. `メモリ サイズ`を`4096 MB`にします。
    4. `スワップ サイズ`を`0 MB`にします。
    5. ウィンドウ右下の`変更の適用`をクリックします。`WSL のシャットダウンが必要です`と表示された場合は`WSL を今すぐシャットダウン`をクリックしてWSLをシャットダウンします。
2. PCを再起動します。タスクバーの`スタート`→`電源`→`再起動`をクリックします。
3. Docker Desktopを起動します。
    - タスクバーの`スタート`→`すべてのアプリ`→`Docker Desktop`をクリックします。
    - Dockerアカウントへのログインを求める画面が表示されることがありますが、Dockerアカウントを作成したりログインしたりする必要はありません。
    - アンケートが表示されることがありますが、答える必要はありません。

### 3. 開発コンテナーを作成し、作成した開発コンテナーに接続する

1. VSCodeを起動します。
2. VSCode上で開発コンテナーを作成するために`Dev Containers`拡張機能をインストールします。`Ctrl+Shift+P`（`Ctrl`キーと`Shift`キーを押しながら`P`キーを押す）を押してコマンドパレットを開き、**`>`を消してから**`ext install ms-vscode-remote.remote-containers`と入力して`Enter`キーを押します。
3. 開発コンテナーを作成します。
    1. `Ctrl+Shift+P`を押してコマンドパレットを開き、`Dev Containers: Clone Repository in Container Volume`と入力して`Enter`キーを押します（途中まで入力して同名の項目を選択しても構いません）。
    2. 入力欄に`https://github.com/HazeyamaLab/systemdesign2024`と入力して`Enter`キーを押します。（このURLをコピーするためにVSCode以外のウィンドウを触ると、コマンドパレットが閉じられてしまうので、注意してください）
    3. 開発コンテナーの作成が完了するまで待ちます。開発コンテナーの作成には数分間かかることがあります。作成に成功すると自動的に開発コンテナーに接続され、ウィンドウ左下のリモートボタンに`Dev Container: systemdesign2024`と表示されます。

## ℹ️ 開発環境の使用方法

このページにはご自身のPC上で作成した開発コンテナー特有の事柄を記します。それ以外の事柄は[README.md](../../README.md#ℹ️-開発環境の使い方)をご覧ください。

### 🧐 作業を終了するには？

VSCodeと開発コンテナーの接続を切断します。VSCodeのメニューバーの`File`（`ファイル`）→`Close Remote Connection`（`リモート接続を閉じる`）をクリックします。その後、VSCodeのウィンドウを閉じてVSCodeを終了します。

### 🧐 作業を再開するには？

Docker Desktopを起動してからVSCodeを開発コンテナーに接続します。

1. Docker DesktopとVSCodeを起動します。
2. VSCodeのウィンドウ左側の`Remote Explorer`（`リモート エクスプローラー`・`🖥️`のようなアイコン）をクリックして、リモートエクスプローラーを表示します。
3. `Remote (Tunnel/SSH)`（`リモート (トンネル/SSH)`）の一覧が表示されている場合は、プルダウンで`Dev Containers`（`開発コンテナー`）の一覧を表示するように切り替えます。
4. `Dev Containers`（`開発コンテナー`）の一覧から`systemdesign2024_devcontainer-devcontainer-1`を選び、`→`（`Open Container in Current Window`、`現在のウィンドウのコンテナーで開く`）または`Open Container in New Window`（`新しいウィンドウのコンテナーで開く`）をクリックします。
5. 開発コンテナーへの接続に成功すると、ウィンドウ左下のリモートボタンに`Dev Container: systemdesign2024`と表示されます。
6. `mysql`コンテナーや`adminer`コンテナーの機能を利用する場合は、`Other Containers`の一覧の`systemdesign2024_devcontainer-mysql-1`および`systemdesign2024_devcontainer-adminer-1`が起動していることを確認します。
    - 起動している場合は`▶️`のようなアイコンが付いています。
    - 起動していない場合は右クリック→`Start Container`（`コンテナーの開始`）をクリックします。

### 🧐 Docker Desktopを起動しているのに開発コンテナーにVSCodeを接続できない

作業を再開しようとしてVSCodeの開発コンテナーの一覧を確認しても開発コンテナーが表示されなかったり、`Docker からエラーが返されました。Docker デーモンが実行されていることを確認します。`と表示されたり、することがあります。

この場合、Docker Desktopが休止状態になっている可能性があります。
休止状態から復帰するには、スタートメニューからDocker Desktopを起動するなどして、Docker Desktopのウィンドウをもう一度開いてください。

### 🧐 Docker DesktopおよびWSLを終了するには？

1. Docker Desktopを終了します。タスクバーの通知領域の`Docker Desktop`のアイコンを右クリック→`Quit Docker Desktop`をクリックします。
2. Docker Desktopが終了したのを確認してからWSLを終了します。ターミナルまたはPowerShellでコマンド`wsl --shutdown`を実行します。

### 🧐 開発コンテナーに割り当てられているメモリが足りない。「`Reconnecting to Devcontainer…`」が頻発する。

- WSL Settingsで、WSLが使用できるメモリのサイズの上限を引き上げてください。[2. Docker Desktopを起動・設定する](#2-docker-desktopを起動設定する)の手順を参考にしてください。WSL Settingsの変更を反映するには、一度[Docker DesktopとWSLを終了する](#-docker-desktopおよびwslを終了するには)必要があります。
- また、不要な拡張機能を無効化するのも効果的です。
    1. VSCodeのウィンドウ左側の`Extensions`（`拡張機能`）をクリックします。
    2. `Dev Container: …`内の以下の拡張機能を右クリック→`Disable`（`無効にする`）をクリックします。
        - Gradle for Java
        - ESLint
        - Maven for Java

