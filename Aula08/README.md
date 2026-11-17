# Aula 08 — Venda integrada ao hub

## Executar

Inicie primeiro o hub da Aula 07 na porta 8081. Em seguida, na pasta `mvc`, execute:

```powershell
.\mvnw.cmd spring-boot:run
```

A tela de pedidos fica em `http://localhost:8080/pedidos`. Ao fechar um pedido, a aplicação persiste a venda e chama o hub por REST. O ID retornado fica salvo no pedido; se o hub estiver indisponível, a venda permanece como `PENDENTE_FATURAMENTO` e pode ser reenviada pela tela.

Para apontar para outro endereço do hub, configure `HUB_FATURAMENTO_URL`. A análise dos dois problemas de integração e suas soluções está em [Analise.md](Analise.md).
