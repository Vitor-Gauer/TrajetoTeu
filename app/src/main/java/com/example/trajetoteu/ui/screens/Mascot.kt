package com.example.trajetoteu.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trajetoteu.ui.theme.EmeraldGreen

enum class MascotItem {
    CALENDAR, MAGNIFIER, PIE, COMPASS, SHIELD, NONE
}

enum class MascotEmotion {
    HAPPY, THINKING, ENCOURAGING, CELEBRATING
}

enum class MascotSize(val dp: Dp) {
    SM(64.dp),
    MD(112.dp),
    LG(160.dp)
}

private val Amber600 = Color(0xFFD97706)
private val Amber700 = Color(0xFFB45309)
private val Amber300 = Color(0xFFFCD34D)
private val Amber400 = Color(0xFFFBBF24)
private val Sky400 = Color(0xFF38BDF8)
private val Slate800 = Color(0xFF1E293B)
private val Slate900 = Color(0xFF0F172A)
private val Slate700 = Color(0xFF334155)

@Composable
fun ExplorerMascot(
    modifier: Modifier = Modifier,
    item: MascotItem = MascotItem.NONE,
    emotion: MascotEmotion = MascotEmotion.HAPPY,
    size: MascotSize = MascotSize.MD
) {
    val infiniteTransition = rememberInfiniteTransition(label = "mascot_animations")
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    val auraAlpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    Box(
        modifier = modifier.size(size.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .alpha(auraAlpha)
                .background(EmeraldGreen, shape = CircleShape)
                .blur(20.dp)
        )

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    width = 2.dp,
                    color = EmeraldGreen.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(24.dp)
                ),
            shape = RoundedCornerShape(24.dp),
            color = Slate800,
            shadowElevation = 8.dp
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    MascotHat()

                    MascotFace(emotion = emotion)

                    if (item != MascotItem.NONE) {
                        MascotItemBadge(item = item, rotationAngle = rotationAngle)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    MascotJacket()
                }

                if (emotion == MascotEmotion.ENCOURAGING) {
                    FistPumpHand()
                }
            }
        }
    }
}

@Composable
private fun MascotHat() {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .height(12.dp)
            .clip(RoundedCornerShape(topStartPercent = 100, topEndPercent = 100))
            .background(Amber600),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(8.dp)
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .background(Amber700)
        )
    }
}

@Composable
private fun ColumnScope.MascotFace(emotion: MascotEmotion) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.weight(1f)
    ) {
        EmotionTopSymbol(emotion = emotion)

        Spacer(modifier = Modifier.height(2.dp))

        if (emotion == MascotEmotion.ENCOURAGING) {
            EncouragingBrows()
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Eye()
            Eye()
        }

        Spacer(modifier = Modifier.height(4.dp))

        MascotMouth(emotion = emotion)
    }
}

@Composable
private fun EmotionTopSymbol(emotion: MascotEmotion) {
    when (emotion) {
        MascotEmotion.THINKING -> {
            Text(
                text = "?",
                color = Amber400,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
        MascotEmotion.CELEBRATING -> {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Amber300,
                modifier = Modifier.size(14.dp)
            )
        }
        else -> Spacer(modifier = Modifier.height(14.dp))
    }
}

@Composable
private fun EncouragingBrows() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(bottom = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .width(10.dp)
                .height(2.dp)
                .rotate(15f)
                .background(EmeraldGreen, shape = CircleShape)
        )
        Box(
            modifier = Modifier
                .width(10.dp)
                .height(2.dp)
                .rotate(-15f)
                .background(EmeraldGreen, shape = CircleShape)
        )
    }
}

@Composable
private fun MascotMouth(emotion: MascotEmotion) {
    when (emotion) {
        MascotEmotion.THINKING -> {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .border(1.5.dp, EmeraldGreen, CircleShape)
            )
        }
        MascotEmotion.CELEBRATING -> {
            Box(
                modifier = Modifier
                    .width(10.dp)
                    .height(6.dp)
                    .background(
                        color = EmeraldGreen,
                        shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                    )
            )
        }
        MascotEmotion.HAPPY, MascotEmotion.ENCOURAGING -> {
            Box(
                modifier = Modifier
                    .width(10.dp)
                    .height(5.dp)
                    .border(
                        width = 1.5.dp,
                        color = EmeraldGreen,
                        shape = RoundedCornerShape(bottomStart = 5.dp, bottomEnd = 5.dp)
                    )
            )
        }
    }
}

@Composable
private fun MascotItemBadge(item: MascotItem, rotationAngle: Float) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Slate900.copy(alpha = 0.9f),
        border = BorderStroke(1.dp, Slate700),
        shadowElevation = 4.dp
    ) {
        Box(
            modifier = Modifier.padding(6.dp),
            contentAlignment = Alignment.Center
        ) {
            MascotItemIcon(item = item, rotation = rotationAngle)
        }
    }
}

@Composable
private fun MascotJacket() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
            .background(EmeraldGreen.copy(alpha = 0.8f))
    )
}

@Composable
private fun FistPumpHand() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.CenterEnd
    ) {
        Box(
            modifier = Modifier
                .padding(end = 10.dp, top = 12.dp)
                .size(12.dp)
                .background(Amber600, shape = CircleShape)
                .border(1.dp, Slate900, CircleShape)
        )
    }
}

@Composable
private fun Eye() {
    Box(
        modifier = Modifier
            .size(12.dp)
            .background(EmeraldGreen, shape = CircleShape),
        contentAlignment = Alignment.TopEnd
    ) {
        Box(
            modifier = Modifier
                .padding(top = 2.dp, end = 2.dp)
                .size(5.dp)
                .background(Slate900, shape = CircleShape)
        )
    }
}

@Composable
private fun MascotItemIcon(item: MascotItem, rotation: Float) {
    val (icon: ImageVector, color: Color) = when (item) {
        MascotItem.CALENDAR -> Icons.Default.CalendarToday to EmeraldGreen
        MascotItem.MAGNIFIER -> Icons.Default.Search to Amber400
        MascotItem.PIE -> Icons.Default.PieChart to Sky400
        MascotItem.SHIELD -> Icons.Default.Shield to EmeraldGreen.copy(alpha = 0.8f)
        MascotItem.COMPASS -> Icons.Default.CompassCalibration to Amber300
        MascotItem.NONE -> return
    }

    val modifier = if (item == MascotItem.PIE) {
        Modifier.rotate(rotation)
    } else {
        Modifier
    }

    Icon(
        imageVector = icon,
        contentDescription = null,
        tint = color,
        modifier = modifier.size(18.dp)
    )
}