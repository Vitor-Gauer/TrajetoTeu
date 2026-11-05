package com.example.trajetoteu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trajetoteu.ui.theme.*

@Composable
fun Screen09Dashboard(
    onNavigateToTrail: (Int) -> Unit = {},
    onNavigateDiscovery: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Trajeto Teu", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = EmeraldGreen)
            AssistChip(
                onClick = {},
                label = { Text("🔥 7 Dias Seguidos", color = WarmYellow, fontWeight = FontWeight.Bold) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        var pesquisa by remember { mutableStateOf("") }
        OutlinedTextField(
            value = pesquisa,
            onValueChange = { pesquisa = it },
            placeholder = { Text("O que você quer aprender hoje?") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = SoftGray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text("Trilhas Recomendadas", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = OffWhite)
        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(
                listOf(
                    Triple(1, "Algoritmos e Agendamento", "Otimize slots de tempo e execute tarefas."),
                    Triple(2, "Análise de Dados com Python", "Crie gráficos e interprete métricas."),
                    Triple(3, "Lógica de Busca", "Aprenda árvores de busca e ordenação.")
                )
            ) { (id, titulo, desc) ->
                Card(
                    modifier = Modifier
                        .width(220.dp)
                        .height(160.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceNavy)
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.SpaceBetween) {
                        Text(titulo, fontWeight = FontWeight.Bold, color = OffWhite, fontSize = 15.sp)
                        Text(desc, fontSize = 12.sp, color = SoftGray)
                        Button(
                            onClick = { onNavigateToTrail(id) },
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen)
                        ) {
                            Text("Iniciar Trilha", fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ExplorerMascot(
                size = MascotSize.LG,
                emotion = MascotEmotion.HAPPY,
                item = MascotItem.COMPASS
            )
        }
    }
}
