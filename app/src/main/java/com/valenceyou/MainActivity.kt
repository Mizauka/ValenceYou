package com.valenceyou

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.valenceyou.affect.*
import com.valenceyou.ui.theme.ValenceYouTheme

/**
 * ValenceYou: 状态导航系统
 *
 * 不是"情绪追踪器"
 * 是"从身体感觉开始的理解路径"
 *
 * 结构:
 * 1. 首页: 32个现象学锚点 (不显示分类,只显示体验句子)
 * 2. Affect Composer: 不知道如何描述? → 从身体感觉开始 → 颜色拼块
 * 3. 状态页: 身体解释 / 风险识别 / 减害内容
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ValenceYouTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFFAFAFA)
                ) {
                    ValenceYouApp()
                }
            }
        }
    }
}

sealed class Screen {
    object Home : Screen()
    object AffectComposer : Screen()
    data class StatePage(val anchor: PhenomenologicalAnchor) : Screen()
}

@Composable
fun ValenceYouApp() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }

    when (val screen = currentScreen) {
        is Screen.Home -> HomeScreen(
            onAnchorClick = { anchor ->
                currentScreen = Screen.StatePage(anchor)
            },
            onAffectComposerClick = {
                currentScreen = Screen.AffectComposer
            }
        )
        is Screen.AffectComposer -> AffectComposerScreen(
            onRouteToState = { anchor ->
                currentScreen = Screen.StatePage(anchor)
            },
            onBack = {
                currentScreen = Screen.Home
            }
        )
        is Screen.StatePage -> StateContentScreen(
            anchor = screen.anchor,
            onBack = {
                currentScreen = Screen.Home
            }
        )
    }
}

@Composable
private fun HomeScreen(
    onAnchorClick: (PhenomenologicalAnchor) -> Unit,
    onAffectComposerClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "ValenceYou",
            fontSize = 28.sp,
            fontWeight = FontWeight.Light,
            color = Color(0xFF424242)
        )

        Text(
            text = "从身体感觉开始理解",
            fontSize = 14.sp,
            color = Color(0xFF9E9E9E),
            modifier = Modifier.padding(top = 8.dp, bottom = 48.dp)
        )

        // 32个现象学锚点 (不显示分类)
        PhenomenologicalAnchor.entries.forEach { anchor ->
            AnchorCard(
                anchor = anchor,
                onClick = { onAnchorClick(anchor) }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Affect Composer 入口
        OutlinedButton(
            onClick = onAffectComposerClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFF757575)
            )
        ) {
            Text(
                "不知道如何描述? 从身体感觉开始",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun AnchorCard(
    anchor: PhenomenologicalAnchor,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = anchor.color.copy(alpha = 0.12f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Color indicator
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(anchor.color, RoundedCornerShape(6.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = anchor.experience,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF424242),
                modifier = Modifier.weight(1f)
            )
        }
    }
}
