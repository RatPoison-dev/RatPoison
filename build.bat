@echo off
title RatPoison Builder
@ECHO OFF
:choice
set /P c=Install JDK (required)? [Y/N] 
if /I "%c%" EQU "Y" goto :s
if /I "%c%" EQU "N" goto :e
goto :choice
:s
echo Downloading JDK...
PowerShell.exe -executionpolicy bypass -Command "& './build.ps1'"
echo Installing JDK...
msiexec /i adoptopenjdk.msi INSTALLLEVEL=2 /passive
goto :install
:e
echo Okay :)
goto :install 
:install
echo Building RatPoison...
call gradlew RatPoison
echo.
echo If there is an error message, close this window, try rebuilding a few times, then make a github issue if it continues. 
echo.
set /P c=Would you like to randomize the file name for safety? [Y/N] 
if /I "%c%" EQU "Y" goto :y
if /I "%c%" EQU "N" goto :n
:y
start /min ren.bat
echo.
echo File name has been randomized. 
goto :n
:n
cd build
cd RatPoison 1.7
echo.
set /P c=Would you like to open the cheat folder? [Y/N] 
if /I "%c%" EQU "Y" goto :y1
if /I "%c%" EQU "N" goto :n1
:y1
start.
goto :n1
:n1
echo.
echo To run this cheat, open csgo and run the bat in the cheat folder.
echo.
pause
