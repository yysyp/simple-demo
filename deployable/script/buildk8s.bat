
echo '-----------------Initializing...-----------------'
set "curDir=%cd%"
set imgName=simple-demo
set ver=versionplaceholder
set ns=app

echo 'Please prepare the Dockerfile, k8s.yaml in currentDirectory!'
echo 'curDir=%curDir% - imgName=%imgName% - ver=%ver% - ns=%ns%'

cd ..

echo '-----------------Maven build and package...-----------------'
call mvn clean package
if %errorlevel% neq 0 exit /b %errorlevel%

cd script
call build-deployable.bat
cd ..

echo '-----------------Docker build...-----------------'
call docker build -t %imgName%:%ver% .
if %errorlevel% neq 0 exit /b %errorlevel%

echo '-----------------Kubernetes deploy...-----------------'
call kubectl delete namespace %ns%
call kubectl create namespace %ns%
if %ERRORLEVEL% neq 0 exit /b %ERRORLEVEL%
call kubectl apply -f script/k8s.yaml
if %ERRORLEVEL% neq 0 exit /b %ERRORLEVEL%
cd %curDir%
