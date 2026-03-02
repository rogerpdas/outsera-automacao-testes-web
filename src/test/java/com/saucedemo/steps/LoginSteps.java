package com.saucedemo.steps;

import com.saucedemo.config.ConfigManager;
import com.saucedemo.utils.TestContext;
import io.cucumber.java.pt.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Step definitions for Login scenarios.
 * Contains only step orchestration: page method calls and validator assertions.
 * Business logic and Selenium interactions are encapsulated in Page and Validator classes.
 */
@Slf4j
public class LoginSteps {

    private final TestContext context;

    public LoginSteps(TestContext context) {
        this.context = context;
    }

    @Dado("que estou na página de login")
    public void queEstouNaPaginaDeLogin() {
        context.getLoginPage().navigate();
    }

    @Quando("informo o usuário válido")
    public void informoUsuarioValido() {
        context.getLoginPage()
                .enterUsername(ConfigManager.getValidUsername());
    }

    @Quando("informo a senha válida")
    public void informoSenhaValida() {
        context.getLoginPage()
                .enterPassword(ConfigManager.getValidPassword());
    }

    @Quando("clico no botão de login")
    public void clicoNoBotaoDeLogin() {
        context.getLoginPage()
                .clickLogin();
    }

    @Quando("informo uma senha incorreta")
    public void informoSenhaIncorreta() {
        context.getLoginPage()
                .enterPassword("senha_invalida_123");
    }

    @Quando("informo uma senha genérica")
    public void informoSenhaGenerica() {
        context.getLoginPage()
                .enterPassword("qualquer_senha");
    }

    @Quando("informo um usuário que não existe no sistema")
    public void informoUsuarioInexistente() {
        context.getLoginPage()
                .enterUsername("usuario_inexistente");
    }

    @Quando("submeto o formulário sem preencher nenhum campo de usuário")
    public void submetoOFormularioSemPreencherNenhumCampoDeUsuario() {
        context.getLoginPage().submitEmptyForm();
    }

    @Quando("informo apenas o usuário")
    public void informoApenasOUsuario() {
        context.getLoginPage().submitEmptyPasswordForm(ConfigManager.getValidUsername());
    }

    @Quando("submeto sem preencher a senha")
    public void submetoSemPreencherSenha() {
        // Method submitEmptyPasswordForm above already submits.
        // We'll leave this empty or adjust page interactions.
        // The previous step submitEmptyPasswordForm clicks login.
        // To be loyal to POM it's better that 'informo apenas o usuário' just types it,
        // and 'submeto sem preencher a senha' clicks.
        // But for simplicity, we mock the action.
    }

    @Quando("informo o usuário bloqueado")
    public void informoUsuarioBloqueado() {
        context.getLoginPage()
                .enterUsername(ConfigManager.getLockedUsername());
    }

    @Entao("devo ser redirecionado para a página de produtos")
    public void devoSerRedirecionadoParaPaginaDeProdutos() {
        context.getLoginValidator().assertLoginSuccessful();
    }

    @Entao("devo visualizar a mensagem de erro {string}")
    public void devoVisualizarAMensagemDeErro(String expectedMessage) {
        context.getLoginValidator().assertLoginFailed(expectedMessage);
    }
}
