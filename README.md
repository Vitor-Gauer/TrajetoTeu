# Trajeto Teu — Trabalho 3: Persistência de Dados com Room

Evolução do projeto **Trajeto Teu** (Trabalho 3 da A2), implementando a camada de persistência local com **Room (SQLite)**.

---

## 🚀 Como Rodar o Projeto
1. Clone o repositório ou abra o projeto no **Android Studio**.
2. Certifique-se de estar usando o **Gradle Wrapper** e sincronize o projeto.
3. Execute o app em um emulador ou dispositivo físico Android (API 24+).

---

## 🏗️ Decisões de Arquitetura e Implementação (Trabalho 3)

### 1. Migração de Memória para Room
* **Trabalho 2:** Os dados de trilhas e tópicos eram armazenados em memória volátil (`mutableStateListOf`), o que causava a perda de todos os dados cadastrados ou alterados sempre que o aplicativo era fechado.
* **Trabalho 3:** Implementação completa da camada de banco de dados com **Room**. As data classes `TrilhaConhecimento` e `TopicoEstudo` foram transformadas em `@Entity` (com `id: Int = 0` e `@PrimaryKey(autoGenerate = true)`).

### 2. Estrutura do Room
* **@Entity:** `TrilhaConhecimento` (tabela `trilhas`) e `TopicoEstudo` (tabela `topicos`).
* **@Dao:** `TrilhaDao` e `TopicoDao` contendo os métodos CRUD básicos (`@Insert`, `@Query`, `@Update`, `@Delete`) implementados como funções `suspend`, além de consultas filtradas por ID (`buscarPorId(id: Int)`) e por relacionamento (`buscarPorTrilha(trilhaId: Int)`).
* **AppDatabase:** Classe abstrata Singleton (`@Volatile` + `synchronized` + `Room.databaseBuilder`) com pre-população automática de dados iniciais na primeira criação (`onCreate`).

### 3. Boas Práticas de Concorrência e UI
* Todas as operações de banco de dados são executadas assincronamente dentro de escopos de corrotinas em `Dispatchers.IO`, garantindo que a thread principal nunca seja travada.
* As telas de listagem (`ScreenTrilhasLista`, `ScreenTopicosLista`) carregam os dados do banco ao abrir utilizando `LaunchedEffect(Unit)`.
* As telas de detalhes (`ScreenTrilhaDetalhe`, `ScreenTopicoDetalhe`) buscam os itens diretamente do banco a partir do `id` recebido via navegação, utilizando a consulta filtrada do DAO (`buscarPorId`).

---

## 🧪 Teste de Aceitação e Persistência
Para comprovar o funcionamento da persistência:
1. Abra a tela de **Trilhas** ou **Tópicos** e adicione um novo item.
2. Feche o aplicativo completamente (remova dos recém-abertos/recentes).
3. Abra o aplicativo novamente.
4. O item recém-adicionado continuará lá, persistido no banco SQLite local via Room.
