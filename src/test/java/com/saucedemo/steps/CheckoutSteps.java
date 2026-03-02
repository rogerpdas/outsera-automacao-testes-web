package com.saucedemo.steps;

import com.saucedemo.config.ConfigManager;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.OrderConfirmationPage;
import com.saucedemo.utils.TestContext;
import io.cucumber.java.pt.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Step definitions for Checkout and Cart scenarios.
 * Contains only step orchestration: page method calls and validator assertions.
 * Business logic and Selenium interactions are encapsulated in Page and Validator classes.
 */
@Slf4j
public class CheckoutSteps {

    private final TestContext context;

    public CheckoutSteps(TestContext context) {
        this.context = context;
    }

    @Dado("que estou autenticado com credenciais válidas")
    public void queEstouAutenticadoComCredenciaisValidas() {
        context.getLoginPage().navigate();
        context.getLoginPage()
                .enterUsername(ConfigManager.getValidUsername())
                .enterPassword(ConfigManager.getValidPassword())
                .clickLogin();
        context.getLoginValidator().assertLoginSuccessful();
    }

    @Quando("adiciono o produto {string} ao carrinho")
    public void adicionoOProdutoAoCarrinho(String itemSlug) {
        context.getInventoryPage().addItemToCartByName(itemSlug);
    }

    @Quando("acesso o carrinho de compras")
    public void acessoOCarrinhoDeCompras() {
        CartPage cartPage = context.getInventoryPage().goToCart();
        context.setCartPage(cartPage);
    }

    @Quando("prossigo para o checkout")
    public void prossigoParagoCheckout() {
        CheckoutPage checkoutPage = context.getCartPage().proceedToCheckout();
        context.setCheckoutPage(checkoutPage);
    }

    @Quando("preencho o nome da entrega com {string}")
    public void preenchoNomeEntrega(String firstName) {
        context.getCheckoutPage().enterFirstName(firstName);
    }

    @Quando("preencho o sobrenome da entrega com {string}")
    public void preenchoSobrenomeEntrega(String lastName) {
        context.getCheckoutPage().enterLastName(lastName);
    }

    @Quando("preencho o cep da entrega com {string}")
    public void preenchoCepEntrega(String postalCode) {
        context.getCheckoutPage().enterPostalCode(postalCode);
    }

    @Quando("confirmo as informações")
    public void confirmoAsInformacoes() {
        context.getCheckoutPage().clickContinue();
    }

    @Quando("avanço para o resumo")
    public void avançoParaOResumo() {
        context.getCheckoutValidator().assertCheckoutStepTwoLoaded();
    }

    @Quando("finalizo o pedido")
    public void finalizoOPedido() {
        OrderConfirmationPage confirmationPage = context.getCheckoutPage().finishOrder();
        context.setOrderConfirmationPage(confirmationPage);
    }

    @Quando("tento avançar sem preencher as informações de entrega")
    public void tentoAvançarSemPreencherAsInformacoesDeEntrega() {
        context.getCheckoutPage().clickContinueWithEmptyForm();
    }

    @Entao("o ícone do carrinho deve exibir o contador {string}")
    public void oIconeDoCarrinhoDeveExibirOContador(String expectedCount) {
        context.getCheckoutValidator().assertItemAddedToCart(Integer.parseInt(expectedCount));
    }

    @Entao("a página do carrinho deve ser exibida corretamente")
    public void aPaginaDoCarrinhoDeveSerExibidaCorretamente() {
        context.getCheckoutValidator().assertCartPageLoaded();
    }

    @Entao("o carrinho deve conter o item {string}")
    public void oCarrinhoDeveConterOItem(String itemName) {
        context.getCheckoutValidator().assertCartContainsItem(itemName);
    }

    @Entao("a confirmação do pedido deve ser exibida com sucesso")
    public void aConfirmacaoDoPedidoDeveSerExibidaComSucesso() {
        context.getCheckoutValidator().assertOrderCompleted();
        context.getCheckoutValidator().assertOrderConfirmationPageTitle();
    }

    @Entao("devo visualizar o erro de checkout {string}")
    public void devoVisualizarOErroDeCheckout(String expectedError) {
        context.getCheckoutValidator().assertCheckoutInfoError(expectedError);
    }
}
