#!bin/sh

LIBPATH=../lib
BUILDPATH=../build
CLASSPATH=.
echo ${CLASSPATH}

CLASSPATH=${CLASSPATH}:${BUILDPATH}/vmdemo.jar
echo ${CLASSPATH}
CLASSPATH=${CLASSPATH}:${LIBPATH}/apache/log4j-1.2.8.jar:${LIBPATH}/apache/commons-email-1.0.jar:${LIBPATH}/apache/commons-collections-2.1.1.jar
CLASSPATH=${CLASSPATH}:${LIBPATH}/velocity/velocity-1.4.jar
CLASSPATH=${CLASSPATH}:${LIBPATH}/javax/mail-1.3.2.jar:${LIBPATH}/javax/activation-1.0.2.jar

echo ${CLASSPATH}
java -Dlog4j.configuration=log4j.properties -cp ${CLASSPATH} com.vm.test.TestVelocity $1