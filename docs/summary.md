## **Summary — Aegis Test (MVP)**

O **Aegis Test** é o módulo do Aegis responsável por **modelar, estruturar e gerar testes automatizados de API**, servindo como a base técnica para a estratégia de **auto-homologação inteligente** da plataforma.

Ele existe para resolver o principal gargalo dos testes modernos:
**transformar especificações incompletas e conhecimento difuso em testes confiáveis, rastreáveis e executáveis**, com suporte de Inteligência Artificial — sem perder controle técnico.

Este repositório opera **exclusivamente no domínio de testes** e assume que **projetos, usuários e permissões já foram resolvidos pelo Core**.

---

## **Papel do Aegis Test dentro do Homologger**

No ecossistema do Homologger, o Aegis Test atua como:

* O **motor de estruturação** de testes (antes da execução)
* O **orquestrador de geração via IA**
* O **repositório de conhecimento testável** do sistema

Ele conecta:

* Especificações humanas (Low-Code)
* Contexto técnico estruturado
* Código executável (High-Code)

---

## **Responsabilidades do Aegis Test**

O Aegis Test é responsável por:

### **1. Contexto e Organização**

* Criar e gerenciar o **TestProject**, entidade raiz do módulo.
* Gerenciar **Environments** (DEV, STAGING, PROD), cada um com:

    * Variáveis globais próprias
    * Base URLs específicas
    * Configurações de autenticação independentes
* Gerenciar **Global Variables**, com escopo por environment.
* Gerenciar **Domains semânticos**, usados para:

    * Organização funcional
    * Contextualização da IA
    * Navegação no frontend
* Gerenciar **Tags**, para classificação e filtragem.

---

### **2. Componentização Técnica**

* Definir **API Calls reutilizáveis**, desacopladas das especificações.
* Centralizar definições de:

    * Método
    * Path
    * Auth
    * Headers
    * Contratos esperados
* Reduzir redundância e inconsistência entre testes.

---

### **3. Criação de Especificações de Teste**

* Permitir a criação de **Test Specifications manuais**, informando:

    * Método HTTP
    * URL (base + path)
    * Necessidade de autenticação
    * Exemplos de request (JSON no MVP)
    * Descrição funcional
* Quanto mais rica a especificação, **melhor a geração automática**.

---

### **4. Geração Automática de Testes via IA**

* Consolidar todo o contexto do projeto:

    * Environments
    * Variáveis
    * Domains
    * API Calls
    * Specification
* Orquestrar o fluxo de geração com os **Aegis Agents**, que:

    1. Planejam cenários e passos
    2. Geram um modelo abstrato estruturado (JSON)
    3. Geram código executável em **Karate DSL**
    4. Autoexecutam e se validam durante a construção
* Persistir o resultado como:

    * **Artefato editável**
    * **Low-Code ou High-Code**
    * **Auditável e versionável**

---

### **5. Persistência e Evolução**

* Persistir testes como entidades de domínio, não apenas arquivos.
* Permitir edição posterior sem regeneração obrigatória.
* Preparar o sistema para múltiplos canais futuros:

    * Repositórios Git
    * API Docs
    * Eventos de PR
    * Outras linguagens de teste

---

## **O que o Aegis Test NÃO faz (MVP)**

Fora do escopo deste módulo:

* ❌ Gestão de usuários, organizações ou billing
* ❌ Execução distribuída de testes em larga escala
* ❌ Integração direta com CI/CD
* ❌ Gerenciamento de repositórios Git

> Esses comportamentos pertencem a outros módulos do Homologger.

---

## **Princípios de Design**

* O Aegis Test **orquestra**, não “imagina” — a inteligência vive nos agentes.
* A IA **não corrige regras de negócio inválidas**: ela falha explicitamente.
* Nenhum teste é considerado válido sem persistência explícita.
* Tudo é **editável após geração**.
* O sistema é projetado para **Low-Code primeiro, High-Code quando necessário**.
* Cada decisão de design visa **reduzir erro humano e aumentar rastreabilidade**.

---

## **Objetivo do MVP**

Validar que o Aegis é capaz de:

* Receber uma especificação funcional
* Traduzi-la em testes estruturados via IA
* Persistir esses testes como ativos do produto
* Preparar o terreno para execução, auditoria e diagnóstico inteligentes