Refer to static/index.html


sample form snippet:
<form action="/api/qnpoll/YourQuestionnaireUri/result" method="post">
    name: <input type="text" name="name" /><br/>
    age: <input type="number" name="age" /><br/>
    married: <input type="checkbox" name="married" /><br/>
    hobby:
    <select name="hobby" multiple>
        <option value="reading">reading</option>
        <option value="play">play</option>
        <option value="movie">movie</option>
        <option value="music">music</option>
    </select>
    <br/>
    job:
    <select name="job">
        <option value="job1">job1</option>
        <option value="job2">job2</option>
        <option value="job3">job3</option>
    </select><br/>
    <input type="submit" name="Submit" />
</form>

---------------------------------------------------------------------------------------------
#Run ps.demo.WebServerApplication
health: http://localhost:8080/healthz
http://localhost:8080/
Swagger Doc Visit:
http://localhost:8080/springdoc/docs.html
http://localhost:8080/springdoc/api-docs.yaml

---------------------------------------------------------------------------------------------
#Run & Start: script/buildk8s.bat
Swagger Doc Visit:
http://localhost:30080/springdoc/docs.html
http://localhost:30080/springdoc/api-docs.yaml

#Stop: kubectl delete namespace app
---------------------------------------------------------------------------------------------
#Copy dependencies from maven:
1) run command: mvn install dependency:copy-dependencies 
2) find the jars in: target/dependency

#Del folder and content & Copy folder and content
rd /S /Q %doc3%\
xcopy /Y /S /E /H /R /C simple-demo\*.* %doc3-simple-demo%\

#Build normal fat jar:
1, update the <mainClass> value in pom.xml
2, run mvn clean package -D spring-boot.repackage.skip=true -D fat.jar=true
3, get the fat jar in: target/simple-demo-1.0.0-jar-with-dependencies.jar