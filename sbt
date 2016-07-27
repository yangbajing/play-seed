SCRIPT_DIR=`dirname $0`
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=49999  -Xmx1024M -Dfile.encoding=UTF-8 -XX:+CMSClassUnloadingEnabled -jar "$SCRIPT_DIR/project/sbt-launch.jar" $@
