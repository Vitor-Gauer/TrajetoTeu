package com.example.trajetoteu.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.trajetoteu.ui.screens.*
import com.example.trajetoteu.ui.theme.DeepNavy
import com.example.trajetoteu.ui.theme.OffWhite
import com.example.trajetoteu.ui.theme.SurfaceNavy

object Rotas {
    const val ONBOARDING_1_ROLE = "onboarding_role"
    const val ONBOARDING_2_MOTIVATION = "onboarding_motivation"
    const val ONBOARDING_3_ACCESSIBILITY = "onboarding_accessibility"
    const val ONBOARDING_4_DEMOGRAPHICS = "onboarding_demographics"
    const val ONBOARDING_5_SELF_ASSESSMENT = "onboarding_self_assessment"
    const val ONBOARDING_6_DIAGNOSTIC = "onboarding_diagnostic"
    const val ONBOARDING_7_SCHEDULE = "onboarding_schedule"
    const val ONBOARDING_8_AUTH = "onboarding_auth"

    const val DASHBOARD = "dashboard"
    const val DISCOVERY = "discovery"
    const val PROFILE = "profile"

    // MAF: Listas e Detalhes
    const val TRILHAS_LISTA = "trilhas_lista"
    const val TRILHA_DETALHE = "trilha_detalhe/{trilhaId}"
    const val TOPICOS_LISTA = "topicos_lista"
    const val TOPICO_DETALHE = "topico_detalhe/{topicoId}"

    const val LESSON_EXPERIENCE = "lesson_experience/{topicoId}"

    fun trilhaDetalhe(trilhaId: Int) = "trilha_detalhe/$trilhaId"
    fun topicoDetalhe(topicoId: Int) = "topico_detalhe/$topicoId"
    fun lessonExperience(topicoId: Int) = "lesson_experience/$topicoId"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val exibeBottomBar = currentRoute in listOf(
        Rotas.DASHBOARD, Rotas.DISCOVERY, Rotas.PROFILE, Rotas.TRILHAS_LISTA, Rotas.TOPICOS_LISTA
    )

