package com.example.trajetoteu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
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
import com.example.trajetoteu.viewmodel.TrilhasViewModel

@Composable
fun ScreenTrilhaDetalhe(
    trilhaId: Int,
    viewModel: TrilhasViewModel,
    onStartLesson: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(trilhaId) {
        viewModel.carregarTrilhaDetalhe(trilhaId)
    }

    val trilha = uiState.trilhaAtual
    val topicosDaTrilha = uiState.topicosDaTrilhaAtual

    if (trilha == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Trilha não encontrada no banco", color = OffWhite)
        }
        return
    }

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
