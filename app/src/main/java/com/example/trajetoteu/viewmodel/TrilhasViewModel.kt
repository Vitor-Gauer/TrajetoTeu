package com.example.trajetoteu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.trajetoteu.data.repository.TopicoRepository
import com.example.trajetoteu.data.repository.TrilhaRepository
import com.example.trajetoteu.ui.screens.MascotItem
import com.example.trajetoteu.model.TopicoEstudo
import com.example.trajetoteu.model.TrilhaConhecimento
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TrilhasUiState(
    val trilhas: List<TrilhaConhecimento> = emptyList(),
    val novoTitulo: String = "",
    val novaDesc: String = "",
    val trilhaAtual: TrilhaConhecimento? = null,
    val topicosDaTrilhaAtual: List<TopicoEstudo> = emptyList(),
    val isLoading: Boolean = false
)

class TrilhasViewModel(
    private val trilhaRepository: TrilhaRepository,
    private val topicoRepository: TopicoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TrilhasUiState())
    val uiState: StateFlow<TrilhasUiState> = _uiState.asStateFlow()

    init {
        observarTrilhas()
    }

    private fun observarTrilhas() {
        viewModelScope.launch {
            trilhaRepository.todasTrilhas.collect { lista ->
                _uiState.update { it.copy(trilhas = lista) }
            }
        }
    }

    fun onNovoTituloChange(titulo: String) {
        _uiState.update { it.copy(novoTitulo = titulo) }
    }

    fun onNovaDescChange(desc: String) {
        _uiState.update { it.copy(novaDesc = desc) }
    }

    fun adicionarTrilha() {
        val state = _uiState.value
        if (state.novoTitulo.isNotBlank()) {
            viewModelScope.launch {
                trilhaRepository.inserir(
                    TrilhaConhecimento(
                        titulo = state.novoTitulo,
                        descricao = state.novaDesc.ifBlank { "Sem descrição" },
                        usoPratico = "Aplicação prática no cotidiano",
                        duracaoEstimadaMin = 30,
                        itemMascote = MascotItem.COMPASS
                    )
                )
                _uiState.update { it.copy(novoTitulo = "", novaDesc = "") }
            }
        }
    }

    fun deletarTrilha(trilha: TrilhaConhecimento) {
        viewModelScope.launch {
            trilhaRepository.deletar(trilha)
        }
    }

    fun carregarTrilhaDetalhe(trilhaId: Int) {
        viewModelScope.launch {
            val trilha = trilhaRepository.buscarPorId(trilhaId)
            _uiState.update { it.copy(trilhaAtual = trilha) }

            topicoRepository.buscarPorTrilha(trilhaId).collect { topicos ->
                _uiState.update { it.copy(topicosDaTrilhaAtual = topicos) }
            }
        }
    }
}

class TrilhasViewModelFactory(
    private val trilhaRepository: TrilhaRepository,
    private val topicoRepository: TopicoRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrilhasViewModel::class.java)) {
            return TrilhasViewModel(trilhaRepository, topicoRepository) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida")
    }
}
