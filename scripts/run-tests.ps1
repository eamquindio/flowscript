#Requires -Version 5.1
param(
    [switch]$Offline,
    [string]$Test,
    [string]$Method,
    [string[]]$ExtraMavenArgs
)

$ErrorActionPreference = 'Stop'

function Find-Maven {
    if ($env:MAVEN_CMD -and (Test-Path $env:MAVEN_CMD)) { return $env:MAVEN_CMD }

    $mvnCmd = Get-Command mvn -ErrorAction SilentlyContinue
    if ($mvnCmd) { return $mvnCmd.Path }

    $common = @(
        'C:\apache-maven\mvn\bin\mvn.cmd',
        'C:\apache-maven\bin\mvn.cmd'
    )
    foreach ($p in $common) { if (Test-Path $p) { return $p } }

    try {
        $found = Get-ChildItem -Path 'C:\Program Files','C:\Program Files (x86)' -Filter mvn.cmd -Recurse -ErrorAction SilentlyContinue | Select-Object -First 1 -ExpandProperty FullName
        if ($found) { return $found }
    } catch {}

    throw 'No se encontró Maven (mvn). Añádelo al PATH o define MAVEN_CMD con la ruta a mvn.cmd.'
}

# Cambiar a la raíz del repo si se ejecuta desde otra carpeta
try {
    $repoRoot = Resolve-Path (Join-Path $PSScriptRoot '..')
    Set-Location $repoRoot
} catch {}

$mvn = Find-Maven

$argsList = @()
if ($Offline) { $argsList += '-o' }

if ($Test) {
    if ($Method) { $argsList += "-Dtest=$Test#$Method" } else { $argsList += "-Dtest=$Test" }
}

if ($ExtraMavenArgs) { $argsList += $ExtraMavenArgs }

$argsList = @('-P','test') + $argsList + 'test'

Write-Host "Ejecutando: $mvn $($argsList -join ' ')" -ForegroundColor Cyan

& $mvn @argsList
exit $LASTEXITCODE

