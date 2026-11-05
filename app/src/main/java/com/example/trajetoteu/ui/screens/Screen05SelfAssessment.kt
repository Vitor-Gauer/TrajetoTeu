package com.example.trajetoteu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trajetoteu.ui.theme.*

@Composable
fun Screen05SelfAssessment(onNext: () -> Unit = {}) {
    var selectedLevel by remember { mutableStateOf("Iniciante") }

    val levels = listOf(
        "Iniciante" to "print(\"Olá Mundo\")",
        "Novato" to "for i in range(10):",
        "Intermediário" to "def calcular(x: Int): Boolean",
        "Avançado" to "class Node<T: Comparable<T>>"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            ExplorerMascot(size = MascotSize.MD, emotion = MascotEmotion.THINKING, item = MascotItem.SHIELD)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Autoavaliação de Nível", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = OffWhite)
            Text("Qual seu grau de familiaridade com lógica e código?", fontSize = 14.sp, color = SoftGray)

            Spacer(modifier = Modifier.height(16.dp))

            levels.forEach { (level, snippet) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { selectedLevel = level }
                        .border(
                            width = 2.dp,
                            color = if (selectedLevel == level) EmeraldGreen else Color.Transparent,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    colors = CardDefaults.cardColors(containerColor = SurfaceNavy)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text(level, fontWeight = FontWeight.Bold, color = OffWhite, fontSize = 15.sp)
                            if (selectedLevel == level) Icon(Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldGreen)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(CardBackground, RoundedCornerShape(6.dp))
                                .padding(8.dp)
                        ) {
                            Text(snippet, color = WarmYellow, fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen)
        ) {
            Text("FAZER TESTE DE DIAGNÓSTICO", fontWeight = FontWeight.Bold)
        }
    }
}
