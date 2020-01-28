$url = "https://github.com/AdoptOpenJDK/openjdk13-binaries/releases/download/jdk-13.0.2%2B8/OpenJDK13U-jdk_x64_windows_hotspot_13.0.2_8.msi"
$output = "adoptopenjdk.msi"
$wc = New-Object System.Net.WebClient
$wc.DownloadFile($url, $output)
