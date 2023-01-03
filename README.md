# DuoDuoExample
DuoDuo 示例项目

## 开发环境
* jdk 17
* DuoDuo版本：7.0.9 
* maven 3.6.3
* IntelliJ IDEA 2022

## 项目部署
* 环境准备：Linux系统（需要安装MySQL 和 Redis）
* 源码下载：git clone https://github.com/qiunet/DuoDuoExample.git
* 第一次打包准备： 
  取消Login、Server、Cross目录下的assembly.xml文件中的注释，同时注释不生成server.conf的配置“<exclude>server.conf</exclude>”
  打包完成后还原配置，以后打包不需要生成。
* 编译打包：mvn package -DskipTests
* 复制打包好的文件到指定目录并解压：
  Server/target/Server-1.0.0.zip
  Login/target/Login-1.0.0.zip
  Cross/target/Cross-1.0.0.zip
  根据需要修改server.conf的服务配置信息
  
* 运行：
    执行上一步解压的三个目录下的operator.sh文件
    如：LoginServer/bin/operator.sh start
    执行日志生成在项目目录下的sysLogs文件夹中
  (注：如果是从windows打包的，需要设置operator.sh为可执行文件，同时用vim修改换行符set ff=unix)
  

## DuoDuo示例项目细节
* [LoginServer细节](Login/README.md)
* [GameServer细节](Server/README.md)
* [CrossServer细节](Cross/README.md)
* 同时可以参考[使用手册](http://www.github.com/qiunet/DuoDuo/wiki)


