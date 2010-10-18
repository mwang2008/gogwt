Prefix:
 gwtdemo is used to show how to use Struts like GWT navigation.
 
Assumption:
  maven is used for your project.

Enviroment: 
  GWT 2.0.3
  Java 1.5
  Eclipse: Helio
  Maven: 2.1.0
  App Server: JBoss
  
1. download gogwtarch_1.0.jar from http://code.google.com/p/gogwtrelease/downloads/list to local directory, ex d:\temp from 

2. install d:\temp\gogwtarch_1.0.jar into local maven repository
   cd d:\temp
   mvn install:install-file -DgroupId=com.gogwt.framework -DartifactId=gogwtarch -Dversion=1.0 -Dpackaging=jar -Dfile=d:\temp\gogwtarch_1.0.jar

3. download gogwtdemo_1.0.zip from http://code.google.com/p/gogwtrelease/downloads/list   
   
4. compile and deploy
   mvn clean package 
   or
   mvn clean install
   then copy gogwtdemo.war from target to app server deploy directory

5. start app server

6. test with following url: assume port is 80
   http://localhost/gogwtdemo/show
   http://localhost/gogwtdemo/show?gwt.codesvr=127.0.0.1:9997
   
---------------------------------------------
1. Import project to Eclipse, in d:\gogwt\gogwtdemo  
     mvn eclipse:clean
     mvn eclipse:eclipse
     
In Eclipse doing: 
File --> Import --> General --> Existing Projects into Workspace
Click Next and Browse to the d:\gogwtdemo directory

   