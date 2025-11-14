$javaExe = Get-Command java -ErrorAction SilentlyContinue

if (-not $javaExe) {
    $javaPaths = @(
        "$env:JAVA_HOME\bin\java.exe",
        (Get-ChildItem "C:\Program Files\Java" -Recurse -Filter "java.exe" -ErrorAction SilentlyContinue | Select-Object -First 1).FullName,
        (Get-ChildItem "C:\Program Files (x86)\Java" -Recurse -Filter "java.exe" -ErrorAction SilentlyContinue | Select-Object -First 1).FullName
    )
    
    foreach ($path in $javaPaths) {
        if ($path -and (Test-Path $path)) {
            $javaExe = $path
            break
        }
    }
    
    if (-not $javaExe) {
        Write-Host "Java nao encontrado! Instale o JDK e adicione ao PATH." -ForegroundColor Red
        exit
    }
} else {
    $javaExe = $javaExe.Source
}

$javacExe = $javaExe -replace "java.exe", "javac.exe"

Write-Host "Compilando..." -ForegroundColor Yellow
& $javacExe *.java

if ($LASTEXITCODE -eq 0) {
    Write-Host "Compilacao OK!" -ForegroundColor Green
    Write-Host ""
    Write-Host "Executando SistemaHospital..." -ForegroundColor Cyan
    & $javaExe SistemaHospital
} else {
    Write-Host "Erro na compilacao!" -ForegroundColor Red
}

