package com.valenceyou.screens

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
import com.valenceyou.model.*
import com.valenceyou.routing.*

/**
 * ComposerScreen: 状态表达入口
 *
 * 不是"主界面"
 * 是"不知道如何描述? 从身体感觉开始"
 *
 * 流程:
 * 1. 放置颜色拼块 (drag + scale)
 * 2. 系统路由到对应锚点 (AnchorRouter)
 * 3. 显示 top-k 最近锚点
 */
@Composable
fun ComposerScreen(
    router: AnchorRouter,
    onRouteToAnchor: (PhenomenologicalAnchor) -> Unit,
    onBack: () -> Unit
) {
    var blocks by remember { mutableStateOf(listOf<AffectBlock>()) }

    // Convert blocks to AffectVector
    val affectVector = remember(blocks) {
        blocksToVector(blocks)
    }

    // Route to anchors
    val scoredAnchors = remember(affectVector) {
        if (blocks.size >= 2) router.routeTopK(affectVector, k = 3) else emptyList()
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

                Spacer(modifier = Modifier.width(48.dp))
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

        // Affect Canvas (simplified)
        AffectCanvas(
            blocks = blocks,
            onBlocksChange = { blocks = it },
            modifier = Modifier.weight(1f)
        )

        // Bottom: routing results
        Surface(
            color = Color(0xFFFAFAFA),
            tonalElevation = 0.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Top-k anchors
                scoredAnchors.forEach { scored ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                            .clickable { onRouteToAnchor(scored.anchor) },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = scored.anchor.color.copy(alpha = 0.2f)
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
                                    text = scored.anchor.title,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF424242)
                                )
                                Text(
                                    text = "相似度: ${(scored.score * 100).toInt()}%",
                                    fontSize = 12.sp,
                                    color = Color(0xFF757575)
                                )
                            }

                            Button(
                                onClick = { onRouteToAnchor(scored.anchor) },
                                shape = RoundedCornerShape(20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = scored.anchor.color.copy(alpha = 0.6f)
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
                    EmotionType.entries.forEach { emotion ->
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

private fun blocksToVector(blocks: List<AffectBlock>): AffectVector {
    val emotionCounts = blocks.groupingBy { it.emotionType }.eachCount()
    val total = blocks.size.toFloat()

    val emotions = emotionCounts.mapValues { it.value / total }
    val intensity = blocks.map { it.intensity }.average().toFloat()
    val proximity = if (blocks.size >= 2) {
        // Calculate average distance between blocks
        var totalDist = 0f
        var count = 0
        for (i in blocks.indices) {
            for (j in i + 1 until blocks.size) {
                totalDist += distance(blocks[i], blocks[j])
                count++
            }
        }
        if (count > 0) 1f - (totalDist / count / 500f).coerceIn(0f, 1f) else 0f
    } else 0f

    return AffectVector(emotions, intensity, proximity)
}

private fun distance(a: AffectBlock, b: AffectBlock): Float {
    return kotlin.math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y))
}
