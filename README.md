# 🧪 SauceDemo E2E Test Automation - Desafio Outsera

Projeto de automação de testes End-to-End para atender os critérios do desafio Outsera, utilizando a plataforma [SauceDemo](https://www.saucedemo.com), cobrindo os fluxos de **autenticação** e **checkout completo** de e-commerce, tanto com dados válidos quanto inválidos.

---

## Descrição

Automação construída com foco em qualidade de produção, seguindo boas práticas de engenharia de software:

- **Page Object Model (POM)** para separação clara de responsabilidades
- **Camada de Validadores** isolada dos steps e das páginas
- **Steps limpos** — apenas orquestração de chamadas, sem lógica de UI
- **Execução paralela e multi-browser** (Chrome, Firefox, Edge)
- **Relatório visual** com ExtentReports (tema dark)
- **CI/CD** com GitHub Actions, execução paralela de suítes e publicação de artefatos
- **Tratamento de erros** com esperas explícitas, retry em clicks interceptados e screenshots automáticos em falhas

---

## Arquitetura e Estrutura de Pastas

```
saucedemo-e2e/
├── .github/
│   └── workflows/
│       └── e2e-tests.yml              # Pipeline CI/CD GitHub Actions
│
├── src/test/
│   ├── java/com/saucedemo/
│   │   ├── config/
│   │   │   ├── ConfigManager.java     # Gerenciador centralizado de propriedades
│   │   │   └── DriverManager.java     # Thread-safe WebDriver (ThreadLocal)
│   │   │
│   │   ├── pages/                     # Page Object Model
│   │   │   ├── BasePage.java          # Métodos Selenium reutilizáveis
│   │   │   ├── LoginPage.java
│   │   │   ├── InventoryPage.java
│   │   │   ├── CartPage.java
│   │   │   ├── CheckoutPage.java
│   │   │   └── OrderConfirmationPage.java
│   │   │
│   │   ├── validators/                # Camada de Assertions/Validações
│   │   │   ├── LoginValidator.java
│   │   │   └── CheckoutValidator.java
│   │   │
│   │   ├── steps/                     # Step Definitions (Cucumber)
│   │   │   ├── LoginSteps.java
│   │   │   └── CheckoutSteps.java
│   │   │
│   │   ├── hooks/
│   │   │   └── Hooks.java             # Setup/Teardown, screenshot em falha
│   │   │
│   │   ├── runners/                   # JUnit Runner Unificado
│   │   │   └── RunAllTests.java
│   │   │
│   │   └── utils/
│   │       ├── TestContext.java        # Injeção de dependência PicoContainer
│   │       ├── ExtentReportManager.java
│   │       └── ScreenshotUtils.java
│   │
│   └── resources/
│       ├── features/
│       │   ├── login/
│       │   │   └── login.feature
│       │   └── checkout/
│       │       └── checkout.feature
│       ├── config.properties          # Configurações do ambiente
│       ├── extent.properties          # Config do ExtentReports adapter
│       ├── extent-config.xml
│       └── logback.xml               # Configuração de logs
│
├── reports/                           # Diretório de saída dos relatórios (pré-criado no repositório)
│   ├── .gitkeep                       # Mantém a pasta versionada no Git (Git não rastreia pastas vazias)
│   ├── ExtentReport.html              # Relatório visual gerado após execução (ExtentReports)
│   ├── screenshots/                   # Screenshots capturados automaticamente em falhas
│   └── test-execution.log            # Log completo da execução (gerado em runtime)
│
├── pom.xml
└── README.md
```

---

## Versões Utilizadas

| Tecnologia           | Versão    |
|----------------------|-----------|
| Java                 | 17        |
| Maven                | 3.9+      |
| Selenium             | 4.18.1    |
| WebDriverManager     | 5.7.0     |
| Cucumber             | 7.15.0    |
| JUnit                | 4.13.2    |
| ExtentReports        | 5.1.1     |
| Extent Cucumber Adapter | 1.14.0 |
| Lombok               | 1.18.30   |
| Logback              | 1.4.14    |
| SLF4J                | 2.0.12    |
| Maven Surefire       | 3.2.5     |

---

## Pré-requisitos e Instalação

### 1. Requisitos
- **Java 17** instalado e `JAVA_HOME` configurado
- **Maven 3.9+** instalado
- **Google Chrome**, **Firefox** ou **Edge** instalado
- **Git** instalado

### 2. Clonar o projeto
```bash
git clone https://github.com/rogerpdas/outsera-automacao-testes-web.git
cd outsera-automacao-testes-web
```

### 3. Instalar dependências
```bash
mvn dependency:resolve
```

> ℹ️ O **WebDriverManager** gerencia automaticamente o download dos drivers (ChromeDriver, GeckoDriver, EdgeDriver). Não é necessário instalar manualmente.

---

## Como Executar os Testes

### Executar todos os testes (modo padrão — Chrome)
```bash
mvn test
```

### Executar com um browser específico
```bash
# Chrome
mvn test -Dbrowser=chrome

# Firefox
mvn test -Dbrowser=firefox

# Edge
mvn test -Dbrowser=edge
```

### Executar em modo headless (sem interface gráfica)
```bash
mvn test -Dheadless=true
```

### Executar por tags Cucumber
Para executar apenas os testes marcados com `@smoke`:
```bash
mvn test -Dcucumber.filter.tags="@smoke"
```

### Execução completa em headless + Firefox (ex: CI local)
```bash
mvn test -Dbrowser=firefox -Dheadless=true
```

### Controlar nível de paralelismo
```bash
mvn test -Dparallel.count=3
```

---

## Relatórios

Após a execução, os relatórios são gerados em `reports/`:

| Arquivo                      | Descrição                              |
|------------------------------|----------------------------------------|
| `reports/ExtentReport.html`  | Relatório visual completo (Extent)     |
| `reports/screenshots/`       | Screenshots de falhas (com timestamp)  |
| `reports/test-execution.log` | Log completo da execução               |

Para abrir o relatório:
```bash
open reports/ExtentReport.html   # macOS
start reports/ExtentReport.html  # Windows
xdg-open reports/ExtentReport.html # Linux
```

---

## Configurações

Edite `src/test/resources/config.properties` para ajustar:

```properties
base.url=https://www.saucedemo.com
browser=chrome
headless=false
implicit.wait=10
explicit.wait=15
page.load.timeout=30
valid.username=standard_user
valid.password=secret_sauce
locked.username=locked_out_user
```

> Todas as propriedades podem ser **sobrescritas via linha de comando** com `-Dchave=valor`.

---

## CI/CD — GitHub Actions

O pipeline em `.github/workflows/e2e-tests.yml` executa:

- **Trigger automático:** push/PR em `main` e `develop`
- **Trigger agendado:** todo dia às 06:00 UTC
- **Trigger manual:** via `workflow_dispatch` com escolha de browser e headless
- **Paralelismo:** Login e Checkout rodam em **jobs separados e simultâneos**
- **Artefatos:** relatórios e screenshots são publicados por 30 dias
- **Summary:** tabela com resultado de cada suíte direto na aba do GitHub Actions

Para executar manualmente no GitHub:
1. Acesse a aba **Actions** do repositório
2. Selecione **E2E Test Automation - Desafio Outsera**
3. Clique em **Run workflow**
4. Selecione browser e headless desejados

---

## Padrões/Boas Práticas

- **Steps são declarativos** — descrevem o que acontece, não como
- **Page Objects** encapsulam toda interação com o browser
- **Validators** centralizam todos os `Assert.*` — nunca nos steps
- **TestContext** compartilha estado entre steps via PicoContainer (sem variáveis estáticas)
- **DriverManager** usa `ThreadLocal<WebDriver>` garantindo isolamento em execução paralela
- **Hooks** gerenciam o ciclo de vida do driver e do report de forma transparente
- **Screenshots** são capturados automaticamente em qualquer step que falhar

---

## Cenários Cobertos

### Login
| Cenário | Tag |
|---------|-----|
| Login com credenciais válidas | `@smoke @login-valido` |
| Login com senha inválida | `@login-senha-invalida` |
| Login com usuário inexistente | `@login-usuario-inexistente` |
| Login com campos em branco | `@login-campos-em-branco` |
| Login sem senha | `@login-senha-em-branco` |
| Login com usuário bloqueado | `@login-usuario-bloqueado` |

### Checkout
| Cenário | Tag |
|---------|-----|
| Adicionar produto ao carrinho | `@smoke @carrinho-adicionar` |
| Adicionar múltiplos produtos | `@carrinho-multiplos-itens` |
| Visualizar itens no carrinho | `@carrinho-visualizar` |
| Finalizar compra com sucesso | `@smoke @checkout-completo` |
| Checkout com campos obrigatórios em branco | `@checkout-campos-obrigatorios` |
