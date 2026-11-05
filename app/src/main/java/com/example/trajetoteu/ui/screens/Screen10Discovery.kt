package com.example.trajetoteu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trajetoteu.ui.theme.*

@Composable
fun Screen10Discovery(onSelectTrack: () -> Unit = {}) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Áreas Inscritas", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = OffWhite)
        }
        items(listOf("Ciência da Computação", "Fundamentos da Matemática")) { area ->
            Card(
                modifier = Modifier.fillMaxWidth().clickable { onSelectTrack() },
                colors = CardDefaults.cardColors(containerColor = SurfaceNavy)
            ) {
                ListItem(
                    headlineContent = { Text(area, fontWeight = FontWeight.Bold, color = OffWhite) },
                    supportingContent = { Text("Módulos práticos com desafios interativos.", color = SoftGray) },
                    leadingContent = { Icon(Icons.Default.Book, contentDescription = null, tint = EmeraldGreen) }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text("Recomendados Para Você", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = OffWhite)
        }
        items(listOf("Matemática do Dia a Dia", "Programação Python", "Lógica Algorítmica")) { rec ->
            Card(
                modifier = Modifier.fillMaxWidth().clickable { onSelectTrack() },
                colors = CardDefaults.cardColors(containerColor = SurfaceNavy)
            ) {
                ListItem(
                    headlineContent = { Text(rec, fontWeight = FontWeight.Bold, color = OffWhite) },
                    supportingContent = { Text("Aprenda conceitos com feedback imediato.", color = SoftGray) },
                    leadingContent = { Icon(Icons.Default.Explore, contentDescription = null, tint = WarmYellow) }
                )
            }
        }
    }
}
