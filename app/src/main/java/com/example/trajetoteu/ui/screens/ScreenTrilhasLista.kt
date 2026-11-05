package com.example.trajetoteu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
fun ScreenTrilhasLista(
    viewModel: TrilhasViewModel,
    onTrilhaClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Gerenciar Trilhas", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = OffWhite)
        Spacer(modifier = Modifier.height(12.dp))

        // Formulário de Adição
        Card(
            colors = CardDefaults.cardColors(containerColor = SurfaceNavy),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Adicionar Nova Trilha", fontWeight = FontWeight.Bold, color = EmeraldGreen)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = uiState.novoTitulo,
                    onValueChange = { viewModel.onNovoTituloChange(it) },
                    label = { Text("Título da Trilha") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = uiState.novaDesc,
                    onValueChange = { viewModel.onNovaDescChange(it) },
                    label = { Text("Descrição Breve") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = { viewModel.adicionarTrilha() },
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Text("Adicionar Trilha")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Trilhas Cadastradas", fontWeight = FontWeight.Bold, color = OffWhite, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))

        // Lista
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            uiState.trilhas.forEach { trilha ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onTrilhaClick(trilha.id) },
                    colors = CardDefaults.cardColors(containerColor = SurfaceNavy)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(trilha.titulo, fontWeight = FontWeight.Bold, color = OffWhite, fontSize = 16.sp)
                            Text(trilha.descricao, color = SoftGray, fontSize = 12.sp)
                        }
                        IconButton(onClick = { viewModel.deletarTrilha(trilha) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Remover", tint = SoftGray)
                        }
                    }
                }
            }
        }
    }
}
