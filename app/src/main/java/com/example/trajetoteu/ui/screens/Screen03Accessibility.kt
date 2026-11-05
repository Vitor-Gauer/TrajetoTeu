package com.example.trajetoteu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trajetoteu.ui.theme.*

@Composable
fun Screen03Accessibility(onNext: () -> Unit = {}) {
    var voiceEnabled by remember { mutableStateOf(true) }
    var adaptiveHints by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            ExplorerMascot(size = MascotSize.MD, emotion = MascotEmotion.THINKING, item = MascotItem.COMPASS)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Acessibilidade & Dicas Inteligentes", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = OffWhite)
            Text("Personalize como o Trajeto Teu orienta seu aprendizado.", color = SoftGray, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(24.dp))

            Card(colors = CardDefaults.cardColors(containerColor = SurfaceNavy), modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.RecordVoiceOver, contentDescription = null, tint = EmeraldGreen)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Narração por Voz", fontWeight = FontWeight.Bold, color = OffWhite)
                        Text("Explicação em áudio dos passos do exercício.", fontSize = 12.sp, color = SoftGray)
                    }
                    Switch(checked = voiceEnabled, onCheckedChange = { voiceEnabled = it })
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(colors = CardDefaults.cardColors(containerColor = SurfaceNavy), modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.TipsAndUpdates, contentDescription = null, tint = WarmYellow)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Assistência Adaptativa", fontWeight = FontWeight.Bold, color = OffWhite)
                        Text("Exibe dicas dinâmicas quando houver erros repetidos.", fontSize = 12.sp, color = SoftGray)
                    }
                    Switch(checked = adaptiveHints, onCheckedChange = { adaptiveHints = it })
                }
            }
        }

        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen)
        ) {
            Text("CONTINUAR", fontWeight = FontWeight.Bold)
        }
    }
}
