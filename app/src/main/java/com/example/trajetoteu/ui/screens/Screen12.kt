package com.example.trajetoteu.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trajetoteu.model.TopicoEstudo
import com.example.trajetoteu.ui.theme.WarmYellow

private val DeepNavy = Color(0xFF0F172A)
private val SurfaceNavy = Color(0xFF1E293B)
private val OffWhite = Color(0xFFF8FAFC)
private val SoftGray = Color(0xFF94A3B8)
private val EmeraldGreen = Color(0xFF10B981)
private val SoftYellow = Color(0xFFFEF08A)
private val WarningText = Color(0xFF854D0E)

enum class FeedbackState {
    NEUTRAL, MISTAKE, SUCCESS
}

@Composable
fun Screen12LessonAndTrail(
    topicoId: String = "top1",
    topicos: List<TopicoEstudo> = emptyList(),
    onExitClick: () -> Unit = {}
) {
    val topicoAtual = topicos.find { it.id == topicoId } ?: topicos.firstOrNull() ?: TopicoEstudo(
        id = "top1",
        trilhaId = "t1",
        titulo = "Problema do Agendamento",
        perguntaDesafio = "Qual a melhor abordagem para agendar tarefas com prazos simultâneos?",
        opcaoCorreta = "Priorizar a tarefa mais curta",
        opcaoIncorreta = "Executar em ordem aleatória",
        dica = "Dica: Tente analisar a duração de cada evento antes de ordenar."
    )

    var feedbackState by remember { mutableStateOf(FeedbackState.NEUTRAL) }
    var isMuted by remember { mutableStateOf(false) }

    val backgroundColor by animateColorAsState(
        targetValue = getBackgroundColor(feedbackState),
        animationSpec = tween(durationMillis = 400),
        label = "backgroundColorAnimation"
    )

    val textColor = getTextColor(feedbackState)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        LessonTopBar(
            onExitClick = onExitClick,
            textColor = textColor,
            isMuted = isMuted,
            onToggleMute = { isMuted = !isMuted }
        )

        Spacer(modifier = Modifier.height(12.dp))

        LessonMainContent(
            modifier = Modifier.weight(1f),
            topico = topicoAtual,
            feedbackState = feedbackState,
            onOptionSelected = { newState -> feedbackState = newState }
        )

        FeedbackBottomBar(
            feedbackState = feedbackState,
            dica = topicoAtual.dica,
            onContinueClick = { feedbackState = FeedbackState.NEUTRAL }
        )
    }
}

private fun getBackgroundColor(state: FeedbackState): Color = when (state) {
    FeedbackState.NEUTRAL -> DeepNavy
    FeedbackState.MISTAKE -> SoftYellow
    FeedbackState.SUCCESS -> EmeraldGreen.copy(alpha = 0.2f)
}

private fun getTextColor(state: FeedbackState): Color =
    if (state == FeedbackState.MISTAKE) WarningText else OffWhite

@Composable
private fun LessonTopBar(
    onExitClick: () -> Unit,
    textColor: Color,
    isMuted: Boolean,
    onToggleMute: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onExitClick) {
                Icon(Icons.Default.Close, contentDescription = "Fechar", tint = textColor)
            }

            Spacer(modifier = Modifier.width(8.dp))

            LinearProgressIndicator(
                progress = { 0.65f },
                modifier = Modifier
                    .weight(1f)
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp)),
                color = EmeraldGreen,
                trackColor = SurfaceNavy
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(onClick = { }) {
                    Icon(Icons.Default.Flag, contentDescription = "Reportar", tint = SoftGray, modifier = Modifier.size(20.dp))
                }
                IconButton(onClick = onToggleMute) {
                    Icon(
                        imageVector = if (isMuted) Icons.AutoMirrored.Filled.VolumeOff else Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = "Áudio",
                        tint = SoftGray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun LessonMainContent(
    modifier: Modifier = Modifier,
    topico: TopicoEstudo,
    feedbackState: FeedbackState,
    onOptionSelected: (FeedbackState) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TrailHeaderSection(feedbackState = feedbackState, titulo = topico.titulo)
        }
        item {
            ExerciseCardSection(topico = topico, onOptionSelected = onOptionSelected)
        }
    }
}

