@echo off
echo "----------------------------------"
if "%JAVA_HOME%"=="" (
	echo "-------------- JAVA_HOME NOT EXIST --------------"
	echo "-------------- JAVA_HOME NOT EXIST --------------"
	echo "-------------- JAVA_HOME NOT EXIST --------------"
	pause
	exit
)

echo "JAVA_HOME:" %JAVA_HOME%
cd %~dp0
cd ..
set GAME_PATH=%cd%
set CLASSPATH1=%JAVA_HOME%/lib/tools.jar
set CLASSPATH2=%GAME_PATH%/common-lib/*
set CLASSPATH2=%GAME_PATH%/lib/*
set CLASSPATH3=%GAME_PATH%/conf/
set CLASSPATH=".;%CLASSPATH1%;%CLASSPATH2%;%CLASSPATH3%"
set MAIN_CLASS="com.xy.game.server.cross.CrossServerBootStrap"

set GAME_LOGS=%GAME_PATH%/logs
set GAME_SYS_LOGS=%GAME_PATH%/sysLogs
set GAME_SWAP=%GAME_PATH%/classes

set JAVA_OPTS=-server -Xmx512m -Xms512m -Xmn300m -Xss256k -XX:MaxDirectMemorySize=1g -XX:+UseParallelGC -XX:+UseParallelOldGC -Dlog.dir=%GAME_LOGS% -DhotSwap.dir=%GAME_SWAP% -DsysLogs.dir=%GAME_SYS_LOGS% -XX:-OmitStackTraceInFastThrow -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -XX:HeapDumpPath=dumps/

set OP=%1
if "%OP%"=="" (
    set OP=restart
)

if "%OP%"=="start" (
	echo "start"
	call:run start
) ^
else if "%OP%"=="stop" (
	echo "stop"
	call:run stop
) ^
else if "%OP%"=="restart" (
	echo "restart"
	call:run stop
	timeout /t 2 /nobreak > NUL
	call:run start
) ^
else if "%OP%"=="reload" (
	echo "reload"
	call:run RELOAD
) ^
else (
	echo "arg error"
)
echo "----------------------------------"
echo "end"
echo "----------------------------------"
goto:eof

:run
echo "run" %1
java %JAVA_OPTS% -classpath %CLASSPATH% %MAIN_CLASS% %1
goto:eof
