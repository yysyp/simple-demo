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
#Method1: Run & Start: script/buildk8s.bat 
#OR Git Bash at script folder & run: ./buildk8s.sh
cd script
buildk8s.bat / ./buildk8s.sh
Swagger Doc Visit:
http://localhost:30080/springdoc/docs.html
http://localhost:30080/springdoc/api-docs.yaml

##"deployable" folder content is copy from target, conf & script folders and Dockerfile.
##Quick deploy to cloud for test:
1) Mvn clean package to build jar in target folder
2) Update script/deployJar.sh and script/k8s-gcr.yaml set correct "image ver"
3) run script/build-deployable script to generate the deployable folder
4) Winscp or scp to copy deployable folder to server
5) run deployJar.sh
6) check by kubectl logs:
kubectl -n app logs $(kubectl -n app get pods --field-selector status.phase=Running --no-headers -o custom-columns=":metadata.name" | grep simple-demo-web | head -1)
   
#Method2: 
2.a) build-deployable: copy jar & script etc. to deployable.
2.b) deploy-deployable: create image from jar and deploy image.

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

##pslab/Kuaima.java is a thymeleaf based quick code generator.
src/main/java/pslab/Kuaima.java
1, Modify tfsource/tf.properties
    1.a) packageName is the target package name
    1.b) moduleName is the target module name
    1.c) entityJson for defining the table structure.
2, Run Kuaima.main method.
3, Start springboot & visit: http://localhost:8080/api/{moduleName}/{table-name}

