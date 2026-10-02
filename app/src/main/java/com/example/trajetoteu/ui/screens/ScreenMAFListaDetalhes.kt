package com.example.trajetoteu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trajetoteu.model.TopicoEstudo
import com.example.trajetoteu.model.TrilhaConhecimento
import com.example.trajetoteu.ui.theme.*

// ==========================================
// LISTA 1: TRILHAS DE CONHECIMENTO
// ==========================================
@Composable
fun ScreenTrilhasLista(
    trilhas: MutableList<TrilhaConhecimento>,
    onTrilhaClick: (String) -> Unit
) {
    var novoTitulo by remember { mutableStateOf("") }
    var novaDesc visual by remember { mutableStateOf("") }
    var novaUsoPratico by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(16.dp)
    ) {
        Text("Gerenciar Trilhas", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = OffWhite)
        Spacer(modifier = Modifier.height(12.dp))

        // Formulário de Adição
        Card(
            colors = CardDefaults.cardColors(containerColor = SurfaceNavy),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Adicionar Nova Trilha", fontWeight = FontWeight.Bold, color = EmeraldGreen)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = novoTitulo,
                    onValueChange = { novoTitulo = it },
                    label = { Text("Título da Trilha") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = novaDesc,
                    onValueChange = { novaDesc = it },
                    label = { Text("Descrição Breve") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        if (novoTitulo.isNotBlank()) {
                            trilhas.add(
                                TrilhaConhecimento(
                                    id = "t_${System.currentTimeMillis()}",
                                    titulo = novoTitulo,
                                    descricao = novaDesc.ifBlank { "Sem descrição" },
                                    usoPratico = "Aplicação geral",
                                    duracaoEstimadaMin = 30,
                                    itemMascote = MascotItem.COMPASS
                                )
                            )
                            novoTitulo = ""
                            novaDesc = ""
                        }
                    },
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Text("Adicionar")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(trilhas, key = { it.id }) { trilha ->
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
                        IconButton(onClick = { trilhas.remove(trilha) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Remover", tint = SoftGray)
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// DETALHE 1: DETALHE DA TRILHA (LÓGICA EXTRA)
// ==========================================
@Composable
fun ScreenTrilhaDetalhe(
    trilhaId: String?,
    trilhas: List<TrilhaConhecimento>,
    topicos: List<TopicoEstudo>,
    onStartLesson: (String) -> Unit
) {
    val trilha = trilhas.find { it.id == trilhaId } ?: trilhas.firstOrNull()

    if (trilha == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Trilha não encontrada", color = OffWhite)
        }
        return
    }

    // LÓGICA EXTRA EXIGIDA PELA SEÇÃO 3.2: Integração de dados + Cálculos em tempo real
    val topicosDaTrilha = topicos.filter { it.trilhaId == trilha.id }
    val concluidos = topicosDaTrilha.count { it.concluido }
    val porcentagem = if (topicosDaTrilha.isNotEmpty()) (concluidos * 100) / topicosDaTrilha.size else 0
    val tempoRestante = (topicosDaTrilha.size - concluidos) * 15

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(16.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = SurfaceNavy),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(trilha.titulo, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = EmeraldGreen)
                Spacer(modifier = Modifier.height(4.dp))
                Text(trilha.descricao, color = OffWhite, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Uso Prático: ${trilha.usoPratico}", color = WarmYellow, fontSize = 12.sp)
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

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(topicosDaTrilha) { topico ->
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
// LISTA 2: TÓPICOS DE ESTUDO
// ==========================================
@Composable
fun ScreenTopicosLista(
    topicos: MutableList<TopicoEstudo>,
    trilhas: List<TrilhaConhecimento>,
    onTopicoClick: (String) -> Unit
) {
    var novoTitulo by remember { mutableStateOf("") }
    var novaPergunta by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(16.dp)
    ) {
        Text("Gerenciar Tópicos de Estudo", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = OffWhite)
        Spacer(modifier = Modifier.height(12.dp))

        // Form Adição
        Card(
            colors = CardDefaults.cardColors(containerColor = SurfaceNavy),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Adicionar Tópico Exemplo", fontWeight = FontWeight.Bold, color = EmeraldGreen)
                OutlinedTextField(
                    value = novoTitulo,
                    onValueChange = { novoTitulo = it },
                    label = { Text("Título do Tópico") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = novaPergunta,
                    onValueChange = { novaPergunta = it },
                    label = { Text("Pergunta Desafio") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        if (novoTitulo.isNotBlank()) {
                            val trilhaPadrao = trilhas.firstOrNull()?.id ?: "t1"
                            topicos.add(
                                TopicoEstudo(
                                    id = "top_${System.currentTimeMillis()}",
                                    trilhaId = trilhaPadrao,
                                    titulo = novoTitulo,
                                    perguntaDesafio = novaPergunta.ifBlank { "Pergunta Genérica?" },
                                    opcaoCorreta = "Opção A",
                                    opcaoIncorreta = "Opção B",
                                    dica = "Dica genérica."
                                )
                            )
                            novoTitulo = ""
                            novaPergunta = ""
                        }
                    },
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen)
                ) {
                    Text("Criar Tópico")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(topicos, key = { it.id }) { topico ->
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
                                val index = topicos.indexOf(topico)
                                if (index != -1) {
                                    topicos[index] = topico.copy(concluido = checado)
                                }
                            }
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(topico.titulo, fontWeight = FontWeight.Bold, color = OffWhite)
                            Text(topico.perguntaDesafio, color = SoftGray, fontSize = 12.sp)
                        }
                        IconButton(onClick = { topicos.remove(topico) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Excluir", tint = SoftGray)
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// DETALHE 2: DETALHE DO TÓPICO (EDIÇÃO/AÇÃO)
// ==========================================
@Composable
fun ScreenTopicoDetalhe(
    topicoId: String?,
    topicos: MutableList<TopicoEstudo>,
    trilhas: List<TrilhaConhecimento>,
    onOpenLesson: (String) -> Unit
) {
    val topico = topicos.find { it.id == topicoId } ?: topicos.firstOrNull()

    if (topico == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Tópico não encontrado", color = OffWhite)
        }
        return
    }

    val trilhaPai = trilhas.find { it.id == topico.trilhaId }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceNavy),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Tópico: ${topico.titulo}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = EmeraldGreen)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Pertence à Trilha: ${trilhaPai?.titulo ?: "Sem Trilha"}", color = SoftGray, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Pergunta do Exercício:", fontWeight = FontWeight.Bold, color = OffWhite)
                    Text(topico.perguntaDesafio, color = OffWhite, fontSize = 14.sp)
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
                            text = if (topico.concluido) "Concluído" else "Pendente",
                            color = if (topico.concluido) EmeraldGreen else WarmYellow,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            val index = topicos.indexOf(topico)
                            if (index != -1) {
                                topicos[index] = topico.copy(concluido = !topico.concluido)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SurfaceNavy)
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldGreen)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (topico.concluido) "Marcar como Pendente" else "Marcar como Concluído")
                    }
                }
            }
        }

        Button(
            onClick = { onOpenLesson(topico.id) },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen)
        ) {
            Text("IR PARA EXPERIÊNCIA DE AULA (TELA 12)", fontWeight = FontWeight.Bold)
        }
    }
}