set SCRIPT_DIR=%~dp0
java -Xmx1024M -Dsbt.override.build.repos=true -XX:+CMSClassUnloadingEnabled -jar "%SCRIPT_DIR%\project\sbt-launch.jar" %*
