@echo off

set logfile=compile_dev_fs24.log
set devdir=d:\fs24\dev-s
set devdircmd=d:\fs24\dev-s\cmd

del %devdircmd%\%logfile% >nul 
cd %devdir%
cmd.exe /c mvn -T 100 -q compile -Dmaven.test.skip=true > %devdircmd%\%logfile%


