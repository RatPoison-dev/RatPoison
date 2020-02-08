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
pause
