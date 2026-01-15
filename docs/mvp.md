# **Especificação MVP: Aegis Tests (Homolog Core)**

> Documento técnico de referência para implementação do MVP
> Objetivo: orientar **GitHub Copilot + dev humano** na codificação correta

---

## **I. Objetivo Consolidado do MVP**

Validar o módulo **Aegis Tests** como um sistema capaz de:

* Criar e organizar **TestProjects** dentro de um Projeto Core.
* Estruturar **especificações de testes manuais** (API-first).
* Orquestrar a **geração automática de testes via IA**, produzindo:

   * Metadados estruturados
   * Código executável em **Karate DSL**
* Persistir tudo de forma **editável, auditável e evolutiva** no backend.

O MVP **não é sobre execução em larga escala**, e sim sobre:

✅ Qualidade de geração
✅ Governança de dados
✅ Arquitetura extensível

---

## **II. Arquitetura Geral (Visão Atualizada)**

A geração é **orquestrada pelo Spring**, mas **planejada e executada pelos agentes de IA**.

### **Módulos Envolvidos**

| Módulo          | Tecnologia   | Responsabilidade                               |
| --------------- | ------------ | ---------------------------------------------- |
| Frontend        | Next.js      | Criar specs, aprovar planos, visualizar testes |
| Aegis Core      | Spring Boot  | Projetos, membros, permissões                  |
| **Aegis Tests** | Spring Boot  | Specs, environments, variáveis, orquestração   |
| Aegis Agents    | Python + LLM | Planejamento, validação e geração de testes    |

---

## **III. Conceitos-Chave do Domínio**

### **TestProject**

Container raiz do módulo de testes, vinculado a um **Project Core**.

Contém:

* Environments
* Global Variables
* Domains
* API Calls
* Test Specifications
* Test Scenarios (gerados)

---

### **Environment**

Representa um contexto de execução.

Cada Environment pode ter:

* Base URL própria
* Variáveis próprias
* Auth própria
* Tags específicas

Exemplo:

* `DEV`
* `STAGING`
* `PROD`

---

### **Global Variables**

Variáveis reutilizáveis e versionáveis.

Escopos:

* `PROJECT`
* `ENVIRONMENT`

Exemplos:

* `BASE_URL`
* `AUTH_TOKEN`
* `CLIENT_ID`

---

### **Domain (Semântico)**

Entidade **puramente organizacional**, sem comportamento técnico.

Serve para:

* Agrupar specs e cenários
* Ajudar IA a entender contexto funcional

Exemplo:

* `Recebíveis`
* `Duplicatas`
* `Liquidação`

---

### **API Call**

Definição reutilizável de uma chamada HTTP.

Separada da Specification.

Contém:

* Método
* Path
* Auth necessária
* Payload base (opcional)
* Expectativas comuns

---

## **IV. Modelo de Dados do MVP**

### **Entidades Principais**

| Entidade              | Propósito              |
| --------------------- | ---------------------- |
| `test_projects`       | Raiz do módulo         |
| `environments`        | Contextos de execução  |
| `global_variables`    | Reuso e parametrização |
| `domains`             | Organização semântica  |
| `api_calls`           | Chamadas reutilizáveis |
| `test_specifications` | Especificação manual   |
| `test_scenarios`      | Testes gerados pela IA |

---

### **test_specifications**

Entrada principal do usuário.

Campos relevantes:

* `method`
* `baseUrl` (preferencialmente variável)
* `path`
* `requiresAuth`
* `description`
* `exampleRequest` (JSON no MVP)
* `requiresApproval` (boolean)

---

### **test_scenarios**

Artefato final da IA.

Campos:

* `title`
* `domainId`
* `tags`
* `abstractModel` (JSON estruturado)
* `generatedGherkin` (TEXT, editável)
* `status` (`DRAFT`, `APPROVED`, `ERROR`)

⚠️ **Decisão de design chave**
`generatedGherkin` é salvo como **TEXT** para permitir:

* Edição manual
* Correção pós-geração
* Evolução low-code / high-code

---

## **V. Convenção de MicroSteps (Karate DSL)**

Como Karate não possui steps nativos, adotamos uma convenção.

```gherkin
# STEP 1: Criar duplicata válida
Given url baseUrl
And path '/duplicates'
And request payload

# STEP 2: Validar criação
When method post
Then status 201
```

Regras:

* `# STEP {n}: descrição`
* Tudo abaixo pertence ao STEP até o próximo
* IA deve respeitar essa convenção **sempre**

---

## **VI. Fluxo de Geração de Testes (Atualizado)**

### **Fluxo Assíncrono Orquestrado**

1. **Usuário cria Specification**

   * Status: `DRAFT`

2. **Usuário solicita geração**

   * `POST /v1/test-specifications/{id}/generate`

3. **Aegis Tests prepara contexto**

   * Project
   * Environment
   * Variables
   * Domains
   * API Calls
   * Specification

4. **Aegis Tests envia requisição ao Aegis Agents**

---

### **Pipeline de IA (Interno ao Aegis Agents)**

**Agente 1 — Planejamento**

* Entende o contexto
* Define Scenarios
* Cria `ScenarioDraft (JSON)`
* Valida coerência funcional

**(Opcional) Aprovação Humana**

* Se `requiresApproval = true`
* Usuário revisa plano

**Agente 2 — Geração**

* Gera Karate DSL
* Aplica microSteps
* Usa variáveis globais
* Usa baseUrls corretas

**Agente 3 — Auto-validação**

* Executa mentalmente / tecnicamente
* Detecta erros grotescos
* NÃO “corrige” regra de negócio errada
* Marca teste como `ERROR` se inválido

---

5. **Aegis Agents retorna resultado**
6. **Aegis Tests persiste**
7. **Frontend é notificado via evento**

---

## **VII. Comunicação e Eventos**

O MVP **já assume arquitetura orientada a eventos**.

Eventos internos:

* `TEST_GENERATION_STARTED`
* `TEST_PLAN_CREATED`
* `TEST_GENERATION_FAILED`
* `TEST_GENERATION_COMPLETED`

Uso:

* Atualização de UI
* Logs
* Auditoria
* Futuro WebSocket / SSE

---

## **VIII. Endpoints Essenciais do MVP**

### **TestProject**

* `POST /v1/projects/{projectId}/test-project`

### **Specification**

* `POST /v1/test-specifications`
* `POST /v1/test-specifications/{id}/generate`
* `POST /v1/test-specifications/{id}/approve`

### **Generation (interno)**

* `POST /internal/generation/start`
* `POST /internal/generation/plan`
* `POST /internal/generation/finish`

---

## **IX. Ordem Recomendada de Desenvolvimento**

### **FASE 1 — Fundação**

* TestProject
* Environment
* Global Variables
* Domain
* API Call
* Specification

### **FASE 2 — Orquestração**

* Pipeline de geração
* DTOs claros
* Mocks de IA

### **FASE 3 — IA**

* Planejamento
* Geração
* Auto-validação

### **FASE 4 — UI**

* Aprovação
* Edição
* Feedback visual

---

## **X. Princípios de Design (Importantes para o Copilot)**

* ❌ IA não corrige regra de negócio errada
* ✅ IA falha explicitamente
* ✅ Tudo é versionável
* ✅ Nada é “mágico”
* ✅ Tudo é editável depois