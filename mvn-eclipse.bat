@echo off

cd %~p0

call mvn eclipse:eclipse -DdownloadSources=true

pause
