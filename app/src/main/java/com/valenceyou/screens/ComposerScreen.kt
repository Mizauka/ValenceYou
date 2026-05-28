package com.valenceyou.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.clickable
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

@Composable
fun ComposerScreen(
    router: AnchorRouter,
    onRouteToAnchor: (PhenomenologicalAnchor) -> Unit,
    onBack: () -> Unit
) {
    var blocks by remember { mutableStateOf(listOf<AffectBlock>()) }

    val emotions = remember(blocks) {
        val counts = blocks.groupingBy { it.emotionType }.eachCount()
        val total = blocks.size.toFloat().coerceAtLeast(1f)
        counts.mapValues { it.value / total }
    }

    val intensity = remember(blocks) {
        if (blocks.isEmpty()) 0f else blocks.map { it.intensity }.average().toFloat()
    }

    val scoredAnchors = remember(emotions, intensity) {
        if (blocks.size >= 2) router.routeTopK(emotions, intensity, k = 3) else emptyList()
    }

    Column(modifier = Modifier.fillMaxSize()) {
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

        // Simplified canvas placeholder
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "[Affect Canvas Placeholder]\nDrag blocks to express feelings",
                fontSize = 14.sp,
                color = Color(0xFF9E9E9E)
            )
        }

        Surface(
            color = Color(0xFFFAFAFA),
            tonalElevation = 0.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
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
    val color = when (emotion) {
        EmotionType.JOY -> Color(0xFFFFF59D)
        EmotionType.SADNESS -> Color(0xFF90CAF9)
        EmotionType.ANGER -> Color(0xFFEF9A9A)
        EmotionType.FEAR -> Color(0xFFCE93D8)
        EmotionType.DISGUST -> Color(0xFFA5D6A7)
        EmotionType.SURPRISE -> Color(0xFFFFCC80)
        EmotionType.TRUST -> Color(0xFF80CBC4)
        EmotionType.ANTICIPATION -> Color(0xFFFFB74D)
    }

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        color = color.copy(alpha = 0.3f),
        modifier = Modifier.size(48.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize())
    }
}

data class AffectBlock(
    val id: String,
    val label: String,
    val emotionType: EmotionType,
    val intensity: Float = 0.5f,
    val x: Float = 0f,
    val y: Float = 0f
)
