Este documento define as regras de resposta para todos os endpoints do **Aegis Orchestrator**, garantindo consistência, rastreabilidade e facilidade de consumo.

---

## Padrões de Resposta de Sucesso (HTTP 2xx)

As respostas de sucesso devem ser diretas e refletir o recurso criado, modificado ou recuperado.

### Sucesso na Criação, Mutação ou Busca (POST/PUT/PATCH/GET/DELETE)

| **Status Code** | **Uso Principal** | **Corpo da Resposta** |
| --- | --- | --- |
| **201 Created** | Retorno de um `POST` bem-sucedido. | Deve retornar o objeto completo (DTO) do recurso recém-criado, incluindo seu ID. |
| **200 OK** | Retorno de um `PUT/PATCH` (atualização), `GET` (detalhe) | Retorna o objeto completo atualizado (PUT) ou o objeto solicitado (GET). |
| **204 No Content** | Retorno de um `DELETE` bem-sucedido. | O corpo da resposta deve ser **vazio**. |

**Estrutura do Detalhe/Criação (200/201):**

```json
{
  "id": "string",
  "name": "string",
  "plan": "string",
  "globalIdentifier": "string"
  // ... outros campos do DTO
}
```

### Coleções e Paginação (GET - Listagem)

Para endpoints que retornam listas (`GET /v1/org`), o retorno deve usar um objeto envelope para suportar paginação, ordenação e metadados.

| **Status Code** | **Uso Principal** | **Corpo da Resposta** |
| --- | --- | --- |
| **200 OK** | Listagem de recursos (Organizações, Cenários, etc.). | Deve retornar o objeto de paginação (Metadata + Items). |

**Estrutura de Paginação Padrão:**

```json
{
  "metadata": {
    "totalRecords": 42,
    "totalPages": 5,
    "currentPage": 1,
    "pageSize": 10,
    "hasNextPage": true
  },
  "items": [
    // Array com os objetos da Organização/Cenário
    { "id": 1, "name": "Cerc Central" },
    { "id": 2, "name": "Nestle Brasil" }
  ]
}
```

---

## Padrão de Respostas de Erro (HTTP 4xx)

Este padrão cobre todas as falhas de cliente, incluindo autenticação, autorização e validação.

### Estrutura Padrão do JSON (Array de Erros)

Para lidar com múltiplas violações de campo (`MISSING_REQUIRED_FIELD`, `INVALID_FORMAT`), a resposta deve ser um **Array JSON**, onde cada objeto representa um erro específico.

```json
[
  {
    "errorCode": "string",
    "message": "string",
    "field": "string"
  }
]
```

| **Campo** | **Tipo** | **Descrição** |
| --- | --- | --- |
| `errorCode` | String | Código interno e padronizado do erro (Ex: `INVALID_HEX_FORMAT`). Usar `UPPER_SNAKE_CASE`. |
| `message` | String | Mensagem amigável, em **Inglês**, que pode ser exibida diretamente ao usuário. |
| `field` | String | O nome do campo do DTO que causou o erro. **Opcional**, mas recomendado para erros 400 e 409. |

### Categorização de Status Code

| **Status Code** | **Categoria** | **Uso Principal** |
| --- | --- | --- |
| **400 Bad Request** | Erro de Validação de Campo | Quebra de Regras de Campo (RCs). Deve retornar **todos** os erros de campo violados no *array*. |
| **401 Unauthorized** | Erro de Autenticação | Token JWT ausente ou inválido/malformado. |
| **403 Forbidden** | Erro de Permissão/Governança | Usuário não tem o `role` necessário (Ex: não é `Admin`) ou está inativo. |
| **409 Conflict** | Erro de Unicidade | Tentativa de criar um recurso que viola chaves únicas). |
| 422 Unprocessable Entity | Erro de Regras de Negócio | Quebra de Regras de Negócio (RNs). |

### Erros de autorização (devem acontecer em todos os Endpoint protegidos):

| **Código** | **Campo(s) envolvido(s)** | **Regra de Negócio** | **Racional** | **errorCode** |
| --- | --- | --- | --- | --- |
| RNxx.1 | `[Header] authorization` | Tem que ser um Token JWT válido e não expirado. | Segurança fundamental. | `INVALID_AUTH_TOKEN` |
| **RNxx.2** | `[Header] authorization` | O ID do usuário (extraído do token) deve ser um usuário ativo na plataforma de Auth. | Garante que o usuário criador esteja habilitado (não desativado no IAM). | `USER_ACCOUNT_INACTIVE` |

---

## Padrões de Requisição (Headers e Consumo)

| **Padrão** | **Regra** | **Racional** |
| --- | --- | --- |
| **Autenticação** | Todas as rotas autenticadas devem esperar o header `Authorization: Bearer <token>`. | Padrão OAuth2/JWT. |
| **Content Type** | O corpo das requisições `POST`, `PUT` e `PATCH` deve ser `Content-Type: application/json`. | Padronização na comunicação entre Frontend e Spring. |
| **Aceitação** | O header `Accept` deve ser sempre `application/json` (a menos que seja uma rota de download de arquivo/relatório). | Padronização na resposta. |
| **Timezones** | Todas as datas e horas (incluindo `createdAt` no JPA) devem ser persistidas em **UTC**. | Garante consistência global e evita problemas de fuso horário em logs e execução. |