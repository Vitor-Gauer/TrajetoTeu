package com.example.trajetoteu.model

import com.example.trajetoteu.ui.screens.MascotItem

data class TrilhaConhecimento(
    val id: String,
    val titulo: String,
    val descricao: String,
    val usoPratico: String,
    val duracaoEstimadaMin: Int,
    val itemMascote: MascotItem,
    var concluida: Boolean = false
)

data class TopicoEstudo(
    val id: String,
    val trilhaId: String,
    val titulo: String,
    val perguntaDesafio: String,
    val opcaoCorreta: String,
    val opcaoIncorreta: String,
    val dica: String,
    var concluido: Boolean = false
)