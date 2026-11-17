# Aula 06 — Módulo de estoque

## Executar

Na pasta `mvc`, execute:

```powershell
.\mvnw.cmd spring-boot:run
```

A tela fica em `http://localhost:8080/produtos`. O usuário administrador usa autenticação HTTP Basic; para desenvolvimento, o padrão é `admin` / `admin123`. Os valores podem ser substituídos por `APP_ADMIN_USERNAME` e `APP_ADMIN_PASSWORD`.

Produtos são criados como `INDISPONIVEL`. Entradas e saídas atualizam o estoque e o status automaticamente. A rota de descontinuação exige o papel `ADMIN`; produtos descontinuados não aceitam novas movimentações.

Os três diagramas da atividade estão em `diagramas/`.
