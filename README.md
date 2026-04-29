# 🚀 CodeNexus — Sprint 2

> Segunda versão do projeto **CodeNexus**, desenvolvida em grupo pelos membros da startup. Este repositório contempla os entregáveis da Sprint 2, com evolução significativa em relação ao Sprint 1: inclusão de backend Java, banco de dados containerizado e visualização de dados aprimorada.

---

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Estrutura do Repositório](#-estrutura-do-repositório)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Pré-requisitos](#-pré-requisitos)
- [Módulos](#-módulos)
  - [codenexus-v2](#codenexus-v2)
  - [mysql](#mysql)
  - [web-data-viz](#web-data-viz)
- [Contribuidores](#-contribuidores)
- [Histórico de Versões](#-histórico-de-versões)

---

## 📌 Sobre o Projeto

O **CodeNexus** é uma plataforma desenvolvida por uma startup em estágio inicial, com foco em conectar desenvolvedores e facilitar o acesso a dados relevantes do ecossistema de tecnologia. O Sprint 2 representa a segunda iteração do produto, incorporando:

- Backend estruturado com **Java**
- Banco de dados relacional com **MySQL** containerizado via **Docker**
- Frontend enriquecido com **HTML, CSS e JavaScript**
- Módulo dedicado de **visualização de dados**

---

## 📁 Estrutura do Repositório

```
Sprint2/
├── codenexus-v2/       # Aplicação backend (Java)
├── mysql/              # Configuração do banco de dados (Docker + MySQL)
├── web-data-viz/       # Frontend e visualização de dados (HTML/CSS/JS)
├── .gitignore
└── README.md
```

---

## 🛠 Tecnologias Utilizadas

| Tecnologia   | Uso                                      | Participação |
|--------------|------------------------------------------|--------------|
| HTML         | Estrutura das páginas web                | ~42%         |
| CSS          | Estilização e layout                     | ~29%         |
| JavaScript   | Interatividade e lógica de frontend      | ~18%         |
| Java         | Backend e lógica de negócio              | ~11%         |
| Dockerfile   | Containerização do banco de dados        | <1%          |
| MySQL        | Banco de dados relacional                | —            |
| Docker       | Orquestração de containers               | —            |

---

## ✅ Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

- [Java 17+](https://adoptium.net/)
- [Maven](https://maven.apache.org/) ou [Gradle](https://gradle.org/)
- [Docker](https://www.docker.com/) e [Docker Compose](https://docs.docker.com/compose/)
- Navegador web moderno (Chrome, Firefox, Edge)

---

## 📦 Módulos

### `codenexus-v2`

Módulo de backend desenvolvido em **Java**. Responsável pela lógica de negócio da aplicação, expondo APIs e integrando-se com o banco de dados MySQL.

**Principais responsabilidades:**
- Processamento e persistência de dados
- Exposição de endpoints para o frontend
- Integração com o banco de dados relacional

---

### `mysql`

Módulo de infraestrutura de banco de dados. Utiliza **Docker** para provisionar um container MySQL de forma isolada e reproduzível.

**Principais responsabilidades:**
- Definição do schema do banco de dados
- Scripts de inicialização e seed de dados
- Configuração via Dockerfile e variáveis de ambiente

---

### `web-data-viz`

Módulo de **frontend e visualização de dados**, desenvolvido com HTML, CSS e JavaScript puro. Apresenta as informações processadas pelo backend em formato visual e interativo.

**Principais responsabilidades:**
- Interface de usuário
- Consumo de APIs do backend
- Exibição de gráficos e dashboards de dados

---

## 👥 Contribuidores

Projeto desenvolvido pelos membros do **Grupo 3 — CodeNexus** como entregável acadêmico/startup.

> Para ver os contribuidores, acesse a aba [Contributors](https://github.com/CodeNexus-Grupo-3/Sprint2/graphs/contributors) no GitHub.

---

## 📜 Histórico de Versões

| Versão  | Repositório                                                                 | Descrição                                      |
|---------|-----------------------------------------------------------------------------|------------------------------------------------|
| Sprint 1 | [Sprint1](https://github.com/CodeNexus-Grupo-3/Sprint1)                   | Primeira versão — foco em frontend e estrutura |
| Sprint 2 | [Sprint2](https://github.com/CodeNexus-Grupo-3/Sprint2) *(atual)*         | Backend Java, MySQL Docker, visualização avançada |

---

<div align="center">
  <sub>Desenvolvido com ❤️ pela startup <strong>CodeNexus — Grupo 3</strong></sub>
</div>
