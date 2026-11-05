package com.example.trajetoteu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
