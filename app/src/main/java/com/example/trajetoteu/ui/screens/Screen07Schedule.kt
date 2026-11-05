package com.example.trajetoteu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trajetoteu.ui.theme.*

@Composable
fun Screen07Schedule(onNext: () -> Unit = {}) {
    var selectedTime by remember { mutableStateOf("15 min") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            ExplorerMascot(size = MascotSize.LG, emotion = MascotEmotion.ENCOURAGING, item = MascotItem.CALENDAR)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Meta Diária", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = OffWhite)
            Text("Defina seu ritmo de aprendizado para criar um hábito.", color = SoftGray, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(24.dp))

            listOf("5 min / dia" to "Ritmo Casual", "15 min / day" to "Hábito Regular", "30 min / day" to "Foco Intensivo").forEach { (time, desc) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { selectedTime = time }
                        .border(
                            width = 2.dp,
                            color = if (selectedTime == time) EmeraldGreen else Color.Transparent,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    colors = CardDefaults.cardColors(containerColor = SurfaceNavy)
                ) {
                    Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Text(time, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = OffWhite)
                            Text(desc, fontSize = 12.sp, color = SoftGray)
                        }
                        Icon(Icons.Default.Schedule, contentDescription = null, tint = WarmYellow)
                    }
                }
            }
        }

        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen)
        ) {
            Text("DEFINIR META E CONTINUAR", fontWeight = FontWeight.Bold)
        }
    }
}
