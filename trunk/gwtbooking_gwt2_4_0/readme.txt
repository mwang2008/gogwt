1. compile/deploy
mvn clean -DskipTests=true install

myBuild.bat   only compile nogwt code.

2. execute: in Jboss_home bin 
myStart.bat

3. web mode: in broswer: for mvp 
http://local.www.gogwt.com/gwtbooking/en-us/mvpreservation
http://local.www.gogwt.com/gwtbooking/en-US/gwtreservation

4. develope mode
Right click ReservationMVPModule.gwt.xml and select Run As or Debug As then Web Application
http://local.www.gogwt.com/gwtbooking/en-us/mvpreservation?gwt.codesvr=127.0.0.1:9997
http://local.www.gogwt.com/gwtbooking/en-us/gwtreservation?gwt.codesvr=127.0.0.1:9997
http://local.www.gogwt.com/gwtbooking/en-us/mvphoteldetail/120284?gwt.codesvr=127.0.0.1:9997

5. compile gwt only
mvn gwt:compile
 xcopy /S /Y /Q *<YOUR-PROJECT-PATH>*target\hi\com.ihg.dec.apps.hi.gwt.reservationentry.ReservationProcessEntryLOCAL  %RESIN_HOME%\www\app\deploy\hi\com.ihg.dec.apps.hi.gwt.reservationentry.ReservationProcessEntryLOCAL

6. Resource Bundles
mvn process-resources

mvn aspectj:compile


7. compile java files
mvn clean compiler:compile
mvn aspectj:compile
xcopy /S /Y /Q target\classes  %JBOSS_HOME%\server\mytest\deploy\gwtbooking.war\WEB-INF\classes

myJavaBuild.bat
myJavaCopy.bat

myStep1_JavaBuild.bat
myStep2_AspectJBuild.bat
myStep3_JavaCopy.bat


http://blog.jeffdouglas.com/2010/02/11/uibinder-with-suggestbox-multiwordsuggestoracle/
http://blog.jeffdouglas.com/2010/02/24/gwt-uibinder-passing-objects-to-widgets/

Notes:
the Google map key is defined in jsp page as uiBinder has problem of using ASYNC call 

In uiBinder, it is limiation
Field address cannot appear multiple times in one template

http://www.informationweek.com/news/business_intelligence/information_mgt/showArticle.jhtml;jsessionid=M0LS4PVSQLLVHQE1GHPSKHWATMY32JVN?articleID=227300422
IHG's reservation system now gets about 30 million availability requests a day.