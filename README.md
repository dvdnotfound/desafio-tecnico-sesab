# Sistema de Gerenciamento de Salas de Espera para Teleconsulta

Sistema desenvolvido em Java EE para gerenciamento de salas de espera em teleconsultas, com validação de conflitos de horário e consulta de disponibilidade.

## 🚀 Tecnologias

- **Java 8**
- **Maven 3.8+**
- **JSF 2.3** - Framework web
- **PrimeFaces 12.0** - Componentes UI
- **Hibernate 5.6** - ORM
- **JPA 2.2** - Persistência
- **H2 Database** - Banco de dados embarcado
- **WildFly 26** - Servidor de aplicação
- **Docker & Docker Compose** - Containerização

## 📋 Funcionalidades

### CRUDs Completos
- ✅ **Usuários** - Nome, e-mail, CPF, período de cadastro
- ✅ **Pacientes** - Dados completos (nome, CPF, RG, CNS, etc)
- ✅ **Unidades de Saúde** - Nome, razão social, CNPJ, CNES
- ✅ **Salas** - Nome, capacidade, unidade de saúde
- ✅ **Reservas** - Data/hora início/fim, sala, usuário

### Funcionalidades Especiais
- ✅ **Validação de Conflitos** - Impede reservas sobrepostas para a mesma sala
- ✅ **Consulta de Disponibilidade** - Lista salas disponíveis por período e unidade
- ✅ **Cancelamento de Reservas** - Soft delete mantendo histórico

### Requisitos Técnicos Atendidos
- ✅ Arquitetura MVC em camadas
- ✅ Consultas com **Criteria API** (obrigatório)
- ✅ Validação de dados com Bean Validation
- ✅ Interface responsiva com PrimeFaces
- ✅ Deploy em container Docker

## 🏗️ Arquitetura
```
teleconsulta-salas/
├── src/main/java/br/com/teleconsulta/
│   ├── model/           # Entidades JPA
│   ├── config/          # Configuração (JPAUtil)
│   ├── dao/             # Data Access Objects
│   ├── service/         # Lógica de negócio
│   └── controller/      # ManagedBeans JSF
├── src/main/resources/
│   └── META-INF/
│       └── persistence.xml
├── src/main/webapp/
│   ├── WEB-INF/
│   │   ├── web.xml
│   │   ├── beans.xml
│   │   └── faces-config.xml
│   ├── resources/css/
│   └── *.xhtml          # Páginas JSF
├── Dockerfile
├── docker-compose.yml
└── pom.xml
```

## 🐳 Como Executar com Docker

### Pré-requisitos
- Docker
- Docker Compose

### Passo 1: Build da aplicação
```bash
mvn clean package
```

### Passo 2: Construir e iniciar containers
```bash
docker-compose up -d --build
```

### Passo 3: Acessar a aplicação
```
Aplicação: http://localhost:8080/teleconsulta-salas/
Console WildFly: http://localhost:9990
  Usuário: admin
  Senha: Admin#123
```

### Comandos úteis
```bash
# Ver logs
docker-compose logs -f

# Parar containers
docker-compose down

# Parar e remover volumes (limpar dados)
docker-compose down -v

# Reconstruir imagem
docker-compose up -d --build
```

## 💻 Como Executar Localmente (sem Docker)

### Pré-requisitos
- Java 8
- Maven 3.8+
- WildFly 26 ou Tomcat 9

### Passo 1: Compilar
```bash
mvn clean package
```

### Passo 2: Deploy
```bash
# WildFly
cp target/teleconsulta-salas.war $WILDFLY_HOME/standalone/deployments/

# Ou usar Maven Jetty
mvn jetty:run
```

### Passo 3: Acessar
```
http://localhost:8080/teleconsulta-salas/
```

## 📊 Modelo de Dados
```sql
USUARIO (id, nome, email, cpf, data_cadastro)
    ↓ 1:N
RESERVA (id, data_hora_inicio, data_hora_fim, usuario_id, sala_id, ativa)
    ↓ N:1
SALA (id, nome, capacidade, unidade_saude_id)
    ↓ N:1
UNIDADE_SAUDE (id, nome, razao_social, sigla, cnpj, cnes)

PACIENTE (id, nome, nome_social, sexo, nome_mae, nome_pai, 
          telefone, email, cpf, rg, cns, data_nascimento, endereco)
```

## 🧪 Testando Validação de Conflitos

1. Acesse: http://localhost:8080/teleconsulta-salas/reserva.xhtml
2. Crie uma reserva para uma sala (ex: 10:00 - 11:00)
3. Tente criar outra reserva para a mesma sala com horário sobreposto (ex: 10:30 - 11:30)
4. Sistema deve bloquear e mostrar mensagem de conflito

## 🧪 Testando Consulta de Disponibilidade

1. Acesse: http://localhost:8080/teleconsulta-salas/sala.xhtml
2. Preencha: Unidade de Saúde, Data/Hora Início e Fim
3. Clique em "Consultar Disponibilidade"
4. Sistema lista apenas salas SEM conflitos no período

## 📁 Persistência de Dados

O banco H2 armazena dados em: `./data/teleconsulta.mv.db`

Para limpar dados:
```bash
rm -rf data/
```

## 🛠️ Tecnologias e Versões

| Tecnologia | Versão |
|------------|--------|
| Java | 8 |
| Maven | 3.8+ |
| JSF (Mojarra) | 2.3.9 |
| PrimeFaces | 12.0.0 |
| Hibernate | 5.6.15.Final |
| H2 Database | 2.1.214 |
| WildFly | 26.1.3.Final |

## 👤 Autor

Desenvolvido como desafio técnico para demonstração de conhecimentos em:
- Java EE
- Arquitetura MVC
- JPA/Hibernate com Criteria API
- JSF e PrimeFaces
- Containerização com Docker

## 📝 Licença

Este projeto foi desenvolvido para fins educacionais e de avaliação técnica.
