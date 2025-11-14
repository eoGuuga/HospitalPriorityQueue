Write-Host "=== Sistema de Fila Hospitalar ===" -ForegroundColor Cyan
Write-Host ""

$javaPath = $null

Write-Host "Procurando Java instalado..." -ForegroundColor Yellow

$possiblePaths = @(
    "$env:JAVA_HOME\bin\java.exe",
    "C:\Program Files\Java\*\bin\java.exe",
    "C:\Program Files (x86)\Java\*\bin\java.exe",
    "$env:ProgramFiles\Java\*\bin\java.exe",
    "$env:ProgramFiles(x86)\Java\*\bin\java.exe"
)

foreach ($path in $possiblePaths) {
    $found = Get-ChildItem -Path $path -ErrorAction SilentlyContinue | Select-Object -First 1 -ExpandProperty FullName
    if ($found) {
        $javaPath = $found
        Write-Host "Java encontrado em: $javaPath" -ForegroundColor Green
        break
    }
}

if (-not $javaPath) {
    Write-Host "Java nao encontrado no sistema!" -ForegroundColor Red
    Write-Host ""
    Write-Host "Opcoes:" -ForegroundColor Yellow
    Write-Host "1. Instale o Java JDK 8 ou superior"
    Write-Host "2. Adicione o Java ao PATH do sistema"
    Write-Host "3. Execute manualmente com o caminho completo do java.exe"
    Write-Host ""
    Write-Host "Exemplo: & 'C:\Program Files\Java\jdk-XX\bin\java.exe' SistemaHospital"
    exit 1
}

Write-Host ""
Write-Host "Compilando arquivos Java..." -ForegroundColor Yellow
$javacPath = $javaPath -replace "java.exe", "javac.exe"

if (Test-Path $javacPath) {
    & $javacPath *.java
    if ($LASTEXITCODE -ne 0) {
        Write-Host "Erro na compilacao!" -ForegroundColor Red
        exit 1
    }
    Write-Host "Compilacao concluida!" -ForegroundColor Green
} else {
    Write-Host "javac.exe nao encontrado. Verifique se o JDK esta instalado." -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "Escolha uma opcao:" -ForegroundColor Cyan
Write-Host "1. Executar Interface Grafica (SistemaHospital)"
Write-Host "2. Executar Teste (TesteHospital)"
Write-Host ""
$opcao = Read-Host "Digite o numero da opcao"

Write-Host ""

switch ($opcao) {
    "1" {
        Write-Host "Executando SistemaHospital..." -ForegroundColor Green
        & $javaPath SistemaHospital
    }
    "2" {
        Write-Host "Executando TesteHospital..." -ForegroundColor Green
        & $javaPath TesteHospital
    }
    default {
        Write-Host "Opcao invalida!" -ForegroundColor Red
    }
}

