package com.example.trajetoteu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import com.example.trajetoteu.viewmodel.TopicosViewModel

@Composable
fun ScreenTopicosLista(
    viewModel: TopicosViewModel,
    onTopicoClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Gerenciar Tópicos e Perguntas", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = OffWhite)
        Spacer(modifier = Modifier.height(12.dp))

        // Formulário de Adição
        Card(
            colors = CardDefaults.cardColors(containerColor = SurfaceNavy),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Criar Novo Tópico com Pergunta", fontWeight = FontWeight.Bold, color = EmeraldGreen)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = uiState.novoTitulo,
                    onValueChange = { viewModel.onNovoTituloChange(it) },
                    label = { Text("Título do Tópico") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = uiState.novaPergunta,
                    onValueChange = { viewModel.onNovaPerguntaChange(it) },
                    label = { Text("Pergunta / Desafio") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = uiState.novaOpcaoCorreta,
                    onValueChange = { viewModel.onNovaOpcaoCorretaChange(it) },
                    label = { Text("Opção Correta (Resposta)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = uiState.novaOpcaoIncorreta,
                    onValueChange = { viewModel.onNovaOpcaoIncorretaChange(it) },
                    label = { Text("Opção Incorreta") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = uiState.novaDica,
                    onValueChange = { viewModel.onNovaDicaChange(it) },
                    label = { Text("Dica para o Aluno") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = { viewModel.adicionarTopico() },
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen)
                ) {
                    Text("Salvar Tópico e Pergunta")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Tópicos Cadastrados", fontWeight = FontWeight.Bold, color = OffWhite, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))

        // Lista de Tópicos
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            uiState.topicos.forEach { topico ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onTopicoClick(topico.id) },
                    colors = CardDefaults.cardColors(containerColor = SurfaceNavy)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = topico.concluido,
                            onCheckedChange = { viewModel.alternarConcluido(topico) }
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(topico.titulo, fontWeight = FontWeight.Bold, color = OffWhite)
                            Text(topico.perguntaDesafio, color = SoftGray, fontSize = 12.sp, maxLines = 1)
                            Text("Resposta: ${topico.opcaoCorreta}", color = EmeraldGreen, fontSize = 11.sp)
                        }
                        IconButton(onClick = { viewModel.deletarTopico(topico) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Excluir", tint = SoftGray)
                        }
                    }
                }
            }
        }
    }
}
