package com.example.trajetoteu.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen01UserRole() {
    val context = LocalContext.current
    var selectedRole by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Trajeto Teu", fontWeight = FontWeight.Bold, color = OffWhite) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DeepNavy)
            )
        },
        containerColor = DeepNavy
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ExplorerMascot(
                size = MascotSize.MD,
                emotion = MascotEmotion.HAPPY,
                item = MascotItem.NONE
            )
            Text(
                text = "Qual é o seu objetivo?",
                color = EmeraldGreen,
                fontSize = 28.sp,
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
                description = "Acesse trilhas de conhecimento.",
                icon = Icons.Default.School,
                isSelected = selectedRole == "Aprender",
                onClick = {
                    selectedRole = "Aprender"
                    Toast.makeText(context, "Perfil Aprender selecionado!", Toast.LENGTH_SHORT).show()
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            RoleCard(
                title = "Quero Ensinar",
                description = "Crie trilhas e ajude outros.",
                icon = Icons.Default.Psychology,
                isSelected = selectedRole == "Ensinar",
                onClick = {
                    selectedRole = "Ensinar"
                    Toast.makeText(context, "Perfil Ensinar selecionado!", Toast.LENGTH_SHORT).show()
                }
            )
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
                Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = OffWhite)
                Spacer(modifier = Modifier.height(4.dp))
                Text(description, fontSize = 14.sp, color = SoftGray)
            }
        }
    }
}

@Composable
fun Screen02Motivation() {
    val goals = listOf(
        "Excelling in school" to Icons.Default.AutoAwesome,
        "Professional growth" to Icons.AutoMirrored.Filled.TrendingUp,
        "Staying sharp" to Icons.Default.Lightbulb,
        "Helping my child learn" to Icons.Default.FamilyRestroom
    )
    var selectedGoal by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        ExplorerMascot(
            size = MascotSize.MD,
            emotion = MascotEmotion.THINKING,
            item = MascotItem.NONE
        )
        Text("What motivates you to learn?", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = OffWhite, modifier= Modifier.padding(
            0.dp,40.dp,0.dp,0.dp
        ))
        Spacer(modifier = Modifier.height(24.dp))

        goals.forEach { (goal, icon) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { selectedGoal = goal }
                    .border(
                        width = 2.dp,
                        color = if (selectedGoal == goal) EmeraldGreen else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    ),
                colors = CardDefaults.cardColors(containerColor = SurfaceNavy)
            ) {
                Row(modifier = Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(icon, contentDescription = null, tint = WarmYellow, modifier = Modifier.size(28.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(goal, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = OffWhite)
                }
            }
        }
    }
}