@Composable
private fun TrailHeaderSection(feedbackState: FeedbackState, titulo: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TrackInfoCard(modifier = Modifier.weight(1f), titulo = titulo)
        TrailMapCard(modifier = Modifier.weight(1.2f), feedbackState = feedbackState)
    }
}

@Composable
private fun TrackInfoCard(modifier: Modifier = Modifier, titulo: String) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = SurfaceNavy),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(text = titulo, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = OffWhite)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Otimize slots de tempo.", fontSize = 11.sp, color = SoftGray)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Progresso: 65%", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = EmeraldGreen)
        }
    }
}

@Composable
private fun TrailMapCard(modifier: Modifier = Modifier, feedbackState: FeedbackState) {
    Card(
        modifier = modifier.height(150.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceNavy),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(12.dp)
        ) {
            PlatformNode(modifier = Modifier.align(Alignment.BottomStart), isCompleted = true, label = "1")
            Box(modifier = Modifier.align(Alignment.Center)) {
                ExplorerMascot(
                    size = MascotSize.SM,
                    emotion = if (feedbackState == FeedbackState.MISTAKE) MascotEmotion.THINKING else MascotEmotion.HAPPY,
                    item = MascotItem.CALENDAR
                )
            }
            PlatformNode(modifier = Modifier.align(Alignment.TopEnd), isCompleted = false, label = "2")
        }
    }
}

@Composable
private fun ExerciseCardSection(
    topico: TopicoEstudo,
    onOptionSelected: (FeedbackState) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = SurfaceNavy),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = topico.titulo, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = OffWhite)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = topico.perguntaDesafio, fontSize = 14.sp, color = SoftGray, textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(16.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OptionButton(
                    text = topico.opcaoCorreta,
                    onClick = { onOptionSelected(FeedbackState.SUCCESS) }
                )
                OptionButton(
                    text = topico.opcaoIncorreta,
                    onClick = { onOptionSelected(FeedbackState.MISTAKE) }
                )
            }
        }
    }
}

@Composable
private fun FeedbackBottomBar(
    feedbackState: FeedbackState,
    dica: String,
    onContinueClick: () -> Unit
) {
    if (feedbackState == FeedbackState.NEUTRAL) return

    val isMistake = feedbackState == FeedbackState.MISTAKE
    val containerColor = if (isMistake) WarmYellow else EmeraldGreen
    val contentColor = if (isMistake) WarningText else DeepNavy

    Surface(
        modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
        shape = RoundedCornerShape(16.dp),
        color = containerColor
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (isMistake) "Quase lá!" else "Excelente!",
                    fontWeight = FontWeight.Bold,
                    color = contentColor,
                    fontSize = 16.sp
                )
                Text(
                    text = if (isMistake) dica else "Você dominou o conceito!",
                    color = contentColor,
                    fontSize = 12.sp
                )
            }
            Button(
                onClick = onContinueClick,
                colors = ButtonDefaults.buttonColors(containerColor = DeepNavy, contentColor = OffWhite)
            ) {
                Text("Continuar")
            }
        }
    }
}

@Composable
private fun PlatformNode(modifier: Modifier = Modifier, isCompleted: Boolean, label: String) {
    Box(
        modifier = modifier
            .size(32.dp)
            .background(if (isCompleted) EmeraldGreen else SurfaceNavy, CircleShape)
            .border(2.dp, EmeraldGreen, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(text = label, color = OffWhite, fontWeight = FontWeight.Bold, fontSize = 11.sp)
    }
}

@Composable
private fun OptionButton(text: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        color = DeepNavy,
        border = androidx.compose.foundation.BorderStroke(1.dp, SoftGray.copy(alpha = 0.3f))
    ) {
        Text(
            text = text,
            color = OffWhite,
            fontSize = 14.sp,
            modifier = Modifier.padding(12.dp),
            textAlign = TextAlign.Center
        )
    }
}