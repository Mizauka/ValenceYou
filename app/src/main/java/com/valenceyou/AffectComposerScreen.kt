package com.valenceyou

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.valenceyou.affect.*
import kotlin.math.*

/**
 * AffectComposerScreen: 状态表达入口
 *
 * 不是"主界面"
 * 是"不知道如何描述? 从身体感觉开始"
 *
 * 流程:
 * 1. 放置颜色拼块 (drag + scale)
 * 2. 观察关系形成 (soft emotional gravity)
 * 3. 系统路由到对应状态页
 */
@Composable
fun AffectComposerScreen(
    onRouteToState: (PhenomenologicalAnchor) -> Unit,
    onBack: () -> Unit
) {
    var blocks by remember { mutableStateOf(listOf<AffectBlock>()) }
    var showRouteHint by remember { mutableStateOf(false) }

    val riskDetector = remember { RiskDetector() }
    val clusters = remember(blocks) {
        calculateClustersForApp(blocks)
    }
    val riskState = remember(blocks, clusters) {
        riskDetector.analyze(blocks, clusters)
    }

    // State routing logic - map to PhenomenologicalAnchor
    val routedAnchor = remember(riskState, clusters) {
        routeToAnchor(riskState, clusters, blocks)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Top bar
        Surface(
            color = Color(0xFFFAFAFA),
            tonalElevation = 0.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onBack) {
                    Text("返回", color = Color(0xFF757575))
                }

                Text(
                    text = "从身体感觉开始",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF424242)
                )

                if (blocks.isNotEmpty() && routedAnchor != null) {
                    TextButton(onClick = {
                        onRouteToState(routedAnchor)
                    }) {
                        Text("这像我", color = Color(0xFF2E7D32))
                    }
                } else {
                    Spacer(modifier = Modifier.width(48.dp))
                }
            }
        }

        // Hint text
        if (blocks.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "拖动颜色拼块,表达你现在的感受\n靠近 = 有关系,大小 = 强度",
                    fontSize = 13.sp,
                    color = Color(0xFF9E9E9E),
                    lineHeight = 18.sp
                )
            }
        }

        // Affect Canvas
        AffectCanvas(
            blocks = blocks,
            onBlocksChange = { blocks = it },
            modifier = Modifier.weight(1f)
        )

        // Bottom: emotion buttons
        Surface(
            color = Color(0xFFFAFAFA),
            tonalElevation = 0.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Routing hint
                if (routedAnchor != null && blocks.size >= 2) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = routedAnchor.color.copy(alpha = 0.2f)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "这可能接近: ${routedAnchor.experience}",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF424242)
                                )
                            }

                            Button(
                                onClick = { onRouteToState(routedAnchor) },
                                shape = RoundedCornerShape(20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = routedAnchor.color.copy(alpha = 0.6f)
                                )
                            ) {
                                Text("进入", fontSize = 12.sp)
                            }
                        }
                    }
                }

                // Emotion buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    EmotionType.entries.filter { it != EmotionType.NEUTRAL }.forEach { emotion ->
                        EmotionButton(
                            emotion = emotion,
                            onClick = {
                                val newBlock = AffectBlock(
                                    id = System.currentTimeMillis().toString(),
                                    label = emotion.name.lowercase().replaceFirstChar { it.uppercase() },
                                    emotionType = emotion,
                                    x = 200f + (blocks.size * 30) % 300,
                                    y = 300f + (blocks.size * 40) % 200
                                )
                                blocks = blocks + newBlock
                            }
                        )
                    }
                }
            }
        }
    }
}

/**
 * State Routing: 从情绪拼图映射到现象学锚点
 */
private fun routeToAnchor(
    riskState: RiskDetector.RiskState,
    clusters: List<Cluster>,
    blocks: List<AffectBlock>
): PhenomenologicalAnchor? {
    if (blocks.size < 2) return null

    // Count emotion types
    val emotionCounts = blocks.groupingBy { it.emotionType }.eachCount()
    val hasOverwhelming = blocks.any { it.intensity == IntensityBand.OVERWHELMING }
    val hasStrong = blocks.any { it.intensity == IntensityBand.STRONG }

    // Route logic based on emotion patterns
    return when {
        // Activation/Overdrive patterns
        (emotionCounts[EmotionType.ANGER] ?: 0) >= 2 &&
        (emotionCounts[EmotionType.ANTICIPATION] ?: 0) >= 1 &&
        (hasStrong || hasOverwhelming) -> PhenomenologicalAnchor.CANT_STOP

        // Numbness/Dissociation patterns
        (emotionCounts[EmotionType.SADNESS] ?: 0) >= 2 &&
        (emotionCounts[EmotionType.FEAR] ?: 0) >= 1 &&
        clusters.any { it.isSoft } -> PhenomenologicalAnchor.CANT_FEEL_SELF

        // Attachment/Validation patterns
        (emotionCounts[EmotionType.TRUST] ?: 0) >= 1 &&
        (emotionCounts[EmotionType.SURPRISE] ?: 0) >= 1 &&
        riskState.overallRisk in 0.3f..0.6f -> PhenomenologicalAnchor.DEPENDENT_ON_SOMEONE

        // Collapse patterns
        (emotionCounts[EmotionType.FEAR] ?: 0) >= 2 &&
        (emotionCounts[EmotionType.ANTICIPATION] ?: 0) >= 1 -> PhenomenologicalAnchor.NO_STRENGTH

        // Default based on risk direction
        riskState.riskDirection > 0.3f -> PhenomenologicalAnchor.CANT_STOP
        riskState.riskDirection < -0.3f -> PhenomenologicalAnchor.CANT_FEEL_SELF
        else -> PhenomenologicalAnchor.NO_STRENGTH
    }
}

@Composable
private fun EmotionButton(
    emotion: EmotionType,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        color = emotion.color.copy(alpha = 0.3f),
        modifier = Modifier.size(48.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize())
    }
}
