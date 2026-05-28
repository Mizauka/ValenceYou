package com.valenceyou

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
 * 1. 首页: 4个状态入口 (我停不下来/我感觉不到自己/我终于感觉到自己/我睡不着)
 * 2. Affect Composer: 不知道如何描述? → 从身体感觉开始 → 颜色拼块
 * 3. State Routing: 拼图结果 → 映射到状态入口
 * 4. 内容页: 身体解释/风险识别/减害内容
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
    data class StatePage(val state: MentalState) : Screen()
}

enum class MentalState(
    val title: String,
    val subtitle: String,
    val color: Color,
    val bodySignals: List<String>,
    val riskSigns: List<String>,
    val harmReduction: List<String>
) {
    OVERDRIVE(
        title = "我停不下来",
        subtitle = "思绪在转,身体在跑,但不知道在追什么",
        color = Color(0xFFFFCC80),
        bodySignals = listOf(
            "心跳加速,即使坐着也像在跑",
            "肩膀紧绷,牙关咬紧",
            "胃部紧缩或恶心",
            "手指颤抖或坐立不安"
        ),
        riskSigns = listOf(
            "连续48小时无法停止活动",
            "出现幻听或思维奔逸",
            "自伤冲动作为'减速'手段"
        ),
        harmReduction = listOf(
            "冷水冲手腕: 激活潜水反射,强制降速",
            "重毯子压身: 提供边界感,减少漂浮感",
            "写下来: 把循环思维外化,给大脑减负"
        )
    ),
    NUMBNESS(
        title = "我感觉不到自己",
        subtitle = "身体在这里,但'我'不在",
        color = Color(0xFF90CAF9),
        bodySignals = listOf(
            "皮肤感觉像橡胶或棉花",
            "时间感扭曲(几分钟像几小时)",
            "声音听起来很远",
            "无法识别自己的情绪"
        ),
        riskSigns = listOf(
            "完全无法感知疼痛或温度",
            "出现人格解体(看自己像陌生人)",
            "现实解体(世界像假的)"
        ),
        harmReduction = listOf(
            "5-4-3-2-1 grounding: 看到5样,听到4样,摸到3样,闻到2样,尝到1样",
            "冷水洗脸: 唤醒面部神经,拉回身体",
            "咀嚼冰块: 强烈感官刺激,打破麻木"
        )
    ),
    AWARENESS(
        title = "我终于感觉到自己",
        subtitle = "某种东西松开了,但不确定这意味着什么",
        color = Color(0xFF80CBC4),
        bodySignals = listOf(
            "突然流泪但不知道原因",
            "身体某处突然放松(通常是胃或肩)",
            "对光线或声音突然敏感",
            "时间感变得清晰"
        ),
        riskSigns = listOf(
            "感觉太强烈,想逃回麻木",
            "出现解离作为自我保护",
            "情绪闪回淹没当下"
        ),
        harmReduction = listOf(
            "不要试图理解,先允许存在",
            "用手按住胸口: 提供身体锚点",
            "缓慢呼吸: 不要深呼吸,那可能触发恐慌"
        )
    ),
    INSOMNIA(
        title = "我睡不着",
        subtitle = "身体累了,但某种东西不让休息",
        color = Color(0xFFCE93D8),
        bodySignals = listOf(
            "眼皮沉重但大脑活跃",
            "腿部不适,想移动",
            "体温波动(忽冷忽热)",
            "心跳在安静时变得明显"
        ),
        riskSigns = listOf(
            "连续72小时无法入睡",
            "出现偏执或被害妄想",
            "幻觉(通常从听觉开始)"
        ),
        harmReduction = listOf(
            "不要强迫入睡: 降低床=压力的条件反射",
            "起床做单调的事: 折袜子,数瓷砖",
            "降低核心体温: 热水澡后自然降温"
        )
    )
}

@Composable
fun ValenceYouApp() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }

    when (val screen = currentScreen) {
        is Screen.Home -> HomeScreen(
            onStateClick = { state ->
                currentScreen = Screen.StatePage(state)
            },
            onAffectComposerClick = {
                currentScreen = Screen.AffectComposer
            }
        )
        is Screen.AffectComposer -> AffectComposerScreen(
            onRouteToState = { state ->
                currentScreen = Screen.StatePage(state)
            },
            onBack = {
                currentScreen = Screen.Home
            }
        )
        is Screen.StatePage -> StateContentScreen(
            state = screen.state,
            onBack = {
                currentScreen = Screen.Home
            }
        )
    }
}

@Composable
private fun HomeScreen(
    onStateClick: (MentalState) -> Unit,
    onAffectComposerClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
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

        // 4个状态入口
        MentalState.entries.forEach { state ->
            StateEntryCard(
                state = state,
                onClick = { onStateClick(state) }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.weight(1f))

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
private fun StateEntryCard(
    state: MentalState,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = state.color.copy(alpha = 0.15f)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = state.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF424242)
            )
            Text(
                text = state.subtitle,
                fontSize = 13.sp,
                color = Color(0xFF757575),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
