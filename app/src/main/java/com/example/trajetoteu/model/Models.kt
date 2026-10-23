package com.example.trajetoteu.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.trajetoteu.ui.screens.MascotItem

@Entity(tableName = "trilhas")
data class TrilhaConhecimento(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String,
    val descricao: String,
    val usoPratico: String,
    val duracaoEstimadaMin: Int,
    val itemMascote: MascotItem,
    val concluida: Boolean = false
)

@Entity(tableName = "topicos")
data class TopicoEstudo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val trilhaId: Int,
    val titulo: String,
    val perguntaDesafio: String,
    val opcaoCorreta: String,
    val opcaoIncorreta: String,
    val dica: String,
    val concluido: Boolean = false
)
