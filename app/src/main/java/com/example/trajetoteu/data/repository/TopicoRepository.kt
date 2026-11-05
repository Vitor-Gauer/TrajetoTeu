package com.example.trajetoteu.data.repository

import com.example.trajetoteu.data.local.TopicoDao
import com.example.trajetoteu.model.TopicoEstudo
import kotlinx.coroutines.flow.Flow

class TopicoRepository(private val topicoDao: TopicoDao) {
    val todosTopicos: Flow<List<TopicoEstudo>> = topicoDao.buscarTodos()

    fun buscarTodos(): Flow<List<TopicoEstudo>> = topicoDao.buscarTodos()

    suspend fun buscarPorId(id: Int): TopicoEstudo? {
        return topicoDao.buscarPorId(id)
    }

    fun buscarPorTrilha(trilhaId: Int): Flow<List<TopicoEstudo>> {
        return topicoDao.buscarPorTrilha(trilhaId)
    }

    suspend fun inserir(topico: TopicoEstudo): Long {
        return topicoDao.inserir(topico)
    }

    suspend fun atualizar(topico: TopicoEstudo): Int {
        return topicoDao.atualizar(topico)
    }

    suspend fun deletar(topico: TopicoEstudo): Int {
        return topicoDao.deletar(topico)
    }
}
