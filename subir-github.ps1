Write-Host "=== Subindo projeto para GitHub ===" -ForegroundColor Cyan
Write-Host ""

$repoName = Read-Host "Digite o nome do seu usuario do GitHub (ex: gustavo-henrick)"

if ([string]::IsNullOrWhiteSpace($repoName)) {
    Write-Host "Nome de usuario invalido!" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "Adicionando remote origin..." -ForegroundColor Yellow
git remote add origin "https://github.com/$repoName/HospitalPriorityQueue.git"

if ($LASTEXITCODE -ne 0) {
    Write-Host "Remote ja existe. Atualizando..." -ForegroundColor Yellow
    git remote set-url origin "https://github.com/$repoName/HospitalPriorityQueue.git"
}

Write-Host ""
Write-Host "Fazendo push para GitHub..." -ForegroundColor Yellow
git push -u origin main

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "✅ Projeto enviado com sucesso!" -ForegroundColor Green
    Write-Host "Acesse: https://github.com/$repoName/HospitalPriorityQueue" -ForegroundColor Cyan
} else {
    Write-Host ""
    Write-Host "❌ Erro ao fazer push!" -ForegroundColor Red
    Write-Host "Certifique-se de que:" -ForegroundColor Yellow
    Write-Host "1. O repositorio foi criado no GitHub" -ForegroundColor Yellow
    Write-Host "2. Voce esta autenticado (git config ou GitHub Desktop)" -ForegroundColor Yellow
    Write-Host "3. O nome do usuario esta correto" -ForegroundColor Yellow
}

