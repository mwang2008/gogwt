1. mvn clean package

2. copy target\hessian_webapp.war to your web server, such as jboss, deploy directory

3. Start jboss:

4. in web broswer type in: http://localhost/hessian_webapp/hello
got: Hessian Requires POST

5. Run client BasicClient
mvn -Pclient

In eclipse: 
run BasicClient 

