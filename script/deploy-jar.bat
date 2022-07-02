echo '-----------------Initializing...-----------------'
set "curDir=%cd%"
set imgName=simple-demo
set ver=v1
set ns=app

cd ..
rd /S /Q deployable\
mkdir deployable\
xcopy /Y /S /E /H /R /C target\*.jar deployable\target\
xcopy /Y /S /E /H /R /C script\ deployable\script\
xcopy /Y /S /E /H /R /C conf\ deployable\
copy Dockerfile deployable\

cd deployable\

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
echo "kubectl delete ns app"
