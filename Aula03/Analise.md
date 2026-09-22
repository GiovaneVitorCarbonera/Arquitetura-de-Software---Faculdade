# Análise de Estilos Arquiteturais

## 1. Monolito

### Conceito e definição

A arquitetura monolítica é quando o sistema inteiro funciona como uma única aplicação. As partes do sistema, como telas, regras de negócio e acesso ao banco de dados, ficam dentro do mesmo projeto.

Por exemplo, em um sistema de uma loja, cadastro de clientes, produtos, pedidos e pagamentos podem estar todos dentro da mesma aplicação.

### Casos de uso comuns

Esse tipo de arquitetura é comum em sistemas menores ou que estão começando a ser desenvolvidos, principalmente quando não existe a necessidade de uma infraestrutura muito complexa.

Um exemplo são **sistemas acadêmicos**, usados para cadastro de alunos, notas, professores e disciplinas. Outro exemplo são **sistemas administrativos**, como aplicações para controle de estoque, funcionários ou vendas.

### Principais vantagens

Uma das principais vantagens é a **simplicidade**. Como tudo está dentro da mesma aplicação, fica mais fácil organizar o projeto e entender como as partes do sistema se relacionam.

A instalação também costuma ser mais simples, já que geralmente é necessário colocar apenas uma aplicação para funcionar.

Outro ponto positivo é que a comunicação entre as partes do sistema é mais direta, pois elas estão dentro da mesma aplicação e não precisam depender de chamadas entre diferentes servidores.

### Principais desvantagens

Conforme o sistema cresce, o código pode ficar muito grande e difícil de organizar. Uma alteração em uma parte também pode acabar afetando outras funcionalidades.

A escalabilidade é outro problema. Se apenas uma funcionalidade estiver recebendo muitos acessos, normalmente é necessário aumentar os recursos da aplicação inteira.

Além disso, quando existe algum problema grave na aplicação, várias ou até todas as funcionalidades podem ficar indisponíveis ao mesmo tempo.

---

## 2. Cliente-Servidor

### Conceito e definição

Na arquitetura cliente-servidor, o sistema é dividido principalmente entre **cliente** e **servidor**. O cliente faz solicitações e apresenta as informações para o usuário, enquanto o servidor recebe essas solicitações, processa os dados e devolve uma resposta.

Um exemplo simples é um navegador acessando um site. O navegador funciona como cliente e envia uma solicitação para o servidor. O servidor processa a solicitação e retorna os dados para o navegador.

### Casos de uso comuns

Esse modelo é muito utilizado em sistemas que precisam atender vários usuários e centralizar informações.

Um exemplo são as **aplicações web**, nas quais o navegador do usuário se comunica com um servidor responsável pelo processamento da aplicação.

Outro exemplo são os **sistemas empresariais**, onde vários computadores podem acessar um servidor que concentra os dados e serviços utilizados pela empresa.

Também é comum em sistemas que utilizam **servidores de banco de dados**, nos quais diferentes aplicações clientes acessam um servidor responsável pelo armazenamento das informações.

### Principais vantagens

Uma das principais vantagens é a **centralização dos dados**. As informações ficam armazenadas no servidor, facilitando o controle e a manutenção.

Também é possível ter vários clientes utilizando o mesmo servidor. Dessa forma, uma alteração feita no servidor pode atender todos os usuários sem precisar modificar cada cliente individualmente.

Outra vantagem é a possibilidade de dividir as responsabilidades. O cliente pode ficar responsável pela interface, enquanto o servidor cuida do processamento e do armazenamento dos dados.

### Principais desvantagens

O servidor pode ficar sobrecarregado quando muitos clientes acessam o sistema ao mesmo tempo. Se não houver recursos suficientes, o desempenho pode diminuir.

A arquitetura também depende da comunicação entre cliente e servidor. Se houver problemas de rede, o cliente pode não conseguir acessar os serviços.

Outro problema é que o servidor pode se tornar um ponto de falha. Se ele ficar indisponível e não houver outro servidor para substituí-lo, os clientes podem perder o acesso ao sistema.

Também é necessário ter cuidados com **segurança**, principalmente no controle de acesso e na proteção das informações que são enviadas entre cliente e servidor.
