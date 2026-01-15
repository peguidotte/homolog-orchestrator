# HU10_00 - Criar Projeto de Testes Automatizados

## Objetivo
Implementar o endpoint `POST /v1/projects/{projectId}/test-projects` para criação de um `TestProject`.

## Checklist de Implementação

### 1. Preparação de Infraestrutura
- [x] Adicionar dependência `springdoc-openapi` ao `pom.xml`
- [x] Ajustar entidade `TestProject` (campo `description` para 1000 chars, índice único)

### 2. Criar Entidades e Repositórios
- [x] Criar entidade `Environment` com relacionamento `@ManyToOne` para `TestProject`
- [x] Criar `EnvironmentRepository`

### 3. Criar DTOs
- [x] `CreateTestProjectRequestDTO` (record com Bean Validation)
- [x] `TestProjectResponseDTO` (record imutável)
- [x] `ErrorResponseDTO` (record para erros padronizados)

### 4. Criar Exceções e Handler
- [x] `TestProjectLimitReachedException`
- [x] `TestProjectNameAlreadyExistsException`
- [x] `GlobalExceptionHandler` com `@RestControllerAdvice`

### 5. Criar Service (TDD)
- [x] Escrever testes unitários para `TestProjectService`
- [x] Implementar `TestProjectService`

### 6. Criar Controller
- [x] Implementar `TestProjectController` com Swagger docs
- [x] Escrever testes do controller

### 7. Configuração
- [x] Criar `SecurityConfig` (MVP - endpoints públicos)
- [x] Criar `OpenApiConfig` para Swagger

### 8. Documentação
- [x] Atualizar `data_model.md`

## Regras de Negócio Implementadas
| Código | Descrição | Status |
|--------|-----------|--------|
| RC10.01.1 | `name` não vazio, max 255 chars | ✅ |
| RC10.01.2 | `description` max 1000 chars | ✅ |
| RN10.01.1 | Projeto Core deve existir (mock) | ⏳ (TODO: integração futura) |
| RN10.01.2 | Limite de 1 TestProject por projeto | ✅ |
| RN10.01.3 | Nome único por projeto | ✅ |

## Arquivos Criados/Modificados

### Entidades
- `src/main/java/.../model/entity/TestProject.java` (modificado)
- `src/main/java/.../model/entity/Environment.java` (novo)

### DTOs
- `src/main/java/.../model/dto/CreateTestProjectRequestDTO.java`
- `src/main/java/.../model/dto/TestProjectResponseDTO.java`
- `src/main/java/.../model/dto/ErrorResponseDTO.java`

### Repositórios
- `src/main/java/.../repository/TestProjectRepository.java` (modificado)
- `src/main/java/.../repository/EnvironmentRepository.java` (novo)

### Services
- `src/main/java/.../services/TestProjectService.java`

### Controllers
- `src/main/java/.../controller/TestProjectController.java`

### Exceções
- `src/main/java/.../exception/TestProjectLimitReachedException.java`
- `src/main/java/.../exception/TestProjectNameAlreadyExistsException.java`
- `src/main/java/.../exception/GlobalExceptionHandler.java`

### Configuração
- `src/main/java/.../config/SecurityConfig.java`
- `src/main/java/.../config/OpenApiConfig.java`

### Testes
- `src/test/java/.../service/TestProjectServiceTest.java`
- `src/test/java/.../controller/TestProjectControllerTest.java`

## Fluxo Técnico Implementado
1. ✅ Validar campos (Bean Validation → 400)
2. ⏳ Validar existência do Projeto Core (mock → assume válido)
3. ✅ Validar limite de TestProjects (RN10.01.2 → 422)
4. ✅ Validar unicidade do nome (RN10.01.3 → 409)
5. ✅ Persistir TestProject
6. ✅ Criar Environment "Default" automaticamente
7. ✅ Retornar 201 Created com DTO

