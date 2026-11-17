# Integração da venda com o hub de faturamento

A aplicação MVC fecha o pedido localmente e envia ao hub uma requisição REST. A chave de integração do pedido é estável e o hub a utiliza para devolver a mesma fatura em chamadas repetidas. O identificador retornado é persistido no pedido; se a chamada falhar, o pedido continua salvo como `PENDENTE_FATURAMENTO` e pode ser reenviado pela tela.

## Problema 1: hub indisponível ou timeout antes de criar a fatura

- **O que acontece com o pedido:** a venda já foi persistida, mas o sistema não recebeu o ID da fatura; o pedido fica pendente.
- **O que o negócio percebe:** pedido registrado sem boleto/nota fiscal, com necessidade de acompanhamento.
- **Solução:** usar uma **outbox transacional** na aplicação de vendas. O fechamento grava pedido e evento pendente na mesma transação; um worker tenta entregar o evento com retentativas e backoff até obter o ID e atualizar o pedido. O usuário pode acompanhar o estado pendente sem perder a venda.

## Problema 2: fatura criada no hub, mas resposta perdida

- **O que acontece com o pedido:** a aplicação MVC não sabe se a primeira chamada foi concluída e pode tentar enviá-la novamente.
- **O que o negócio percebe:** risco de emissão duplicada ou divergência entre pedido e fatura.
- **Solução:** aplicar **idempotência** no hub usando a chave estável do pedido. A restrição única no repositório evita uma segunda fatura e a repetição retorna a fatura já criada. A mesma chave deve ser mantida em todas as retentativas; o hub desta atividade já implementa esse comportamento.
