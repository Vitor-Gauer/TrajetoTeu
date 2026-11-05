package com.example.trajetoteu.data.repository

import com.example.trajetoteu.data.local.TrilhaDao
import com.example.trajetoteu.model.TrilhaConhecimento
import kotlinx.coroutines.flow.Flow

class TrilhaRepository(private val trilhaDao: TrilhaDao) {
    val todasTrilhas: Flow<List<TrilhaConhecimento>> = trilhaDao.buscarTodos()

    fun buscarTodos(): Flow<List<TrilhaConhecimento>> = trilhaDao.buscarTodos()

    suspend fun buscarPorId(id: Int): TrilhaConhecimento? {
        return trilhaDao.buscarPorId(id)
    }

    suspend fun inserir(trilha: TrilhaConhecimento): Long {
        return trilhaDao.inserir(trilha)
    }

    suspend fun atualizar(trilha: TrilhaConhecimento): Int {
        return trilhaDao.atualizar(trilha)
    }

    suspend fun deletar(trilha: TrilhaConhecimento): Int {
        return trilhaDao.deletar(trilha)
    }
}
