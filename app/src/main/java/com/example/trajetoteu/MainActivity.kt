package com.example.trajetoteu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.example.trajetoteu.ui.screens.*
import com.example.trajetoteu.ui.theme.TrajetoTeuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrajetoTeuTheme {
                RenderScreen(12)
            }
        }
    }
}

@Composable
fun RenderScreen(screenNumber: Int) {
    when (screenNumber) {
        1 -> Screen01UserRole()
        2 -> Screen02Motivation()
        3 -> Screen03Accessibility()
        4 -> Screen04Demographics()
        5 -> Screen05SelfAssessment()
        6 -> Screen06DiagnosticTest()
        7 -> Screen07Schedule()
        8 -> Screen08Auth()
        9 -> Screen09Dashboard()
        10 -> Screen10Discovery()
        11 -> Screen11Profile()
        12 -> Screen12LessonAndTrail()
        else -> Screen01UserRole()
    }

}