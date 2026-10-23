package com.example.trajetoteu.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trajetoteu.data.local.AppDatabase
import com.example.trajetoteu.model.TopicoEstudo
import com.example.trajetoteu.model.TrilhaConhecimento
import com.example.trajetoteu.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// ==========================================
// LISTA 1: TRILHAS DE CONHECIMENTO (ROOM PERSISTED)
// ==========================================
@Composable
fun ScreenTrilhasLista(
    onTrilhaClick: (Int) -> Unit
) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val coroutineScope = rememberCoroutineScope()

    var trilhas by remember { mutableStateOf<List<TrilhaConhecimento>>(emptyList()) }
    var novoTitulo by remember { mutableStateOf("") }
    var novaDesc by remember { mutableStateOf("") }

    // Carrega do banco ao abrir (LaunchedEffect)
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            val fetched = db.trilhaDao().buscarTodos()
            withContext(Dispatchers.Main) {
                trilhas = fetched
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Gerenciar Trilhas (Room)", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = OffWhite)
        Spacer(modifier = Modifier.height(12.dp))

        // Formulário de Adição
        Card(
            colors = CardDefaults.cardColors(containerColor = SurfaceNavy),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Adicionar Nova Trilha", fontWeight = FontWeight.Bold, color = EmeraldGreen)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = novoTitulo,
                    onValueChange = { novoTitulo = it },
                    label = { Text("Título da Trilha") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = novaDesc,
                    onValueChange = { novaDesc = it },
                    label = { Text("Descrição Breve") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        if (novoTitulo.isNotBlank()) {
                            coroutineScope.launch(Dispatchers.IO) {
                                try {
                                    db.trilhaDao().inserir(
                                        TrilhaConhecimento(
                                            titulo = novoTitulo,
                                            descricao = novaDesc.ifBlank { "Sem descrição" },
                                            usoPratico = "Aplicação prática no cotidiano",
                                            duracaoEstimadaMin = 30,
                                            itemMascote = MascotItem.COMPASS
                                        )
                                    )
                                    val updated = db.trilhaDao().buscarTodos()
                                    withContext(Dispatchers.Main) {
                                        trilhas = updated
                                        novoTitulo = ""
                                        novaDesc = ""
                                    }
                                } catch (e: Exception) {
                                    android.util.Log.e("TrilhasLista", "Erro ao inserir trilha", e)
                                }
                            }
                        }
                    },
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Text("Adicionar Trilha")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Trilhas Cadastradas", fontWeight = FontWeight.Bold, color = OffWhite, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))

        // Lista
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            trilhas.forEach { trilha ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onTrilhaClick(trilha.id) },
                    colors = CardDefaults.cardColors(containerColor = SurfaceNavy)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(trilha.titulo, fontWeight = FontWeight.Bold, color = OffWhite, fontSize = 16.sp)
                            Text(trilha.descricao, color = SoftGray, fontSize = 12.sp)
                        }
                        IconButton(onClick = {
                            coroutineScope.launch(Dispatchers.IO) {
                                try {
                                    db.trilhaDao().deletar(trilha)
                                    val updated = db.trilhaDao().buscarTodos()
                                    withContext(Dispatchers.Main) {
                                        trilhas = updated
                                    }
                                } catch (e: Exception) {
                                    android.util.Log.e("TrilhasLista", "Erro ao deletar trilha", e)
                                }
                            }
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Remover", tint = SoftGray)
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// DETALHE 1: DETALHE DA TRILHA (LENDO DIRETO DO BANCO - SEÇÃO 3.3)
// ==========================================
@Composable
fun ScreenTrilhaDetalhe(
    trilhaId: Int,
    onStartLesson: (Int) -> Unit
) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }

    var trilha by remember { mutableStateOf<TrilhaConhecimento?>(null) }
    var topicosDaTrilha by remember { mutableStateOf<List<TopicoEstudo>>(emptyList()) }

    LaunchedEffect(trilhaId) {
        withContext(Dispatchers.IO) {
            val t = db.trilhaDao().buscarPorId(trilhaId)
            val tops = db.topicoDao().buscarPorTrilha(trilhaId)
            withContext(Dispatchers.Main) {
                trilha = t
                topicosDaTrilha = tops
            }
        }
    }

    if (trilha == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Trilha não encontrada no banco", color = OffWhite)
        }
        return
    }

    val t = trilha!!
    val concluidos = topicosDaTrilha.count { it.concluido }
    val porcentagem = if (topicosDaTrilha.isNotEmpty()) (concluidos * 100) / topicosDaTrilha.size else 0
    val tempoRestante = (topicosDaTrilha.size - concluidos) * 15

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = SurfaceNavy),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(t.titulo, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = EmeraldGreen)
                Spacer(modifier = Modifier.height(4.dp))
                Text(t.descricao, color = OffWhite, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Uso Prático: ${t.usoPratico}", color = WarmYellow, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Card com Cálculo Dinâmico Extra
        Card(
            colors = CardDefaults.cardColors(containerColor = CardBackground),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Métricas de Desempenho (Tempo Real)", fontWeight = FontWeight.Bold, color = OffWhite)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Progresso da Trilha: $porcentagem%", color = EmeraldGreen, fontWeight = FontWeight.Bold)
                Text("Tópicos Concluídos: $concluidos / ${topicosDaTrilha.size}", color = SoftGray, fontSize = 12.sp)
                Text("Tempo Estimado para Conclusão: $tempoRestante min", color = WarmYellow, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Tópicos Vinculados", fontWeight = FontWeight.Bold, color = OffWhite, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            topicosDaTrilha.forEach { topico ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = SurfaceNavy)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(topico.titulo, color = OffWhite, fontWeight = FontWeight.SemiBold)
                            Text(topico.perguntaDesafio, color = SoftGray, fontSize = 12.sp, maxLines = 1)
                        }
                        Button(
                            onClick = { onStartLesson(topico.id) },
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen)
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null)
                            Text("Iniciar")
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// LISTA 2: TÓPICOS E PERGUNTAS (ROOM PERSISTED)
// ==========================================
@Composable
fun ScreenTopicosLista(
    onTopicoClick: (Int) -> Unit
) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val coroutineScope = rememberCoroutineScope()

    var topicos by remember { mutableStateOf<List<TopicoEstudo>>(emptyList()) }
    var trilhas by remember { mutableStateOf<List<TrilhaConhecimento>>(emptyList()) }

    var novoTitulo by remember { mutableStateOf("") }
    var novaPergunta by remember { mutableStateOf("") }
    var novaOpcaoCorreta by remember { mutableStateOf("") }
    var novaOpcaoIncorreta by remember { mutableStateOf("") }
    var novaDica by remember { mutableStateOf("") }
    var selectedTrilhaId by remember { mutableStateOf(1) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            val fetchedTops = db.topicoDao().buscarTodos()
            val fetchedTrils = db.trilhaDao().buscarTodos()
            withContext(Dispatchers.Main) {
                topicos = fetchedTops
                trilhas = fetchedTrils
                if (fetchedTrils.isNotEmpty()) {
                    selectedTrilhaId = fetchedTrils.first().id
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Gerenciar Tópicos e Perguntas (Room)", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = OffWhite)
        Spacer(modifier = Modifier.height(12.dp))

        // Form Adição Completo (com Título, Pergunta, Opção Correta, Opção Incorreta e Dica)
        Card(
            colors = CardDefaults.cardColors(containerColor = SurfaceNavy),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Criar Novo Tópico com Pergunta", fontWeight = FontWeight.Bold, color = EmeraldGreen)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = novoTitulo,
                    onValueChange = { novoTitulo = it },
                    label = { Text("Título do Tópico") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = novaPergunta,
                    onValueChange = { novaPergunta = it },
                    label = { Text("Pergunta / Desafio") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = novaOpcaoCorreta,
                    onValueChange = { novaOpcaoCorreta = it },
                    label = { Text("Opção Correta (Resposta)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = novaOpcaoIncorreta,
                    onValueChange = { novaOpcaoIncorreta = it },
                    label = { Text("Opção Incorreta") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = novaDica,
                    onValueChange = { novaDica = it },
                    label = { Text("Dica para o Aluno") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        if (novoTitulo.isNotBlank() && novaPergunta.isNotBlank()) {
                            coroutineScope.launch(Dispatchers.IO) {
                                try {
                                    db.topicoDao().inserir(
                                        TopicoEstudo(
                                            trilhaId = selectedTrilhaId,
                                            titulo = novoTitulo,
                                            perguntaDesafio = novaPergunta,
                                            opcaoCorreta = novaOpcaoCorreta.ifBlank { "Correta" },
                                            opcaoIncorreta = novaOpcaoIncorreta.ifBlank { "Incorreta" },
                                            dica = novaDica.ifBlank { "Analise bem o enunciado." }
                                        )
                                    )
                                    val updated = db.topicoDao().buscarTodos()
                                    withContext(Dispatchers.Main) {
                                        topicos = updated
                                        novoTitulo = ""
                                        novaPergunta = ""
                                        novaOpcaoCorreta = ""
                                        novaOpcaoIncorreta = ""
                                        novaDica = ""
                                    }
                                } catch (e: Exception) {
                                    android.util.Log.e("TopicosLista", "Erro ao inserir tópico", e)
                                }
                            }
                        }
                    },
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen)
                ) {
                    Text("Salvar Tópico e Pergunta")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Tópicos Cadastrados", fontWeight = FontWeight.Bold, color = OffWhite, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))

        // Lista de Tópicos
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            topicos.forEach { topico ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onTopicoClick(topico.id) },
                    colors = CardDefaults.cardColors(containerColor = SurfaceNavy)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = topico.concluido,
                            onCheckedChange = { checado ->
                                coroutineScope.launch(Dispatchers.IO) {
                                    try {
                                        db.topicoDao().atualizar(topico.copy(concluido = checado))
                                        val updated = db.topicoDao().buscarTodos()
                                        withContext(Dispatchers.Main) {
                                            topicos = updated
                                        }
                                    } catch (e: Exception) {
                                        android.util.Log.e("TopicosLista", "Erro ao atualizar", e)
                                    }
                                }
                            }
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(topico.titulo, fontWeight = FontWeight.Bold, color = OffWhite)
                            Text(topico.perguntaDesafio, color = SoftGray, fontSize = 12.sp, maxLines = 1)
                            Text("Resposta: ${topico.opcaoCorreta}", color = EmeraldGreen, fontSize = 11.sp)
                        }
                        IconButton(onClick = {
                            coroutineScope.launch(Dispatchers.IO) {
                                try {
                                    db.topicoDao().deletar(topico)
                                    val updated = db.topicoDao().buscarTodos()
                                    withContext(Dispatchers.Main) {
                                        topicos = updated
                                    }
                                } catch (e: Exception) {
                                    android.util.Log.e("TopicosLista", "Erro ao deletar", e)
                                }
                            }
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Excluir", tint = SoftGray)
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// DETALHE 2: DETALHE DO TÓPICO (LENDO DO BANCO - SEÇÃO 3.3)
// ==========================================
@Composable
fun ScreenTopicoDetalhe(
    topicoId: Int,
    onOpenLesson: (Int) -> Unit
) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val coroutineScope = rememberCoroutineScope()

    var topico by remember { mutableStateOf<TopicoEstudo?>(null) }
    var trilhaPai by remember { mutableStateOf<TrilhaConhecimento?>(null) }

    LaunchedEffect(topicoId) {
        withContext(Dispatchers.IO) {
            val t = db.topicoDao().buscarPorId(topicoId)
            val tr = t?.let { db.trilhaDao().buscarPorId(it.trilhaId) }
            withContext(Dispatchers.Main) {
                topico = t
                trilhaPai = tr
            }
        }
    }

    if (topico == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Tópico não encontrado no banco", color = OffWhite)
        }
        return
    }

    val t = topico!!

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceNavy),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Tópico: ${t.titulo}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = EmeraldGreen)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Pertence à Trilha: ${trilhaPai?.titulo ?: "Sem Trilha"}", color = SoftGray, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Pergunta do Exercício:", fontWeight = FontWeight.Bold, color = OffWhite)
                    Text(t.perguntaDesafio, color = OffWhite, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Opção Correta: ${t.opcaoCorreta}", color = EmeraldGreen, fontSize = 13.sp)
                    Text("Opção Incorreta: ${t.opcaoIncorreta}", color = SoftGray, fontSize = 13.sp)
                    Text("Dica: ${t.dica}", color = WarmYellow, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Ação Interativa de Edição
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceNavy),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Ações e Estado do Tópico", fontWeight = FontWeight.Bold, color = OffWhite)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Status: ", color = SoftGray)
                        Text(
                            text = if (t.concluido) "Concluído" else "Pendente",
                            color = if (t.concluido) EmeraldGreen else WarmYellow,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            coroutineScope.launch(Dispatchers.IO) {
                                val updated = t.copy(concluido = !t.concluido)
                                db.topicoDao().atualizar(updated)
                                val refreshed = db.topicoDao().buscarPorId(topicoId)
                                withContext(Dispatchers.Main) {
                                    topico = refreshed
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SurfaceNavy)
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldGreen)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (t.concluido) "Marcar como Pendente" else "Marcar como Concluído")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onOpenLesson(t.id) },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen)
        ) {
            Text("IR PARA EXPERIÊNCIA DE AULA (TELA 12)", fontWeight = FontWeight.Bold)
        }
    }
}
