package com.example.trajetoteu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trajetoteu.ui.theme.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Screen04Demographics(onNext: () -> Unit = {}) {
    val interests = listOf("Matemática", "Programação", "Idiomas", "História", "Ciências", "Análise de Dados", "Lógica")
    var selectedInterests by remember { mutableStateOf(setOf("Matemática", "Programação")) }
    var selectedAgeGroup by remember { mutableStateOf("18-24") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            ExplorerMascot(size = MascotSize.MD, emotion = MascotEmotion.THINKING, item = MascotItem.CALENDAR)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Sobre Você", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = OffWhite)

            Spacer(modifier = Modifier.height(16.dp))
            Text("Selecione sua faixa etária", fontSize = 14.sp, color = SoftGray)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(vertical = 8.dp)) {
                listOf("Menor de 18", "18-24", "25-34", "35+").forEach { age ->
                    FilterChip(
                        selected = age == selectedAgeGroup,
                        onClick = { selectedAgeGroup = age },
                        label = { Text(age) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Áreas de Interesse Principal", fontSize = 14.sp, color = SoftGray)
            Spacer(modifier = Modifier.height(8.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                interests.forEach { tag ->
                    val isSelected = selectedInterests.contains(tag)
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            selectedInterests = if (isSelected) selectedInterests - tag else selectedInterests + tag
                        },
                        label = { Text(tag) },
                        leadingIcon = if (isSelected) { { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp)) } } else null
                    )
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
