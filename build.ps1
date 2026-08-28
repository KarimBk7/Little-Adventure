# Baut Kaiju Adventure:
#   1. ausfuehrbares JAR  -> Kaiju Adventure\target\KaijuAdventure.jar
#   2. eigenstaendige Windows-Version (inkl. Java-Laufzeit) -> release\KaijuAdventure\
#
# Voraussetzung: JDK 17 oder neuer (fuer jlink/jpackage) und Maven im PATH.
# Aufruf:  powershell -ExecutionPolicy Bypass -File .\build.ps1

$ErrorActionPreference = 'Stop'

$root    = $PSScriptRoot
$projekt = Join-Path $root 'Kaiju Adventure'
$release = Join-Path $root 'release'
$arbeit  = Join-Path $env:TEMP 'kaiju-build'
$version = '2.4.4'

Push-Location $projekt
try {
    Write-Host '== 1/3  JAR bauen (Maven) ==' -ForegroundColor Cyan
    mvn -B -q clean package
    if ($LASTEXITCODE -ne 0) { throw 'Maven-Build fehlgeschlagen.' }

    Write-Host '== 2/3  Minimale Java-Laufzeit bauen (jlink) ==' -ForegroundColor Cyan
    if (Test-Path $arbeit) { Remove-Item -Recurse -Force $arbeit }
    New-Item -ItemType Directory -Force -Path (Join-Path $arbeit 'input') | Out-Null
    Copy-Item (Join-Path $projekt 'target\KaijuAdventure.jar') (Join-Path $arbeit 'input')

    jlink --add-modules java.base,java.desktop,java.sql `
          --strip-debug --no-header-files --no-man-pages --compress=zip-6 `
          --output (Join-Path $arbeit 'runtime')
    if ($LASTEXITCODE -ne 0) { throw 'jlink fehlgeschlagen.' }

    Write-Host '== 3/3  Windows-Anwendung bauen (jpackage) ==' -ForegroundColor Cyan
    if (Test-Path $release) { Remove-Item -Recurse -Force $release }

    jpackage --type app-image `
             --name KaijuAdventure `
             --input (Join-Path $arbeit 'input') `
             --main-jar KaijuAdventure.jar `
             --main-class main.Main `
             --runtime-image (Join-Path $arbeit 'runtime') `
             --icon (Join-Path $projekt 'packaging\kaiju.ico') `
             --app-version $version `
             --vendor 'Abdil Karim Bakir' `
             --dest $release
    if ($LASTEXITCODE -ne 0) { throw 'jpackage fehlgeschlagen.' }

    Remove-Item -Recurse -Force $arbeit
    Write-Host ''
    Write-Host "Fertig: $release\KaijuAdventure\KaijuAdventure.exe" -ForegroundColor Green
}
finally {
    Pop-Location
}
