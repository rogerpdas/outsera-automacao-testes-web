# language: pt
@checkout
Funcionalidade: Checkout - Fluxo de Compra no E-commerce
  Como usuário autenticado no SauceDemo
  Quero adicionar produtos ao carrinho e finalizar a compra
  Para que o fluxo completo de checkout funcione corretamente

  Contexto:
    Dado que estou autenticado com credenciais válidas

  @smoke @carrinho-adicionar
  Cenário: Adicionar um produto ao carrinho
    Quando adiciono o produto "sauce-labs-backpack" ao carrinho
    Então o ícone do carrinho deve exibir o contador "1"

  @carrinho-multiplos-itens
  Cenário: Adicionar múltiplos produtos ao carrinho
    Quando adiciono o produto "sauce-labs-backpack" ao carrinho
    E adiciono o produto "sauce-labs-bike-light" ao carrinho
    Então o ícone do carrinho deve exibir o contador "2"

  @carrinho-visualizar
  Cenário: Visualizar itens adicionados no carrinho
    Quando adiciono o produto "sauce-labs-backpack" ao carrinho
    E acesso o carrinho de compras
    Então a página do carrinho deve ser exibida corretamente
    E o carrinho deve conter o item "Sauce Labs Backpack"

  @smoke @checkout-completo
  Cenário: Finalizar compra com sucesso
    Quando adiciono o produto "sauce-labs-backpack" ao carrinho
    E acesso o carrinho de compras
    E prossigo para o checkout
    E preencho o nome da entrega com "João"
    E preencho o sobrenome da entrega com "Silva"
    E preencho o cep da entrega com "12345-678"
    E confirmo as informações
    E avanço para o resumo
    E finalizo o pedido
    Então a confirmação do pedido deve ser exibida com sucesso

  @checkout-campos-obrigatorios
  Cenário: Tentar finalizar checkout com campos obrigatórios em branco
    Quando adiciono o produto "sauce-labs-backpack" ao carrinho
    E acesso o carrinho de compras
    E prossigo para o checkout
    E tento avançar sem preencher as informações de entrega
    Então devo visualizar o erro de checkout "Error: First Name is required"
