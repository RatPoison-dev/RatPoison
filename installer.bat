@echo off
title Installer downloader
IF EXIST "installer.exe" (
  installer.exe %1 %2
) ELSE (
  echo Installer script wasn't found. Downloading...
  powershell -Command "(New-Object Net.WebClient).DownloadFile('http://github.com/SPRAVEDLIVO/RatInstaller/releases/latest/download/installer.exe', 'installer.exe')"
  installer.exe %1 %2
)