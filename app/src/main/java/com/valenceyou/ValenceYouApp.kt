package com.valenceyou

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.valenceyou.model.*
import com.valenceyou.routing.AnchorRouter
import com.valenceyou.screens.*

sealed class Screen {
    object Home : Screen()
    object Composer : Screen()
    data class Content(val anchor: PhenomenologicalAnchor) : Screen()
}

@Composable
fun ValenceYouApp(
    anchors: List<PhenomenologicalAnchor>,
    router: AnchorRouter
) {
    var currentScreen = remember { mutableStateOf<Screen>(Screen.Home) }

    when (val screen = currentScreen.value) {
        is Screen.Home -> HomeScreen(
            anchors = anchors,
            onAnchorClick = { anchor ->
                currentScreen.value = Screen.Content(anchor)
            },
            onAffectComposerClick = {
                currentScreen.value = Screen.Composer
            }
        )
        is Screen.Composer -> ComposerScreen(
            router = router,
            onRouteToAnchor = { anchor ->
                currentScreen.value = Screen.Content(anchor)
            },
            onBack = {
                currentScreen.value = Screen.Home
            }
        )
        is Screen.Content -> ContentScreen(
            anchor = screen.anchor,
            content = loadContent(screen.anchor),
            onBack = {
                currentScreen.value = Screen.Home
            }
        )
    }
}

private fun loadContent(anchor: PhenomenologicalAnchor): AnchorContent {
    // TODO: Load from local JSON
    return AnchorContent(
        description = "${anchor.title}是一种常见的身体体验",
        bodySignals = listOf(
            "身体信号1: 具体感受",
            "身体信号2: 具体感受",
            "身体信号3: 具体感受"
        ),
        riskSignals = listOf(
            "危险信号1: 需要注意",
            "危险信号2: 需要寻求帮助"
        ),
        grounding = listOf(
            "先做这些1: 具体行动",
            "先做这些2: 具体行动",
            "先做这些3: 具体行动"
        )
    )
}
