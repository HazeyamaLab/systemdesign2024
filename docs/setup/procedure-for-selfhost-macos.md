# 【🍎macOSセルフホスト用】開発環境の構築・使用方法

## 👉 開発環境の構築手順

大まかな手順は次の通りです。

1. 必要なソフトウェアをインストールする。
    - **Visual Studio Code（VSCode）**: 開発コンテナーに接続できるエディタ。
    - **Git**: 開発環境のテンプレートの取得に用いるツール。
    - **Colima**: macOSの仮想マシンサービス上で動作する仮想マシンで、コンテナー実行環境のDockerが付属している。
    - **Docker Desktop**: Dockerのフロントエンド。
2. コンテナー実行環境を起動する。
3. 開発に必要な一切がインストールされたコンテナーを開発コンテナーとして作成する。
4. 作成した開発コンテナーにVSCodeを接続する。

### 1. 必要なソフトウェアのインストール

> [!NOTE]
> 2024年度に櫨山研究室に配属された学生はこの手順を行う必要はありません。

1. ターミナルを起動します。
    - Dockの`Launchpad`をクリック→`その他`→`ターミナル`の順にクリックします。
    - または、Finderを開き、メニューバーの`移動`→`アプリケーション`をクリックして`アプリケーション`フォルダを開きます。`アプリケーション`フォルダ内の`ユーティリティ`フォルダを開き、その中の`ターミナル`をダブルクリックします。
2. 次のスクリプトをターミナルで実行してください。下記内容をコピーして、ターミナルのウィンドウを右クリックして貼り付けることもできます。すでにこれらのソフトウェアをインストールしている場合は、適宜スクリプトの内容を変更して実行してください。実行中にパスワードが求められた場合は、PCにログインする際のパスワードを入力してください。
    ```sh
    # コマンドラインでソフトウェアをインストールするために Homebrew をインストール
    /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
    echo 'eval "$(/opt/homebrew/bin/brew shellenv)"' >> ~/.zprofile
    eval "$(/opt/homebrew/bin/brew shellenv)"
    
    # VSCode, Git, Git Credential Manager, Colima, Docker Desktop をインストール
    brew install --cask visual-studio-code
    brew install git
    brew install --cask git-credential-manager
    brew install colima
    brew install --cask docker
    ```
3. インストールが完了したら（完了すると最後の行の左端が`user@macbook ~ %`のようなプロンプトに戻ります）、インストールしたソフトウェアが正しく動作することを確認します。
    - VSCode: `アプリケーション`フォルダまたは`Launchpad`に`Visual Studio Code`が追加されていて、起動できることを確認します。
    - Git: 次のコマンドをターミナルで実行します。正しく動作する場合は`git version 2.44.0`のように表示されます。
        ```sh
        git -v
        ```
    - Colima: 次のコマンドをターミナルで実行します。正しく動作する場合は`colima version 0.6.8`のように表示されます。
        ```sh
        colima --version
        ```
    - Docker Desktop: 次のコマンドをターミナルで実行します。正しく動作する場合は`Docker version 26.1.1, build 4cf5afa`のように表示されます。
        ```sh
        docker -v
        ```

### 2. Colimaを起動する

1. ターミナルで次のコマンドを実行します。ここでは、Colimaに3GBのメモリと16GBのストレージを割り当てています。起動に成功すると`INFO[0013] done`のように表示されます。
    ```sh
    colima start -m 3 -d 16
    ```

### 3. 開発コンテナーを作成し、作成した開発コンテナーに接続する

1. VSCodeを起動します。
    - タスクバーの`スタート`→`すべてのアプリ`→`Visual Studio Code`をクリックします。
2. VSCode上で開発コンテナーを作成するために`Dev Containers`拡張機能をインストールします。`⇧⌘P`（`⇧`キーと`⌘`キーを押しながら`P`キーを押す）を押してコマンドパレットを開き、**`>`を消してから**`ext install ms-vscode-remote.remote-containers`と入力して`Enter`キーを押します。
3. 開発コンテナーを作成します。
    1. `⇧⌘P`を押してコマンドパレットを開き、`Dev Containers: Clone Repository in Container Volume`と入力して`Enter`キーを押します（途中まで入力して同名の項目を選択しても構いません）。
    2. 入力欄に`https://github.com/HazeyamaLab/systemdesign2024`と入力して`Enter`キーを押します。
    3. 開発コンテナーの作成が完了するまで待ちます。開発コンテナーの作成には数分間かかることがあります。作成に成功すると自動的に開発コンテナーに接続され、画面左下のリモートボタンに`Dev Container: systemdesign2024`と表示されます。

## ℹ️ 開発環境の使用方法

このページにはご自身のPC上で作成した開発コンテナー特有の事柄を記します。それ以外の事柄は[README.md](../../README.md#-開発環境の使い方)をご覧ください。

### 🧐 作業を終了するには？

VSCodeと開発コンテナーの接続を切断します。メニューバーの`File`（`ファイル`）→`Close Remote Connection`（`リモート接続を閉じる`）をクリックします。その後、VSCodeのウィンドウを閉じてVSCodeを終了します。

### 🧐 作業を再開するには？

Colimaを起動してからVSCodeを開発コンテナーに接続します。

1. ColimaとVSCodeを起動します。
    - Colimaを起動するには、ターミナルでコマンド`colima start -m 3 -d 16`を実行します。
2. VSCodeの画面左側の`Remote Explorer`（`リモート エクスプローラー`・`🖥️`のようなアイコン）をクリックして、リモートエクスプローラーを表示します。
3. `Remote (Tunnel/SSH)`（`リモート (トンネル/SSH)`）の一覧が表示されている場合は、プルダウンで`Dev Containers`（`開発コンテナー`）の一覧を表示するように切り替えます。
4. `Dev Containers`（`開発コンテナー`）の一覧から`systemdesign2024_devcontainer-devcontainer-1`を選び、`→`（`Open Container in Current Window`、`現在のウィンドウのコンテナーで開く`）または`Open Container in New Window`（`新しいウィンドウのコンテナーで開く`）をクリックします。
5. 開発コンテナーへの接続に成功すると、画面左下のリモートボタンに`Dev Container: systemdesign2024`と表示されます。
6. `mysql`コンテナーや`adminer`コンテナーの機能を利用する場合は、`Other Containers`の一覧の`systemdesign2024_devcontainer-mysql-1`および`systemdesign2024_devcontainer-adminer-1`が起動していることを確認します。
    - 起動している場合は`▶️`のようなアイコンが付いています。
    - 起動していない場合は右クリック→`Start Container`（`コンテナーの開始`）をクリックします。

### 🧐 Colimaを終了するには？

ターミナルでコマンド`colima stop`を実行します。

### 🧐 開発コンテナーに割り当てられているメモリが足りない。「`Reconnecting to Devcontainer…`」が頻発する。

Colimaが使用できるメモリのサイズの上限を引き上げてください。
一度[Colimaを終了して](#colimaを終了するには)から、[2. Colimaを起動する](#2-colimaを起動する)の手順を参考にして、Colimaにより大きいメモリを割り当てて起動してください。
