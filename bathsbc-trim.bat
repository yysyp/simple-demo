@REM tess4j & selenium

del /f src\test\java\ps\demo\util\SeleniumTest.java
del /f src\test\java\ps\demo\util\SeleniumTest2.java
del /f src\test\java\ps\demo\util\Tess4JTest.java
del /f src\main\java\ps\demo\util\SwingTool.java
del /f src\main\java\ps\demo\util\MyWebDriverUtil.java

del pom.xml
ren pom-small.xml pom.xml