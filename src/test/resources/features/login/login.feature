# language: pt
@login
Funcionalidade: Login - Autenticação de Usuários
  Como usuário da plataforma SauceDemo
  Quero realizar login com diferentes credenciais
  Para que o sistema valide corretamente os acessos

  Contexto:
    Dado que estou na página de login

  @smoke @login-valido
  Cenário: Login com credenciais válidas
    Quando informo o usuário válido
    E informo a senha válida
    E clico no botão de login
    Então devo ser redirecionado para a página de produtos

  @login-senha-invalida
  Cenário: Login com senha inválida
    Quando informo o usuário válido
    E informo uma senha incorreta
    E clico no botão de login
    Então devo visualizar a mensagem de erro "Epic sadface: Username and password do not match any user in this service"

  @login-usuario-inexistente
  Cenário: Login com usuário inexistente
    Quando informo um usuário que não existe no sistema
    E informo uma senha genérica
    E clico no botão de login
    Então devo visualizar a mensagem de erro "Epic sadface: Username and password do not match any user in this service"

  @login-campos-em-branco
  Cenário: Login com campos em branco
    Quando submeto o formulário sem preencher nenhum campo de usuário
    E submeto sem preencher a senha
    Então devo visualizar a mensagem de erro "Epic sadface: Username is required"

  @login-senha-em-branco
  Cenário: Login sem informar a senha
    Quando informo apenas o usuário
    E submeto sem preencher a senha
    Então devo visualizar a mensagem de erro "Epic sadface: Password is required"

  @login-usuario-bloqueado
  Cenário: Login com usuário bloqueado
    Quando informo o usuário bloqueado
    E informo a senha válida
    E clico no botão de login
    Então devo visualizar a mensagem de erro "Epic sadface: Sorry, this user has been locked out"
