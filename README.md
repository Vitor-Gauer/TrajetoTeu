# Trajeto Teu - Mínimo Aplicativo Funcional (MAF)

O **Trajeto Teu** é um aplicativo educacional para Android construído em Kotlin com Jetpack Compose, inspirado na plataforma Brilliant.org. Possui uma jornada gamificada de aprendizado, mascote adaptativo e navegação por rotas dinâmicas.

---

## Como Executar o Projeto

1. **Pré-requisitos**:
    * Android Studio Ladybug (ou versão recente).
    * JDK 17+.
    * Emulador Android ou dispositivo físico (API 26+ / Android 8.0+).
2. **Passos para rodar**:
    * Clone o repositório em sua máquina:
      ```bash
      git clone [https://github.com/vitor-gauer/trajeto-teu.git](https://github.com/vitor-gauer/trajeto-teu.git)
      ```
    * Abra o Android Studio e selecione `Open...` apontando para a pasta raiz do repositório.
    * Aguarde a sincronização do Gradle.
    * Selecione o dispositivo (emulador/físico) e clique em **Run (Shift + F10)**.

---

## Estrutura e Navegação do App (MNavHost)

A navegação é centralizada no `AppNavigation.kt` através do Jetpack Compose Navigation (`NavHost` / `NavController`), contemplando mais de 12 telas interligadas:

1. **Onboarding (Telas 1 a 8)**:
    * **Tela 1**: Seleção de Objetivo (*Aprender* ou *Ensinar*).
    * **Tela 2**: Escolha de Motivações.
    * **Tela 3**: Ajuste de Acessibilidade & Narração por Voz.
    * **Tela 4**: Perfil Demográfico e Interesses em Chips.
    * **Tela 5**: Autoavaliação de Nível Técnico.
    * **Tela 6**: Teste Diagnóstico Interativo.
    * **Tela 7**: Definição de Meta Diária de Estudo.
    * **Tela 8**: Autenticação / Cadastro.
2. **Navegação Principal & BottomBar**:
    * **Tela 9 (Dashboard)**: Busca e Carrossel de Trilhas Recomendadas.
    * **Tela 10 (Discovery)**: Áreas Inscritas e Cursos Recomendados.
    * **Tela 11 (Profile)**: Métricas com alternadores de tempo (7, 30, 365 dias).
3. **Requisitos de Negócio MAF (Listas, CRUD e Detalhes)**:
    * **Lista 1 - Trilhas (`ScreenTrilhasLista`)**: Lista reativa (`TrilhaConhecimento`) com suporte a **Adicionar** e **Remover**.
    * **Detalhe 1 - Trilha (`ScreenTrilhaDetalhe`)**: Exibe as informações da Trilha e executa um **cálculo em tempo real de porcentagem de progresso e tempo estimado de conclusão** com base nos Tópicos vinculados.
    * **Lista 2 - Tópicos (`ScreenTopicosLista`)**: Lista reativa (`TopicoEstudo`) com suporte a **Adicionar**, **Remover** e marcar estado via **Checkbox**.
    * **Detalhe 2 - Tópico (`ScreenTopicoDetalhe`)**: Permite alterar o estado de conclusão do tópico e navegar para o exercício interativo.
4. **Experiência de Aprendizado**:
    * **Tela 12 (Lesson & Trail)**: Mapa diagonal com nó interativo e mascote dinâmico trocando de emoção e itens de acordo com o feedback.

---

## Decisões de Design
* **Cores**: Deep Navy (`#0F172A`), Emerald Green (`#10B981`), Warm Yellow (`#F59E0B`).
* **Mascote**: *Explorer Mascot* em gráficos 2D em Compose.