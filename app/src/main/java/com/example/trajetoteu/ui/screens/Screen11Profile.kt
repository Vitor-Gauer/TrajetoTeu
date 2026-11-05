package com.example.trajetoteu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trajetoteu.ui.theme.*

@Composable
fun Screen11Profile() {
    var timeFrame by remember { mutableStateOf("7 Dias") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier.size(80.dp).background(EmeraldGreen, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Face, contentDescription = null, modifier = Modifier.size(50.dp), tint = DeepNavy)
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text("Estudante Explorador", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = OffWhite)

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("7 Dias", "30 Dias", "365 Dias").forEach { frame ->
                FilterChip(
                    selected = timeFrame == frame,
                    onClick = { timeFrame = frame },
                    label = { Text(frame) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard(title = "Avaliações", value = "12", modifier = Modifier.weight(1f))
            StatCard(title = "Taxa de Acerto", value = "94%", modifier = Modifier.weight(1f))
            StatCard(title = "Resolvidos", value = "148", modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun StatCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = SurfaceNavy)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = EmeraldGreen)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = title, fontSize = 11.sp, color = SoftGray, textAlign = TextAlign.Center)
        }
    }
}
