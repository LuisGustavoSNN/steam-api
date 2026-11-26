# Steam Library — API REST

![Java](https://img.shields.io/badge/Java-25-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.2.0-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

## Sobre o projeto

Steam Library é um projeto acadêmico de biblioteca digital de jogos inspirado na Steam. A página exibe a foto, o nome **pythoN** e o **nível 12** sobre o fundo animado do perfil. A animação começa automaticamente, sem som.

A interface responsiva usa HTML, CSS e JavaScript. A API REST usa Java 25, Spring Boot, Spring Web MVC, Spring Data JPA e Hibernate para cadastrar e consultar jogos no MySQL.

## Como a biblioteca funciona

| Modo de acesso | Funcionalidades | Armazenamento |
| --- | --- | --- |
| Abrir `src/main/resources/static/index.html` diretamente no navegador | Cadastrar, editar e excluir jogos da coleção | `localStorage` do navegador |
| Abrir `http://localhost:8080/` com o Spring Boot em execução | Consultar e cadastrar jogos pela API. Editar jogos e atribuir preços neste navegador | MySQL para cadastro e consulta; navegador para edições e preços |

Nos dois modos, a biblioteca oferece pesquisa por nome ou desenvolvedora, filtro por gênero, ordenação, capas e descrições. No modo local, ela também calcula a quantidade de jogos gratuitos e o valor da coleção a partir dos preços cadastrados.

A API atual oferece `GET` e `POST`. Ao usar a interface pelo Spring, as edições e os preços ficam no navegador e a exclusão está disponível no modo local. Os jogos da API sem preço informado aparecem com **Preço não cadastrado**. A coleção local e o catálogo MySQL são independentes.

## Estrutura

```text
steam-api/
├── src/
│   ├── main/
│   │   ├── java/com/dest1/steam/
│   │   │   ├── controllers/JogoController.java
│   │   │   ├── entities/Jogo.java
│   │   │   ├── repositories/JogoRepository.java
│   │   │   └── SteamApplication.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── index.html
│   │       │   ├── biblioteca.html
│   │       │   ├── avatar-python.png
│   │       │   └── perfil-fundo.{jpg,mp4,webm}
│   │       ├── application.properties
│   │       └── application-mysql.properties
│   └── test/java/com/dest1/steam/
│       ├── JogoControllerIntegrationTests.java
│       └── SteamApplicationTests.java
├── pom.xml
├── mvnw
└── mvnw.cmd
```

O `JogoController` recebe requisições HTTP e usa o `JogoRepository` para persistir ou consultar entidades `Jogo`, mapeadas para a tabela `tb_jogo`.

## Endpoints da API

| Método | Endpoint | Operação | Resposta de sucesso |
| --- | --- | --- | --- |
| `GET` | `/api/jogos` | Lista os jogos cadastrados | `200 OK` com uma lista JSON |
| `POST` | `/api/jogos` | Cadastra um jogo | `200 OK` com o jogo salvo e seu `id` |

Exemplo de corpo JSON para `POST /api/jogos`, com `Content-Type: application/json`.

```json
{
  "nome": "Portal 2",
  "genero": "Puzzle",
  "criador": "Valve",
  "ano": 2011,
  "modo": "Individual e cooperativo",
  "classificacao": "10 anos",
  "descricao": "Jogo de quebra-cabeças com portais.",
  "capa": "",
  "disponivel": true
}
```

Para listar os jogos:

```bash
curl http://localhost:8080/api/jogos
```

## Como executar

Para a API, use JDK 25, MySQL em execução e acesso às dependências do Maven. O projeto inclui o Maven Wrapper.

```bash
git clone https://github.com/LuisGustavoSNN/steam-api.git
cd steam-api
```

O banco padrão é `steam`. Configure as variáveis de ambiente conforme sua instalação do MySQL.

| Variável | Uso |
| --- | --- |
| `DB_URL` | URL JDBC. O padrão usa `jdbc:mysql://localhost:3306/steam?createDatabaseIfNotExist=true` |
| `DB_USER` | Usuário MySQL. O padrão é `root` |
| `DB_PASSWORD` | Senha do usuário MySQL. Defina o valor da sua instalação |

O Hibernate usa `ddl-auto=update`. A senha padrão é vazia. Se o seu MySQL exige senha, defina `DB_PASSWORD` antes de iniciar a aplicação. Mantenha credenciais reais nas variáveis de ambiente.

No Linux ou macOS:

```bash
./mvnw spring-boot:run
```

No Windows, pelo PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

Abra a interface em `http://localhost:8080/` ou consulte a API em `http://localhost:8080/api/jogos`.

## Testes

Os testes verificam a inicialização do Spring e o cadastro e a listagem via HTTP. Eles usam H2 em memória.

```bash
./mvnw test
```

No Windows:

```powershell
.\mvnw.cmd test
```

## Melhorias futuras

- Completar a integração de cadastro, edição e exclusão entre a interface e a API.
- Adicionar consulta por ID, validação de dados e tratamento de erros.
- Unificar os campos da interface e do modelo Java, incluindo o preço.
- Padronizar o tipo do identificador entre a entidade e o repositório.

## Autor

Desenvolvido por **Luis Gustavo**. [GitHub](https://github.com/LuisGustavoSNN)
