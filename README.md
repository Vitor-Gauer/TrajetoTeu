# Trajeto Teu — Trabalho 4: Arquitetura MVVM

Evolução do projeto **Trajeto Teu** (Trabalho 4 da A2 — continuação do Trabalho 3), refatorando a aplicação para o padrão de arquitetura **MVVM (Model-View-ViewModel)** com **Repository**, **StateFlow/UiState**, **DAO Reativo (Flow)** e **ViewModelFactory**.

---

## Como Rodar o Projeto
1. Clone o repositório ou abra o projeto no **Android Studio**.
2. Certifique-se de estar usando o **Gradle Wrapper** e sincronize o projeto.
3. Execute o app em um emulador ou dispositivo físico Android (API 24+).

---

## Organização do Código em Pacotes (`3.1`)

O projeto foi totalmente modularizado em pacotes com responsabilidades bem definidas:

* `com.example.trajetoteu.ui.screens`: Contém exclusivamente a interface visual (`@Composable`), **com cada tela em seu próprio arquivo Kotlin**, sem nenhuma regra de negócio ou lógica de banco.
  * `Screen01UserRole.kt` até `Screen11Profile.kt`
  * `Screen12LessonAndTrail.kt`
  * `ScreenTrilhasLista.kt`
  * `ScreenTrilhaDetalhe.kt`
  * `ScreenTopicosLista.kt`
  * `ScreenTopicoDetalhe.kt`
  * `ExplorerMascot.kt`
* `com.example.trajetoteu.ui.navigation`: `AppNavigation.kt` (gerencia as rotas do NavHost e a injeção de dependências dos ViewModels).
* `com.example.trajetoteu.data.local`: Entidades `@Entity`, interfaces `@Dao` e a classe `@Database` do Room (`AppDatabase.kt`).
* `com.example.trajetoteu.data.repository`: Camada de Repositórios (`TrilhaRepository.kt`, `TopicoRepository.kt`), sendo a única fonte de verdade.
* `com.example.trajetoteu.viewmodel`: Gerenciamento de estado e lógica de UI (`TrilhasViewModel.kt`, `TopicosViewModel.kt`, `LessonViewModel.kt`, acompanhados de suas data classes `UiState` e `ViewModelFactory`s).
* `com.example.trajetoteu.model`: Data classes do domínio (`TrilhaConhecimento`, `TopicoEstudo`).

---

## Documentação do Processo e Decisões de Arquitetura (`Seção 4`)

### 1. Como estava o projeto no Trabalho 3, e o que mudou na reorganização?
* **Trabalho 3:** As telas `@Composable` acessavam o `@Dao` diretamente, disparavam `CoroutineScope(Dispatchers.IO)` manualmente e gerenciavam o estado da UI espalhado em múltiplas variáveis `remember { mutableStateOf(...) }`.
* **Trabalho 4:** Refatoração completa para a arquitetura **MVVM**. As telas tornaram-se 100% declarativas e isentas de chamadas ao banco ou corrotinas. O estado de cada tela é centralizado em uma `data class UiState` exposta por um `StateFlow`, e os DAOs do Room retornam `Flow` reativo.

### 2. Como o trio decidiu dividir os pacotes?
Divisão estrita em 4 camadas principais:
* `ui`: Telas isoladas em arquivos Kotlin individuais para facilitar manutenção.
* `data.local`: Mapeamento das tabelas SQLite e DAOs com Room.
* `data.repository`: Repositórios por entidade.
* `viewmodel`: ViewModels, UiStates e ViewModelFactorys.

### 3. Por que o Repository existe — o que ele resolve que não existia antes?
O **Repository** é o intermediário que abstrai a origem dos dados para a aplicação. Antes, a UI dependia diretamente das consultas do Room. Com o Repository:
* O ViewModel interage apenas com o Repository, sem saber se a fonte é o Room, um cache em memória ou uma API REST futura.
* Se a fonte de dados for alterada no futuro, **somente a classe Repository é modificada**, mantendo os ViewModels e as telas de UI totalmente intocados.

### 4. O que tem dentro do UiState de cada tela, e por que escolheram esses campos?
* **`TrilhasUiState`**: `trilhas` (lista reativa `List<TrilhaConhecimento>`), `novoTitulo` e `novaDesc` (campos do formulário de criação), `trilhaAtual` e `topicosDaTrilhaAtual` (dados para a tela de detalhes).
* **`TopicosUiState`**: `topicos` (lista reativa `List<TopicoEstudo>`), `trilhas` (lista de apoio para vinculação), campos de formulário (`novoTitulo`, `novaPergunta`, `novaOpcaoCorreta`, `novaOpcaoIncorreta`, `novaDica`, `selectedTrilhaId`), `topicoAtual` e `trilhaPaiDoTopicoAtual`.
* **`LessonUiState`**: `topico` (dados da pergunta e respostas), `feedbackState` (estado visual da resposta: NEUTRAL, MISTAKE, SUCCESS), `isMuted` (áudio).
* *Motivo da escolha:* Agrupar todo o estado em uma única `data class` imutável garante consistência, evita corridas de estado e facilita a renderização na UI via `collectAsState()`.

### 5. Alguma funcionalidade quebrou durante a reorganização? Como encontraram e corrigiram?
Ao migrar a consulta `buscarTodos()` do DAO de `suspend fun` para retorno de `Flow<List<T>>` reativo:
* Os métodos de inserção e exclusão no Room atualizam as tabelas e o Room notifica o `Flow` automaticamente.
* Ajustamos os ViewModels para coletar o `Flow` no `viewModelScope.launch` no `init`. Com isso, eliminamos a necessidade de refazer a busca manualmente após cada cadastro/exclusão, tornando a atualização instantânea e sem travamentos.

### 6. Algum integrante do trio teve dificuldade em algum ponto? Como resolveram?
A maior dificuldade foi entender a injeção do Repository no ViewModel via `ViewModelProvider.Factory` no Compose. Resolvemos criando as factories dedicadas (`TrilhasViewModelFactory`, `TopicosViewModelFactory`, `LessonViewModelFactory`) e instanciando-as no nível do `AppNavigation` com `viewModel(factory = ...)`.

---

## Teste de Aceitação e Persistência Reativa
1. Abra a tela de **Trilhas** ou **Tópicos**.
2. Adicione ou remova um item. Note que a lista atualiza **instantaneamente** na tela via `Flow` do Room + `StateFlow` do ViewModel.
3. Feche o aplicativo completamente (remova das tarefas recentes).
4. Abra o app novamente: todos os dados criados permanecem persistidos no SQLite via Room com arquitetura MVVM.

## Apresentação & Documentação
* **Link para os Slides da Apresentação:** [Apresentação do Trabalho 4 - Google Slides](https://docs.google.com/presentation/d/1E0_bnxdOMFq3yF3yayZcQaP9HKzefT8PdOvjwr9XgV8/edit?usp=sharing)
