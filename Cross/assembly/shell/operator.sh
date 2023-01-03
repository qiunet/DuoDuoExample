#! /bin/bash
# qiunet
# qiunet@gmail.com
# 2018-01-18 12:08:50
#
#---------------BODY-----------------
cd `dirname $0`
GAME_HOME=$(dirname $(pwd))
# 玩家行为日志目录
GAME_LOGS=${GAME_HOME}/logs
# 系统打印的日志
GAME_SYS_LOGS=${GAME_HOME}/sysLogs
# 热更新目录
GAME_SWAP=${GAME_HOME}/classes

red(){
    echo -e "\033[31m\033[01m$1\033[0m"
}

if [ "${JAVA_HOME}" = "" ]; then
        red "=================没有设置JAVA_HOME================="
        red "=================没有设置JAVA_HOME================="
        red "=================没有设置JAVA_HOME================="
        exit 1
fi

if [[ ! -d ${GAME_LOGS} ]];then mkdir -p ${GAME_LOGS} ; fi
if [[ ! -d ${GAME_SWAP} ]];then mkdir -p ${GAME_SWAP} ; fi
if [[ ! -d ${GAME_SYS_LOGS} ]];then mkdir -p ${GAME_SYS_LOGS} ; fi

heap_ops="-Xmx512m -Xms256m -Xmn300m -Xss256k -XX:MaxDirectMemorySize=256m"

JAVA_OPTS="-server\
  ${heap_ops}\
 -Dlog.dir=${GAME_LOGS}\
 -DhotSwap.dir=${GAME_SWAP}\
 -Dio.netty.recycler.ratio=0\
 -DsysLogs.dir=${GAME_SYS_LOGS}\
 -XX:-OmitStackTraceInFastThrow\
 -XX:+HeapDumpOnOutOfMemoryError\
 -Dfile.encoding=UTF-8\
 -XX:HeapDumpPath=dumps/"

CLASSPATH="."
CLASSPATH="${CLASSPATH}:${JAVA_HOME}/lib/tools.jar"
CLASSPATH="${CLASSPATH}:${GAME_HOME}/common-lib/*"
CLASSPATH="${CLASSPATH}:${GAME_HOME}/lib/*"
CLASSPATH="${CLASSPATH}:${GAME_HOME}/conf"

BOOTSTRAP_CLASS="com.game.example.cross.CrossServerBootStrap"


start(){
       nohup java ${JAVA_OPTS}  -classpath ${CLASSPATH} ${BOOTSTRAP_CLASS} start >> /dev/null 2>&1 &
}

other(){
      java -classpath ${CLASSPATH} ${BOOTSTRAP_CLASS} "$1" &
        sleep 5
}


case "$1" in
        start)
                start
        ;;
        stop)
                other "$1"
        ;;
      	restart)
                other "stop"
                start
              ;;
        reload)
                other "RELOAD"
        ;;
        hotswap)
                other "$1"
        ;;
        *)
               other "$1"
        ;;
esac
