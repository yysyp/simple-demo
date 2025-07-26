---------------------------------------------------------------------------------------------
##Set up prod environment:
1) Create database name in i.e. Mysql and config the name in: "src/main/resources/application-prod.yml"
2) Set "ddl-auto: create" in: "src/main/resources/application-prod.yml"
3) Set "ddl-auto: none" in: "src/main/resources/application-prod.yml"
4) Run initsql/dml.sql to create user and role.
5) Start up the application and visit:
   http://localhost:8080/index.html
   http://localhost:8080/healthz

6) visit http://localhost:8080/api/login/ and use admin/12345 to login   

---------------------------------------------------------------------------------------------
##Set up dev environment.
1) set spring.profiles.active: dev And then start the project.
Open H2 to set the login user data in database:
http://localhost:8080/h2-console/login.jsp
url: jdbc:h2:file:./ignorefolder/h2/simpledemo
username: sa
password:

2) Run below sql to set admin Username: admin, Password: 12345
INSERT INTO login_user
(id, created_by, is_active, is_logical_deleted, modified_by, comments, company, department, disabled, email, failed_count, first_name, last_login_ip, last_name, password, phone, salute, sex, user_name, version)
VALUES(0, 'sys', 1, 0, 'sys', '', '', '', 0, '', 0, '', '', '', '827CCB0EEA8A706C4C34A16891F84E7B', '', '', '', 'admin', 0);
commit;
   
3) visit http://localhost:8080/api/login/ and use admin/12345 to login
---------------------------------------------------------------------------------------------


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
http://localhost:8080/index.html
Swagger Doc Visit:
http://localhost:8080/springdoc/docs.html
http://localhost:8080/springdoc/api-docs.yaml

---------------------------------------------------------------------------------------------
#Method1: Run & Start: 
    1) cd to script folder. 
    2) run "buildk8s.sh" (Can use Git Bash to run) or "buildk8s.bat"
    3) visit swagger:
        http://localhost:30080/springdoc/docs.html
        http://localhost:30080/springdoc/api-docs.yaml

##"deployable" folder content is copy from target, conf & script folders and Dockerfile.
##Quick deploy to cloud for test:
1) Mvn clean package to build jar in target folder
2) run script/build-deployable script to generate the deployable folder
4) Winscp or scp to copy deployable folder to server
5) run deployJar.sh
6) check by kubectl logs:
kubectl -n app logs $(kubectl -n app get pods --field-selector status.phase=Running --no-headers -o custom-columns=":metadata.name" | grep simple-demo-web | head -1)
   
#Method2: 
2.a) build-deployable: copy jar & script etc. to deployable.
2.b) deploy-deployable: create image from jar and deploy image.

#Stop: kubectl delete namespace app
---------------------------------------------------------------------------------------------
#Build frontend:
cd frontend
cnpm i
cnpm run build
//cnpm run build:sit

Below "umi/index.html" no need anymore, as the config "publicPath: '/umi/'" can do the trick.
#--Copy files in "src/main/resources/static/umi" to "src/main/resources/static" OR 
#--Modify "src/main/resources/static/umi/index.html":
#--a) href="/umi... TO href="/umi/umi...
#--b) src="/umi... TO src="/umi/umi...

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

##Page fetching:
doc3\3-learn\33-coding\337-mytool-demo-snippet-code\myiconclick\src\ps\demo\OreillyFetch.java

##http://localhost:8080/umi/index.html#/stock
#new stock table: new_stock_data structure:
kemu: item of financial reports (debt, balance/benefit, cashflow)
kemuType: debt or benefit or cash
kemu_value: item's value
yoy: year on year value of this kemu value.
pctInAssetOrRevenue: for debt kemu, the value will be (kemu value / total assets value); for benefit kemu, the value will be (kemu value / income)
coreProfitOnAssetEffect: the coreProfitOnAsset delta caused by this kemu value yoy difference.

import excels in "newstock-import-samples" downloaded from: http://stockpage.10jqka.com.cn/600519/finance/#finance
and these excels can be parsed and import into new_stock_data

##Useful tools:
src/main/java/pslab/Kuaima.java -- auto CRUD
src/main/java/pslab/NativeHookDemo.java -- UI record key & mouse and run
src/main/java/pslab/Main.java -- act as command listening application
src/main/java/ps/demo/util/SwingTool.java -- UI open chrome with webdriver and commands
src/main/java/ps/demo/util/MyRobotUtil.java -- Robot auto click and paste
src/main/java/ps/demo/util/MyImageUtil.java -- image tool, screenshot, comparing etc.




