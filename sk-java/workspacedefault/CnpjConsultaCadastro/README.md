# Serviço de Consulta de CNPJ

Um serviço completo para consulta de dados de empresas através do CNPJ, desenvolvido em Java com Maven padrão, integrando ReceitaWS e SEFAZ.

## Funcionalidades

- ✅ Consulta de CNPJ via API pública (ReceitaWS)
- ✅ Integração com SEFAZ para dados fiscais
- ✅ Validação completa de CNPJ (dígitos verificadores)
- ✅ Suporte a certificados A1 para consultas SEFAZ
- ✅ Processamento XML/SOAP com JDOM
- ✅ Tratamento robusto de erros
- ✅ Logging detalhado com SLF4J/Logback
- ✅ Interface console interativa

## Pré-requisitos

- Java 1.8 ou superior
- Maven 3.6 ou superior

## Instalação

1. Clone o repositório
2. Compile o projeto:

```bash
mvn clean compile
```

3. Execute a aplicação:

```bash
mvn exec:java -Dexec.mainClass="br.com.dabu.consulta.cadastro.CnpjConsultaApplication"
```

Ou compile e execute o JAR:

```bash
mvn clean package
java -jar target/cnpj-consulta-service-1.0.0.jar
```

## Uso

### Interface Console

A aplicação oferece um menu interativo com as seguintes opções:

1. **Consultar CNPJ** - Consulta completa integrando ReceitaWS e SEFAZ
2. **Configurar certificado** - Configura certificado A1 para consultas SEFAZ
3. **Ver informações do certificado** - Exibe dados do certificado configurado
4. **Testar CNPJs de exemplo** - Testa com CNPJs pré-definidos
5. **Validar CNPJ** - Valida apenas os dígitos verificadores
6. **Sair** - Encerra a aplicação

### Configuração de Certificado

Para consultas SEFAZ, configure um certificado A1:

1. Coloque o arquivo `.p12` ou `.pfx` na pasta `src/main/resources/certificado/`
2. Use a opção 2 do menu para configurar
3. Informe o nome do arquivo e a senha

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/br/com/dabu/consulta/cadastro/
│   │   ├── CnpjConsultaApplication.java               # Classe principal
│   │   ├── service/
│   │   │   ├── CnpjConsultaService.java               # Serviço principal
│   │   │   ├── ReceitaWSService.java                  # Integração ReceitaWS
│   │   │   └── SefazConsultaCadastroService.java      # Integração SEFAZ
│   │   ├── model/
│   │   │   ├── CnpjConsultaResult.java                # Resultado consolidado
│   │   │   ├── ReceitaWSResponse.java                 # Resposta ReceitaWS
│   │   │   └── SefazConsultaCadastroResponseJDOM.java # Resposta SEFAZ
│   │   ├── config/
│   │   │   └── SefazEndpoints.java                    # Endpoints SEFAZ
│   │   └── util/
│   │       ├── CertificadoManager.java                # Gerenciamento certificados
│   │       ├── CertificadoUtil.java                   # Utilitários certificado
│   │       └── XMLUtil.java                           # Processamento XML
│   └── resources/
│       ├── certificado/                               # Pasta para certificados
│       └── logback.xml                                # Configuração logging
└── pom.xml                                            # Dependências Maven
```

## Tecnologias Utilizadas

- **Java 1.8**
- **Maven** (gerenciamento de dependências)
- **Apache HTTP Client** (requisições HTTP)
- **JDOM** (processamento XML/SOAP)
- **Jackson** (processamento JSON)
- **SLF4J/Logback** (logging)
- **JUnit 4** (testes)

## Validação de CNPJ

O sistema implementa validação completa de CNPJ incluindo:

- Verificação de formato (14 dígitos)
- Validação dos dígitos verificadores
- Verificação de CNPJs inválidos (todos os dígitos iguais)

## Integração com Frameworks

Este projeto foi desenvolvido como uma biblioteca Maven padrão, facilitando a integração com:

- **Sankhya Om** - Framework de desenvolvimento empresarial
- **Spring Boot** - Para aplicações web
- **Quarkus** - Para aplicações nativas
- **Qualquer framework Java** - Como dependência Maven

## Logs

Os logs são salvos em:

- Console (tempo real)
- Arquivo: `logs/cnpj-consulta.log`
- Rotação automática por data e tamanho (10MB)

## Exemplos de CNPJ para Teste

- **Válido**: `61.750.345/0001-57` (ReceitaWS)
- **Válido**: `11222333000181` (ReceitaWS)
- **Inválido**: `12345678000195` (dígitos verificadores incorretos)

## Licença

MIT

