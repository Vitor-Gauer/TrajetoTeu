package com.example.trajetoteu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.Lightbulb
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
fun Screen02Motivation(onNext: () -> Unit = {}) {
    val goals = listOf(
        "Destacar-se nos estudos" to Icons.Default.AutoAwesome,
        "Crescimento profissional" to Icons.AutoMirrored.Filled.TrendingUp,
        "Manter a mente ativa" to Icons.Default.Lightbulb,
        "Ajudar meu filho a aprender" to Icons.Default.FamilyRestroom
    )
    var selectedGoal by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            ExplorerMascot(size = MascotSize.MD, emotion = MascotEmotion.THINKING, item = MascotItem.NONE)
            Spacer(modifier = Modifier.height(16.dp))
            Text("O que te motiva a aprender?", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = OffWhite)
            Spacer(modifier = Modifier.height(20.dp))

            goals.forEach { (goal, icon) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { selectedGoal = goal }
                        .border(
                            width = 2.dp,
                            color = if (selectedGoal == goal) EmeraldGreen else Color.Transparent,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    colors = CardDefaults.cardColors(containerColor = SurfaceNavy)
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(icon, contentDescription = null, tint = WarmYellow, modifier = Modifier.size(28.dp))
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(goal, fontSize = 15.sp, fontWeight = FontWeight.Medium, color = OffWhite)
                    }
                }
            }
        }

        Button(
            onClick = onNext,
            enabled = selectedGoal != null,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen)
        ) {
            Text("AVANÇAR", fontWeight = FontWeight.Bold)
        }
    }
}
