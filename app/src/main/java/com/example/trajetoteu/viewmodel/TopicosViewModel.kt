package com.example.trajetoteu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.trajetoteu.data.repository.TopicoRepository
import com.example.trajetoteu.data.repository.TrilhaRepository
import com.example.trajetoteu.model.TopicoEstudo
import com.example.trajetoteu.model.TrilhaConhecimento
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TopicosUiState(
    val topicos: List<TopicoEstudo> = emptyList(),
    val trilhas: List<TrilhaConhecimento> = emptyList(),
    val novoTitulo: String = "",
    val novaPergunta: String = "",
    val novaOpcaoCorreta: String = "",
    val novaOpcaoIncorreta: String = "",
    val novaDica: String = "",
    val selectedTrilhaId: Int = 1,
    val topicoAtual: TopicoEstudo? = null,
    val trilhaPaiDoTopicoAtual: TrilhaConhecimento? = null,
    val isLoading: Boolean = false
)

class TopicosViewModel(
    private val topicoRepository: TopicoRepository,
    private val trilhaRepository: TrilhaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TopicosUiState())
    val uiState: StateFlow<TopicosUiState> = _uiState.asStateFlow()

    init {
        observarDados()
    }

    private fun observarDados() {
        viewModelScope.launch {
            topicoRepository.todosTopicos.collect { lista ->
                _uiState.update { it.copy(topicos = lista) }
            }
        }
        viewModelScope.launch {
            trilhaRepository.todasTrilhas.collect { lista ->
                _uiState.update { currentState ->
                    currentState.copy(
                        trilhas = lista,
                        selectedTrilhaId = if (lista.isNotEmpty() && currentState.selectedTrilhaId == 1) lista.first().id else currentState.selectedTrilhaId
                    )
                }
            }
        }
    }

    fun onNovoTituloChange(valor: String) {
        _uiState.update { it.copy(novoTitulo = valor) }
    }

    fun onNovaPerguntaChange(valor: String) {
        _uiState.update { it.copy(novaPergunta = valor) }
    }

    fun onNovaOpcaoCorretaChange(valor: String) {
        _uiState.update { it.copy(novaOpcaoCorreta = valor) }
    }

    fun onNovaOpcaoIncorretaChange(valor: String) {
        _uiState.update { it.copy(novaOpcaoIncorreta = valor) }
    }

    fun onNovaDicaChange(valor: String) {
        _uiState.update { it.copy(novaDica = valor) }
    }

    fun onSelectedTrilhaIdChange(trilhaId: Int) {
        _uiState.update { it.copy(selectedTrilhaId = trilhaId) }
    }

    fun adicionarTopico() {
        val state = _uiState.value
        if (state.novoTitulo.isNotBlank() && state.novaPergunta.isNotBlank()) {
            viewModelScope.launch {
                topicoRepository.inserir(
                    TopicoEstudo(
                        trilhaId = state.selectedTrilhaId,
                        titulo = state.novoTitulo,
                        perguntaDesafio = state.novaPergunta,
                        opcaoCorreta = state.novaOpcaoCorreta.ifBlank { "Correta" },
                        opcaoIncorreta = state.novaOpcaoIncorreta.ifBlank { "Incorreta" },
                        dica = state.novaDica.ifBlank { "Analise bem o enunciado." }
                    )
                )
                _uiState.update {
                    it.copy(
                        novoTitulo = "",
                        novaPergunta = "",
                        novaOpcaoCorreta = "",
                        novaOpcaoIncorreta = "",
                        novaDica = ""
                    )
                }
            }
        }
    }

    fun alternarConcluido(topico: TopicoEstudo) {
        viewModelScope.launch {
            topicoRepository.atualizar(topico.copy(concluido = !topico.concluido))
            if (_uiState.value.topicoAtual?.id == topico.id) {
                val updated = topicoRepository.buscarPorId(topico.id)
                _uiState.update { it.copy(topicoAtual = updated) }
            }
        }
    }

    fun deletarTopico(topico: TopicoEstudo) {
        viewModelScope.launch {
            topicoRepository.deletar(topico)
        }
    }

    fun carregarTopicoDetalhe(topicoId: Int) {
        viewModelScope.launch {
            val topico = topicoRepository.buscarPorId(topicoId)
            val trilha = topico?.let { trilhaRepository.buscarPorId(it.trilhaId) }
            _uiState.update {
                it.copy(
                    topicoAtual = topico,
                    trilhaPaiDoTopicoAtual = trilha
                )
            }
        }
    }
}

class TopicosViewModelFactory(
    private val topicoRepository: TopicoRepository,
    private val trilhaRepository: TrilhaRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TopicosViewModel::class.java)) {
            return TopicosViewModel(topicoRepository, trilhaRepository) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida")
    }
}
