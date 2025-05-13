# Sistema de Gerenciamento de Cursos Online

Este projeto é uma aplicação Spring Boot para gerenciamento de cursos online, implementada com JDBC e JPA/Hibernate, usando MySQL em Docker.

## Pré-requisitos
- Docker
- Maven
- Java 17

## Instruções para Execução

1. **Iniciar o MySQL com Docker**:
   ```bash
   docker-compose up -d
   ```

2. **Executar o projeto**:
   ```bash
   mvn spring-boot:run
   ```

3. **Testar os endpoints**:
   - Listar cursos (JDBC): `curl http://localhost:8080/cursos`
   - Criar curso (JPA): `curl -X POST -H "Content-Type: application/json" -d '{"titulo":"Java","duracaoHoras":40.0,"instrutor":{"id":1}}' http://localhost:8080/jpa/cursos`
   - Filtrar cursos: `curl "http://localhost:8080/cursos/filtro?titulo=Java&minDuracao=10"`

4. **Executar os testes**:
   ```bash
   mvn test
   ```

## Boas Práticas Aplicadas
- **JDBC**: Uso de consultas parametrizadas para evitar SQL Injection.
- **JPA**: Relacionamentos com `FetchType.LAZY` para otimizar desempenho.
- **Transações**: Uso de `@Transactional` apenas em operações de escrita.
- **Validações**: `@NotNull` e `@Positive` nas entidades JPA.
- **HikariCP**: Configurado para pooling de conexões eficiente.

## Comparação entre JDBC e JPA
- **JDBC**: Mais controle sobre as consultas SQL, mas requer mais código boilerplate para mapeamento de resultados.
- **JPA**: Abstrai o acesso ao banco com anotações e repositórios, facilitando CRUD e consultas derivadas, mas pode ser menos eficiente sem otimizações como `JOIN FETCH`.

## Evidências
- Banco MySQL: Ver logs do Docker (`docker logs <container_id>`).
- Endpoints: Teste com cURL ou Postman.
- Testes: Execute `mvn test` e verifique o relatório.