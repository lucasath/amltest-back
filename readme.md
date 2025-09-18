# Projeto Backend - Gerenciamento de Tarefas

## Tecnologias Utilizadas
- **Java 21** com **Spring Boot 3**
- Gerenciamento de dependências via **Maven** ou **Gradle**
- Persistência com **JPA/Hibernate**
- Ferramento de auxílio **Lombok**
- Banco de dados **H2** (em memória)
- Testes unitários com **JUnit** e **Mockito**

---

## Estrutura do Projeto

### Entidade Tarefa
A entidade `Tarefa` possui a seguinte estrutura:

| Campo      | Tipo                     | Observações                        |
|------------|--------------------------|------------------------------------|
| id         | long                     | Identificador único                |
| titulo     | String                   | Título da tarefa                   |
| descricao  | String                   | Descrição detalhada da tarefa      |
| prazo      | java.time.LocalDate      | Data limite da tarefa              |
| concluida  | boolean                  | Indica se a tarefa está concluída (padrão: false) |
| categoria  | Categoria (enum)         | Categoria da tarefa (PESSOAL, TRABALHO, ESTUDO, etc.) |

### Enum Categoria
```java
public enum Categoria {
    PESSOAL,
    TRABALHO,
    ESTUDO
}
```

---

## Endpoints da API

### 1. Criar uma nova tarefa
**POST** `/api/tasks`  
**Request Body (JSON)**
```json
{
  "titulo": "Task 3",
  "descricao": "Task de teste numero 3",
  "prazo": "2025-09-25",
  "categoria": "PESSOAL"
}
```

### 2. Listar tarefas
**GET** `/api/tasks`  
**Parâmetros opcionais:**
- `categoria` (filtrar por categoria)
- `prazo,asc` (asc/desc para ordenar por prazo)

### 3. Marcar tarefa como concluída
**PUT** `/api/tasks/{id}/complete`

### 4. Excluir uma tarefa
**DELETE** `/api/tasks/{id}`

### 5. Listar categorias
**GET** `/api/categories`  
Retorna todos os valores do enum `Categoria`.

---

## Testes
- Testes unitários criados para a **camada de serviço** utilizando **JUnit** e **Mockito**.

---

## Rodando o Projeto
1. Clonar o repositório:
```bash
git clone github.com/lucasath/amltest-back.git
cd amltest-back
```
2. Construir o projeto (Maven):
```bash
mvn clean install
mvn spring-boot:run
```
ou (Gradle):
```bash
./gradlew build
./gradlew bootRun
```
3. Acessar a API em `http://localhost:8080`

---

## Observações
- O banco **H2** é em memória, então os dados serão perdidos ao reiniciar a aplicação.
- Endpoint de listagem retorna no formato paginado.
- Endpoints retornam **JSON** como padrão.

