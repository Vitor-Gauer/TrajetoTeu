package com.example.trajetoteu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trajetoteu.ui.theme.EmeraldGreen
import com.example.trajetoteu.ui.theme.OffWhite
import com.example.trajetoteu.ui.theme.SurfaceNavy

enum class MascotSize {
    SM, MD, LG
}

enum class MascotEmotion {
    HAPPY, THINKING, ENCOURAGING
}

enum class MascotItem {
    NONE, CALENDAR, PIE, MAGNIFIER, COMPASS, SHIELD
}

@Composable
fun ExplorerMascot(
    modifier: Modifier = Modifier,
    size: MascotSize = MascotSize.MD,
    emotion: MascotEmotion = MascotEmotion.HAPPY,
    item: MascotItem = MascotItem.NONE
) {
    val mascotDp = when (size) {
        MascotSize.SM -> 48.dp
        MascotSize.MD -> 72.dp
        MascotSize.LG -> 100.dp
    }

    val emoji = when (emotion) {
        MascotEmotion.HAPPY -> "😸"
        MascotEmotion.THINKING -> "🤔"
        MascotEmotion.ENCOURAGING -> "🌟"
    }

    val itemIcon: ImageVector? = when (item) {
        MascotItem.NONE -> null
        MascotItem.CALENDAR -> Icons.Default.DateRange
        MascotItem.PIE -> Icons.Default.PieChart
        MascotItem.MAGNIFIER -> Icons.Default.Search
        MascotItem.COMPASS -> Icons.Default.Explore
        MascotItem.SHIELD -> Icons.Default.Shield
    }

    Surface(
        modifier = modifier
            .size(mascotDp)
            .clip(CircleShape),
        color = SurfaceNavy,
        tonalElevation = 4.dp,
        shadowElevation = 4.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = emoji,
                fontSize = when (size) {
                    MascotSize.SM -> 22.sp
                    MascotSize.MD -> 34.sp
                    MascotSize.LG -> 48.sp
                }
            )

            if (itemIcon != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(2.dp)
                        .size(
                            when (size) {
                                MascotSize.SM -> 16.dp
                                MascotSize.MD -> 22.dp
                                MascotSize.LG -> 30.dp
                            }
                        )
                        .clip(CircleShape)
                        .background(EmeraldGreen),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = itemIcon,
                        contentDescription = null,
                        tint = OffWhite,
                        modifier = Modifier.fillMaxSize(0.7f)
                    )
                }
            }
        }
    }
}
