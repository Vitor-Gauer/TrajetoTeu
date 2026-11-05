package com.example.trajetoteu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.trajetoteu.data.repository.TopicoRepository
import com.example.trajetoteu.model.TopicoEstudo
import com.example.trajetoteu.ui.screens.FeedbackState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LessonUiState(
    val topico: TopicoEstudo = TopicoEstudo(
        id = 1,
        trilhaId = 1,
        titulo = "Problema do Agendamento",
        perguntaDesafio = "Qual a melhor abordagem para agendar tarefas com prazos simultâneos?",
        opcaoCorreta = "Priorizar a tarefa mais curta",
        opcaoIncorreta = "Executar em ordem aleatória",
        dica = "Dica: Tente analisar a duração de cada evento antes de ordenar."
    ),
    val feedbackState: FeedbackState = FeedbackState.NEUTRAL,
    val isMuted: Boolean = false,
    val isLoading: Boolean = false
)

class LessonViewModel(
    private val topicoRepository: TopicoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LessonUiState())
    val uiState: StateFlow<LessonUiState> = _uiState.asStateFlow()

    fun carregarTopico(topicoId: Int) {
        viewModelScope.launch {
            val fetched = topicoRepository.buscarPorId(topicoId)
            if (fetched != null) {
                _uiState.update { it.copy(topico = fetched, isLoading = false) }
            }
        }
    }

    fun selecionarOpcao(state: FeedbackState) {
        _uiState.update { it.copy(feedbackState = state) }
    }

    fun continuar() {
        _uiState.update { it.copy(feedbackState = FeedbackState.NEUTRAL) }
    }

    fun toggleMute() {
        _uiState.update { it.copy(isMuted = !it.isMuted) }
    }
}

class LessonViewModelFactory(
    private val topicoRepository: TopicoRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LessonViewModel::class.java)) {
            return LessonViewModel(topicoRepository) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida")
    }
}
