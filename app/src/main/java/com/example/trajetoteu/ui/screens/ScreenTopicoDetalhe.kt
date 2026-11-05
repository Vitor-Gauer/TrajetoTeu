package com.example.trajetoteu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trajetoteu.ui.theme.*
import com.example.trajetoteu.viewmodel.TopicosViewModel

@Composable
fun ScreenTopicoDetalhe(
    topicoId: Int,
    viewModel: TopicosViewModel,
    onOpenLesson: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(topicoId) {
        viewModel.carregarTopicoDetalhe(topicoId)
    }

    val topico = uiState.topicoAtual
    val trilhaPai = uiState.trilhaPaiDoTopicoAtual

    if (topico == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Tópico não encontrado no banco", color = OffWhite)
        }
        return
    }

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
                    Text("Tópico: ${topico.titulo}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = EmeraldGreen)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Pertence à Trilha: ${trilhaPai?.titulo ?: "Sem Trilha"}", color = SoftGray, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Pergunta do Exercício:", fontWeight = FontWeight.Bold, color = OffWhite)
                    Text(topico.perguntaDesafio, color = OffWhite, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Opção Correta: ${topico.opcaoCorreta}", color = EmeraldGreen, fontSize = 13.sp)
                    Text("Opção Incorreta: ${topico.opcaoIncorreta}", color = SoftGray, fontSize = 13.sp)
                    Text("Dica: ${topico.dica}", color = WarmYellow, fontSize = 12.sp)
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
                        onClick = { viewModel.alternarConcluido(topico) },
                        colors = ButtonDefaults.buttonColors(containerColor = SurfaceNavy)
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldGreen)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (topico.concluido) "Marcar como Pendente" else "Marcar como Concluído")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onOpenLesson(topico.id) },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen)
        ) {
            Text("IR PARA EXPERIÊNCIA DE AULA (TELA 12)", fontWeight = FontWeight.Bold)
        }
    }
}