@Composable
fun Screen03Accessibility() {
    var voiceEnabled by remember { mutableStateOf(true) }
    var adaptiveHints by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        ExplorerMascot(
            size = MascotSize.MD,
            emotion = MascotEmotion.THINKING,
            item = MascotItem.COMPASS
        )
        Text("Accessibility & Smart Hints", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = OffWhite, modifier= Modifier.padding(
            0.dp,40.dp,0.dp,0.dp
        ))
        Text("Customize how Trajeto Teu guides your learning journey.", color = SoftGray, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(32.dp))

        Card(colors = CardDefaults.cardColors(containerColor = SurfaceNavy), modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.RecordVoiceOver, contentDescription = null, tint = EmeraldGreen)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Voice Narration", fontWeight = FontWeight.Bold, color = OffWhite)
                        Text("Audio explanation for problem steps", fontSize = 12.sp, color = SoftGray)
                    }
                    Switch(checked = voiceEnabled, onCheckedChange = { voiceEnabled = it })
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(colors = CardDefaults.cardColors(containerColor = SurfaceNavy), modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.TipsAndUpdates, contentDescription = null, tint = WarmYellow)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Adaptive Learning Assistance", fontWeight = FontWeight.Bold, color = OffWhite)
                        Text("Provides dynamic context hints when repeated mistakes happen.", fontSize = 12.sp, color = SoftGray)
                    }
                    Switch(checked = adaptiveHints, onCheckedChange = { adaptiveHints = it })
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Screen04Demographics() {
    val interests = listOf("Math", "Programming", "Languages", "History", "Science", "Data Analysis", "Logic")
    var selectedInterests by remember { mutableStateOf(setOf("Math", "Programming")) }
    var selectedAgeGroup by remember { mutableStateOf("18-24") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        ExplorerMascot(
            size = MascotSize.MD,
            emotion = MascotEmotion.THINKING,
            item = MascotItem.CALENDAR
        )
        Text("About You", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = OffWhite, modifier= Modifier.padding(
            0.dp,40.dp,0.dp,0.dp
        ))
        Spacer(modifier = Modifier.height(16.dp))

        Text("Select your age group", fontSize = 14.sp, color = SoftGray)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(vertical = 8.dp)) {
            listOf("Under 18", "18-24", "25-34", "35+").forEach { age ->
                FilterChip(
                    selected = age == selectedAgeGroup,
                    onClick = { selectedAgeGroup = age },
                    label = { Text(age) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("Primary Areas of Interest", fontSize = 14.sp, color = SoftGray)
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
}

@Composable
fun Screen05SelfAssessment() {
    var selectedLevel by remember { mutableStateOf("Beginner") }

    val levels = listOf(
        "Beginner" to "print(\"hello world\")",
        "Novice" to "for i in range(10):",
        "Intermediate" to "def calculate(x: int) -> bool:",
        "Advanced" to "class Node<T: Comparable<T>>"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        ExplorerMascot(
            size = MascotSize.MD,
            emotion = MascotEmotion.THINKING,
            item = MascotItem.SHIELD
        )
        Text("Self-Assessment", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = OffWhite, modifier= Modifier.padding(
            0.dp,40.dp,0.dp,0.dp
        ))
        Text("Select your experience level", fontSize = 14.sp, color = SoftGray)

        Spacer(modifier = Modifier.height(24.dp))

        levels.forEach { (level, snippet) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { selectedLevel = level }
                    .border(
                        width = 2.dp,
                        color = if (selectedLevel == level) EmeraldGreen else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    ),
                colors = CardDefaults.cardColors(containerColor = SurfaceNavy)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text(level, fontWeight = FontWeight.Bold, color = OffWhite, fontSize = 16.sp)
                        if (selectedLevel == level) Icon(Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldGreen)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
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
}

@Composable
fun Screen06DiagnosticTest() {
    var selectedOption by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(20.dp, 35.dp, 20.dp, 50.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Diagnostic Test", color = EmeraldGreen, fontWeight = FontWeight.Bold)
                Text("Question 1 of 3", color = SoftGray)
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(progress = { 0.33f }, modifier = Modifier.fillMaxWidth(), color = EmeraldGreen)

            Spacer(modifier = Modifier.height(32.dp))

            Text("What is the time complexity of searching an element in a balanced Binary Search Tree?", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = OffWhite, modifier= Modifier.padding(
            0.dp,40.dp,0.dp,0.dp
        ))

            Spacer(modifier = Modifier.height(24.dp))

            val options = listOf("O(1)", "O(log n)", "O(n)", "O(n²)")
            options.forEachIndexed { index, option ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { selectedOption = index }
                        .border(
                            width = 2.dp,
                            color = if (selectedOption == index) EmeraldGreen else Color.Transparent,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    colors = CardDefaults.cardColors(containerColor = SurfaceNavy)
                ) {
                    Text(option, modifier = Modifier.padding(16.dp), color = OffWhite, fontSize = 16.sp)
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ExplorerMascot(
                size = MascotSize.LG,
                emotion = MascotEmotion.THINKING,
                item = MascotItem.NONE
            )
        }

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen)
        ) {
            Text("SUBMIT ANSWER", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun Screen07Schedule() {
    var selectedTime by remember { mutableStateOf("15 min") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        ExplorerMascot(
            size = MascotSize.LG,
            emotion = MascotEmotion.ENCOURAGING,
            item = MascotItem.CALENDAR
        )
        Text("Daily Goal", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = OffWhite, modifier= Modifier.padding(
            0.dp,40.dp,0.dp,0.dp
        ))
        Text("Set your learning rhythm to build a habit.", color = SoftGray, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(32.dp))

        listOf("5 min / day" to "Casual learning", "15 min / day" to "Regular streak", "30 min / day" to "Intensive path").forEach { (time, desc) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { selectedTime = time }
                    .border(
                        width = 2.dp,
                        color = if (selectedTime == time) EmeraldGreen else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    ),
                colors = CardDefaults.cardColors(containerColor = SurfaceNavy)
            ) {
                Row(modifier = Modifier.padding(20.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text(time, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = OffWhite)
                        Text(desc, fontSize = 12.sp, color = SoftGray)
                    }
                    Icon(Icons.Default.Schedule, contentDescription = null, tint = WarmYellow)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen08Auth() {
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        containerColor = DeepNavy
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                    Text(
                        text = "Criar Conta",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = OffWhite
                    )

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
                                Toast.makeText(context, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen)
                    ) {
                        Text("CADASTRAR", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun Screen09Dashboard() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("Trajeto Teu", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = EmeraldGreen)
            AssistChip(
                onClick = {},
                label = { Text("🔥 7 Day Streak", color = WarmYellow, fontWeight = FontWeight.Bold) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        var texto by remember { mutableStateOf("") }
        OutlinedTextField(
            value = texto,
            onValueChange = {novoTexto ->
                texto = novoTexto
            },
            placeholder = { Text("What do you want to learn?") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = SoftGray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Recommended Tracks", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = OffWhite, modifier= Modifier.padding(
            0.dp,40.dp,0.dp,0.dp
        ))
        Spacer(modifier = Modifier.height(30.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(listOf("Data Analysis", "Python Graphs", "Scheduling Problems")) { track ->
                Card(
                    modifier = Modifier
                        .width(220.dp)
                        .height(160.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceNavy)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.SpaceBetween) {
                        Text(track, fontWeight = FontWeight.Bold, color = OffWhite, fontSize = 16.sp)
                        Text("Practical use case: Solve scheduling and optimize workflows.", fontSize = 12.sp, color = SoftGray)
                        Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen)) {
                            Text("Start Track", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ExplorerMascot(
                size = MascotSize.LG,
                emotion = MascotEmotion.THINKING,
                item = MascotItem.NONE
            )
        }
    }
}

@Composable
fun Screen10Discovery() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("Enrolled Areas", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = OffWhite, modifier= Modifier.padding(
            0.dp,40.dp,0.dp,0.dp
        ))
        }
        items(listOf("Computer Science", "Math Foundations")) { area ->
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = SurfaceNavy)) {
                ListItem(
                    headlineContent = { Text(area, fontWeight = FontWeight.Bold, color = OffWhite) },
                    supportingContent = { Text("Breakdown: Interactive logic, practical algorithms.", color = SoftGray) },
                    leadingContent = { Icon(Icons.Default.Book, contentDescription = null, tint = EmeraldGreen) }
                )
            }
        }

        item {
            Text("Recommended For You", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = OffWhite, modifier= Modifier.padding(
            0.dp,40.dp,0.dp,0.dp
        ))
        }
        items(listOf("Everyday Math", "Python Programming", "Algorithmic Logic")) { rec ->
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = SurfaceNavy)) {
                ListItem(
                    headlineContent = { Text(rec, fontWeight = FontWeight.Bold, color = OffWhite) },
                    supportingContent = { Text("Interactive step-by-step learning path.", color = SoftGray) },
                    leadingContent = { Icon(Icons.Default.Explore, contentDescription = null, tint = WarmYellow) }
                )
            }
        }
        item{
            Row(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ExplorerMascot(
                    size = MascotSize.LG,
                    emotion = MascotEmotion.ENCOURAGING,
                    item = MascotItem.NONE
                )
            }
        }
    }
}

@Composable
fun Screen11Profile() {
    var timeFrame by remember { mutableStateOf("7 Days") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(16.dp, 70.dp, 16.dp, 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(EmeraldGreen, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Face, contentDescription = null, modifier = Modifier.size(50.dp), tint = DeepNavy)
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Explorer Student",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = OffWhite,
            modifier = Modifier.padding(0.dp, 40.dp, 0.dp, 0.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("7 Days", "30 Days", "365 Days").forEach { frame ->
                FilterChip(
                    selected = timeFrame == frame,
                    onClick = { timeFrame = frame },
                    label = { Text(frame) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            StatCard(
                title = "Evaluations",
                value = "12",
                modifier = Modifier.fillMaxWidth(0.5f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(title = "Accuracy Rate", value = "94%", modifier = Modifier.weight(1f))
            StatCard(title = "Problems Solved", value = "148", modifier = Modifier.weight(1f))
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = EmeraldGreen,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                fontSize = 11.sp,
                color = SoftGray,
                textAlign = TextAlign.Center
            )
        }
    }
}