xcopy /S /Y /Q target\classes  %JBOSS_HOME%\server\mytest\deploy\gwtbooking.war\WEB-INF\classes
xcopy /S /Y /Q src\main\resources %JBOSS_HOME%\server\mytest\deploy\gwtbooking.war\WEB-INF\classes
xcopy /S /Y /Q src\main\webapp\WEB-INF %JBOSS_HOME%\server\mytest\deploy\gwtbooking.war\WEB-INF
xcopy /S /Y /Q src\main\webapp %JBOSS_HOME%\server\mytest\deploy\gwtbooking.war