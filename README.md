# DuoDuoExample
DuoDuo 示例项目
> [DuoDuo框架](https://github.com/qiunet/DuoDuo)

## 开发环境
* jdk 17
* DuoDuo版本：7.0.9 
* maven 3.6.3
* IntelliJ IDEA 2022

## 项目部署
* 环境准备：Linux系统(必须安装MySQL 和 Redis; git 和 maven可以按需安装)
* 源码下载：git clone https://github.com/qiunet/DuoDuoExample.git
* 第一次打包准备： 
  取消Login、Server、Cross目录下的assembly.xml文件中的注释，同时注释不生成server.conf的配置
  打包完成后还原配置，以后打包不需要生成。
  ![assembly.xml配置](.img/assembly.png)
* 编译打包：mvn package -DskipTests
* 复制打包好的文件到指定目录并解压：
  > Server/target/Server-1.0.0.zip
  
  > Login/target/Login-1.0.0.zip
  
  > Cross/target/Cross-1.0.0.zip
  
  ![解压后目录结构](.img/project.png)
  > 根据需要修改server.conf的服务器配置
  
* 运行：
  > 执行上一步解压的三个目录下bin文件夹中的operator.sh文件，参数start
  
  > 如：LoginServer/bin/operator.sh start
  
  > 执行日志生成在项目目录下的sysLogs文件夹中
  ![启动日志](.img/start.png)
  (注：如果是从windows打包的，需要设置operator.sh为可执行文件，同时用vim修改换行符set ff=unix)
  
* 重启或停止：
  执行operator.sh文件，参数restart 或 stop
  
* 优雅关停：
  执行operator.sh文件，参数deprecated
  > 执行后会通知所有在线玩家客户端，该服务器要关闭了。在适当的时机退出，重新请求登录到别的服务器。当所有在线玩家都退出后，该服务器才停止。
* 热更配置
  执行operator.sh文件，参数reload
  > 在Windows环境下，监听了配置文件变化，当文件变化后自动加载更新配置。在Linux环境下，替换配置文件后，需要手动触发配置更新。
* 热更class
  执行operator.sh文件，参数hotswap
  > 在服务器运行状态下，在classes目录下放入编译好的class文件，执行指令，即可热更相应的class

## DuoDuo示例项目细节
* [LoginServer细节](Login/README.md)
* [GameServer细节](Server/README.md)
* [CrossServer细节](Cross/README.md)
* 同时可以参考[使用手册](http://www.github.com/qiunet/DuoDuo/wiki)


