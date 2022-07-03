set "curDir=%cd%"

cd ..
rd /S /Q deployable\
mkdir deployable\target\
copy /Y target\*.jar deployable\target\
copy /Y Dockerfile deployable\
xcopy /Y /S /E /H /R /C script\ deployable\script\
xcopy /Y /S /E /H /R /C conf\ deployable\conf\

cd %curDir%
