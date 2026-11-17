# Aula 07 — Hub de faturamento

## Executar

Na pasta `hub`, execute:

```powershell
.\mvnw.cmd spring-boot:run
```

O endpoint REST fica em `http://localhost:8081/api/faturas`. Envie `pedidoId`, `clienteEmail` e `valor` em JSON. A resposta contém o ID persistido da fatura, o código de barras, a chave de acesso de 44 dígitos e o hash SHA-256 do e-mail.

O caso de uso depende somente das portas de saída. Os adaptadores de fila, SOAP e SMTP simulam as integrações externas; o adaptador de persistência usa H2. Repetir um pedido com a mesma chave `pedidoId` retorna a fatura existente.