    Scaffold(
        topBar = {
            if (currentRoute != null && currentRoute !in listOf(Rotas.DASHBOARD, Rotas.LESSON_EXPERIENCE) && navController.previousBackStackEntry != null) {
                TopAppBar(
                    title = { Text("Trajeto Teu", fontWeight = FontWeight.Bold, color = OffWhite) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = OffWhite)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = DeepNavy)
                )
            }
        },
        bottomBar = {
            if (exibeBottomBar) {
                NavigationBar(containerColor = SurfaceNavy) {
                    NavigationBarItem(
                        selected = currentRoute == Rotas.DASHBOARD,
                        onClick = { navController.navigate(Rotas.DASHBOARD) { popUpTo(Rotas.DASHBOARD) { saveState = true }; launchSingleTop = true } },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Início") },
                        label = { Text("Início") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == Rotas.TRILHAS_LISTA,
                        onClick = { navController.navigate(Rotas.TRILHAS_LISTA) },
                        icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Trilhas") },
                        label = { Text("Trilhas") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == Rotas.TOPICOS_LISTA,
                        onClick = { navController.navigate(Rotas.TOPICOS_LISTA) },
                        icon = { Icon(Icons.Default.CompassCalibration, contentDescription = "Tópicos") },
                        label = { Text("Tópicos") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == Rotas.PROFILE,
                        onClick = { navController.navigate(Rotas.PROFILE) },
                        icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                        label = { Text("Perfil") }
                    )
                }
            }
        },
        containerColor = DeepNavy
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Rotas.ONBOARDING_1_ROLE,
            modifier = Modifier.padding(innerPadding)
        ) {
            // --- FLUXO DE ONBOARDING ---
            composable(Rotas.ONBOARDING_1_ROLE) {
                Screen01UserRole(onNext = { navController.navigate(Rotas.ONBOARDING_2_MOTIVATION) })
            }
            composable(Rotas.ONBOARDING_2_MOTIVATION) {
                Screen02Motivation(onNext = { navController.navigate(Rotas.ONBOARDING_3_ACCESSIBILITY) })
            }
            composable(Rotas.ONBOARDING_3_ACCESSIBILITY) {
                Screen03Accessibility(onNext = { navController.navigate(Rotas.ONBOARDING_4_DEMOGRAPHICS) })
            }
            composable(Rotas.ONBOARDING_4_DEMOGRAPHICS) {
                Screen04Demographics(onNext = { navController.navigate(Rotas.ONBOARDING_5_SELF_ASSESSMENT) })
            }
            composable(Rotas.ONBOARDING_5_SELF_ASSESSMENT) {
                Screen05SelfAssessment(onNext = { navController.navigate(Rotas.ONBOARDING_6_DIAGNOSTIC) })
            }
            composable(Rotas.ONBOARDING_6_DIAGNOSTIC) {
                Screen06DiagnosticTest(onNext = { navController.navigate(Rotas.ONBOARDING_7_SCHEDULE) })
            }
            composable(Rotas.ONBOARDING_7_SCHEDULE) {
                Screen07Schedule(onNext = { navController.navigate(Rotas.ONBOARDING_8_AUTH) })
            }
            composable(Rotas.ONBOARDING_8_AUTH) {
                Screen08Auth(onAuthSuccess = {
                    navController.navigate(Rotas.DASHBOARD) {
                        popUpTo(Rotas.ONBOARDING_1_ROLE) { inclusive = true }
                    }
                })
            }

            // --- NAVEGAÇÃO PRINCIPAL ---
            composable(Rotas.DASHBOARD) {
                Screen09Dashboard(
                    onNavigateToTrail = { trilhaId -> navController.navigate(Rotas.trilhaDetalhe(trilhaId)) },
                    onNavigateDiscovery = { navController.navigate(Rotas.DISCOVERY) }
                )
            }
            composable(Rotas.DISCOVERY) {
                Screen10Discovery(onSelectTrack = { navController.navigate(Rotas.TRILHAS_LISTA) })
            }
            composable(Rotas.PROFILE) {
                Screen11Profile()
            }

            // --- REQUISITOS MAF: LISTA 1 & DETALHE 1 (TRILHAS COM ROOM) ---
            composable(Rotas.TRILHAS_LISTA) {
                ScreenTrilhasLista(
                    onTrilhaClick = { id -> navController.navigate(Rotas.trilhaDetalhe(id)) }
                )
            }
            composable(
                route = Rotas.TRILHA_DETALHE,
                arguments = listOf(navArgument("trilhaId") { type = NavType.IntType })
            ) { backStackEntry ->
                val trilhaId = backStackEntry.arguments?.getInt("trilhaId") ?: 1
                ScreenTrilhaDetalhe(
                    trilhaId = trilhaId,
                    onStartLesson = { topicoId -> navController.navigate(Rotas.lessonExperience(topicoId)) }
                )
            }

            // --- REQUISITOS MAF: LISTA 2 & DETALHE 2 (TÓPICOS COM ROOM) ---
            composable(Rotas.TOPICOS_LISTA) {
                ScreenTopicosLista(
                    onTopicoClick = { id -> navController.navigate(Rotas.topicoDetalhe(id)) }
                )
            }
            composable(
                route = Rotas.TOPICO_DETALHE,
                arguments = listOf(navArgument("topicoId") { type = NavType.IntType })
            ) { backStackEntry ->
                val topicoId = backStackEntry.arguments?.getInt("topicoId") ?: 1
                ScreenTopicoDetalhe(
                    topicoId = topicoId,
                    onOpenLesson = { id -> navController.navigate(Rotas.lessonExperience(id)) }
                )
            }

            // --- TELA 12: LESSON & TRAIL EXPERIENCE ---
            composable(
                route = Rotas.LESSON_EXPERIENCE,
                arguments = listOf(navArgument("topicoId") { type = NavType.IntType })
            ) { backStackEntry ->
                val topicoId = backStackEntry.arguments?.getInt("topicoId") ?: 1
                Screen12LessonAndTrail(
                    topicoId = topicoId,
                    onExitClick = { navController.popBackStack() }
                )
            }
        }
    }
}
