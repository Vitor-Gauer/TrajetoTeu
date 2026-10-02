package com.example.trajetoteu.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trajetoteu.ui.theme.*

@Composable
fun Screen01UserRole(onNext: () -> Unit = {}) {
    var selectedRole by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ExplorerMascot(
            size = MascotSize.MD,
            emotion = MascotEmotion.HAPPY,
            item = MascotItem.NONE
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Qual é o seu objetivo?",
            color = EmeraldGreen,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Selecione uma opção para começar a aprender ou ensinar na plataforma.",
            color = SoftGray,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        RoleCard(
            title = "Quero Aprender",
            description = "Acesse trilhas de conhecimento interativas.",
            icon = Icons.Default.School,
            isSelected = selectedRole == "Aprender",
            onClick = { selectedRole = "Aprender" }
        )

        Spacer(modifier = Modifier.height(16.dp))

        RoleCard(
            title = "Quero Ensinar",
            description = "Crie conteúdos e ajude outros estudantes.",
            icon = Icons.Default.Psychology,
            isSelected = selectedRole == "Ensinar",
            onClick = { selectedRole = "Ensinar" }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onNext,
            enabled = selectedRole != null,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen)
        ) {
            Text("CONTINUAR", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun RoleCard(title: String, description: String, icon: ImageVector, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .border(
                width = 2.dp,
                color = if (isSelected) EmeraldGreen else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = SurfaceNavy)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = EmeraldGreen, modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = OffWhite)
                Spacer(modifier = Modifier.height(4.dp))
                Text(description, fontSize = 13.sp, color = SoftGray)
            }
        }
    }
}

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

@Composable
fun Screen08Auth(onAuthSuccess: () -> Unit = {}) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = SurfaceNavy),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Criar Conta", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = OffWhite)

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("E-mail") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = EmeraldGreen,
                        unfocusedBorderColor = SoftGray,
                        focusedLabelColor = EmeraldGreen,
                        unfocusedLabelColor = SoftGray,
                        focusedTextColor = OffWhite,
                        unfocusedTextColor = OffWhite
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Senha") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = EmeraldGreen,
                        unfocusedBorderColor = SoftGray,
                        focusedLabelColor = EmeraldGreen,
                        unfocusedLabelColor = SoftGray,
                        focusedTextColor = OffWhite,
                        unfocusedTextColor = OffWhite
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (email.isNotEmpty() && password.isNotEmpty()) {
                            Toast.makeText(context, "Bem-vindo ao Trajeto Teu!", Toast.LENGTH_SHORT).show()
                            onAuthSuccess()
                        } else {
                            Toast.makeText(context, "Preencha e-mail e senha!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen)
                ) {
                    Text("CADASTRAR E COMEÇAR", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun Screen09Dashboard(
    onNavigateToTrail: (String) -> Unit = {},
    onNavigateDiscovery: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Trajeto Teu", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = EmeraldGreen)
            AssistChip(
                onClick = {},
                label = { Text("🔥 7 Dias Seguidos", color = WarmYellow, fontWeight = FontWeight.Bold) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        var pesquisa by remember { mutableStateOf("") }
        OutlinedTextField(
            value = pesquisa,
            onValueChange = { pesquisa = it },
            placeholder = { Text("O que você quer aprender hoje?") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = SoftGray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text("Trilhas Recomendadas", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = OffWhite)
        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(
                listOf(
                    Triple("t1", "Algoritmos e Agendamento", "Otimize slots de tempo e execute tarefas."),
                    Triple("t2", "Análise de Dados com Python", "Crie gráficos e interprete métricas."),
                    Triple("t3", "Lógica de Busca", "Aprenda árvores de busca e ordenação.")
                )
            ) { (id, titulo, desc) ->
                Card(
                    modifier = Modifier
                        .width(220.dp)
                        .height(160.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceNavy)
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.SpaceBetween) {
                        Text(titulo, fontWeight = FontWeight.Bold, color = OffWhite, fontSize = 15.sp)
                        Text(desc, fontSize = 12.sp, color = SoftGray)
                        Button(
                            onClick = { onNavigateToTrail(id) },
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen)
                        ) {
                            Text("Iniciar Trilha", fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ExplorerMascot(
                size = MascotSize.LG,
                emotion = MascotEmotion.HAPPY,
                item = MascotItem.COMPASS
            )
        }
    }
}

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