package com.example.trajetoteu.data.local

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.trajetoteu.model.TopicoEstudo
import com.example.trajetoteu.model.TrilhaConhecimento
import com.example.trajetoteu.ui.screens.MascotItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MascotItemTypeConverter {
    @TypeConverter
    fun fromMascotItem(item: MascotItem): String = item.name

    @TypeConverter
    fun toMascotItem(name: String): MascotItem {
        return try {
            MascotItem.valueOf(name)
        } catch (e: Exception) {
            MascotItem.COMPASS
        }
    }
}

@Dao
interface TrilhaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(trilha: TrilhaConhecimento): Long

    @Query("SELECT * FROM trilhas")
    suspend fun buscarTodos(): List<TrilhaConhecimento>

    @Query("SELECT * FROM trilhas WHERE id = :id")
    suspend fun buscarPorId(id: Int): TrilhaConhecimento?

    @Update
    suspend fun atualizar(trilha: TrilhaConhecimento): Int

    @Delete
    suspend fun deletar(trilha: TrilhaConhecimento): Int
}

@Dao
interface TopicoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(topico: TopicoEstudo): Long

    @Query("SELECT * FROM topicos")
    suspend fun buscarTodos(): List<TopicoEstudo>

    @Query("SELECT * FROM topicos WHERE id = :id")
    suspend fun buscarPorId(id: Int): TopicoEstudo?

    @Query("SELECT * FROM topicos WHERE trilhaId = :trilhaId")
    suspend fun buscarPorTrilha(trilhaId: Int): List<TopicoEstudo>

    @Update
    suspend fun atualizar(topico: TopicoEstudo): Int

    @Delete
    suspend fun deletar(topico: TopicoEstudo): Int
}

@Database(entities = [TrilhaConhecimento::class, TopicoEstudo::class], version = 1, exportSchema = false)
@TypeConverters(MascotItemTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trilhaDao(): TrilhaDao
    abstract fun topicoDao(): TopicoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "trajeto_teu_database"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                INSTANCE?.let { database ->
                                    val tDao = database.trilhaDao()
                                    val topDao = database.topicoDao()

                                    val t1Id = tDao.inserir(TrilhaConhecimento(id = 1, titulo = "Algoritmos e Agendamento", descricao = "Aprenda a otimizar slots e priorizar tarefas.", usoPratico = "Otimização de tarefas operacionais.", duracaoEstimadaMin = 45, itemMascote = MascotItem.CALENDAR)).toInt()
                                    val t2Id = tDao.inserir(TrilhaConhecimento(id = 2, titulo = "Análise de Dados com Python", descricao = "Entenda gráficos, médias e distribuições.", usoPratico = "Tomada de decisão orientada a dados.", duracaoEstimadaMin = 60, itemMascote = MascotItem.PIE)).toInt()
                                    val t3Id = tDao.inserir(TrilhaConhecimento(id = 3, titulo = "Lógica de Busca", descricao = "Técnicas eficientes de busca e indexação.", usoPratico = "Aceleramento de consultas em banco.", duracaoEstimadaMin = 30, itemMascote = MascotItem.MAGNIFIER)).toInt()

                                    topDao.inserir(TopicoEstudo(id = 1, trilhaId = t1Id, titulo = "Problema do Agendamento", perguntaDesafio = "Qual a melhor abordagem para tarefas com prazos simultâneos?", opcaoCorreta = "Priorizar a tarefa mais curta", opcaoIncorreta = "Executar em ordem aleatória", dica = "Ordene pela menor duração primeiro."))
                                    topDao.inserir(TopicoEstudo(id = 2, trilhaId = t1Id, titulo = "Prioridade Dinâmica", perguntaDesafio = "Como evitar inanição (starvation) em filas?", opcaoCorreta = "Aumentar prioridade com o tempo", opcaoIncorreta = "Ignorar tarefas antigas", dica = "Aumentar gradualmente a prioridade dos processos parados."))
                                    topDao.inserir(TopicoEstudo(id = 3, trilhaId = t2Id, titulo = "Gráficos de Setores", perguntaDesafio = "Quando utilizar um gráfico de pizza?", opcaoCorreta = "Para comparar partes de um todo de 100%", opcaoIncorreta = "Para séries temporais longas", dica = "Ideal para porcentagens relativas."))
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
