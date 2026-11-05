package com.example.trajetoteu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trajetoteu.ui.theme.*

@Composable
fun Screen06DiagnosticTest(onNext: () -> Unit = {}) {
    var selectedOption by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Teste Diagnóstico", color = EmeraldGreen, fontWeight = FontWeight.Bold)
                Text("Questão 1 de 3", color = SoftGray)
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(progress = { 0.33f }, modifier = Modifier.fillMaxWidth(), color = EmeraldGreen)

            Spacer(modifier = Modifier.height(24.dp))

            Text("Qual a complexidade de tempo para buscar um elemento em uma Árvore de Busca Binária balanceada?", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = OffWhite)

            Spacer(modifier = Modifier.height(16.dp))

            val options = listOf("O(1)", "O(log n)", "O(n)", "O(n²)")
            options.forEachIndexed { index, option ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { selectedOption = index }
                        .border(
                            width = 2.dp,
                            color = if (selectedOption == index) EmeraldGreen else Color.Transparent,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    colors = CardDefaults.cardColors(containerColor = SurfaceNavy)
                ) {
                    Text(option, modifier = Modifier.padding(14.dp), color = OffWhite, fontSize = 15.sp)
                }
            }
        }

        Button(
            onClick = onNext,
            enabled = selectedOption != null,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen)
        ) {
            Text("ENVIAR RESPOSTA", fontWeight = FontWeight.Bold)
        }
    }
}
