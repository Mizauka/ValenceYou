package com.valenceyou

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valenceyou.affect.*
import com.valenceyou.ui.ResultScreen
import com.valenceyou.ui.theme.ValenceYouTheme

/**
 * ValenceYou: 情绪拓扑系统
 * 
 * 不是情绪追踪器
 * 是"感受的空间"
 * 
 * 核心交互:
 * - 放置情绪块 (drag + scale)
 * - 观察关系形成 (soft emotional gravity)
 * - 看到风景 (single-screen, non-clinical)
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

@Composable
fun ValenceYouApp() {
    var blocks by remember { mutableStateOf(listOf<AffectBlock>()) }
    var showResult by remember { mutableStateOf(false) }
    
    val riskDetector = remember { RiskDetector() }
    val clusters = remember(blocks) {
        calculateClustersForApp(blocks)
    }
    val riskState = remember(blocks, clusters) {
        riskDetector.analyze(blocks, clusters)
    }
    
    if (showResult) {
        ResultScreen(
            blocks = blocks,
            clusters = clusters,
            riskState = riskState,
            onBack = { showResult = false }
        )
    } else {
        AffectCanvasScreen(
            blocks = blocks,
            onBlocksChange = { blocks = it },
            onShowResult = { showResult = true }
        )
    }
}

@Composable
private fun AffectCanvasScreen(
    blocks: List<AffectBlock>,
    onBlocksChange: (List<AffectBlock>) -> Unit,
    onShowResult: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Top bar: minimal
        Surface(
            color = Color(0xFFFAFAFA),
            tonalElevation = 0.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "ValenceYou",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF424242)
                )
                
                if (blocks.isNotEmpty()) {
                    TextButton(onClick = onShowResult) {
                        Text("看到风景", color = Color(0xFF757575))
                    }
                }
            }
        }
        
        // Canvas
        AffectCanvas(
            blocks = blocks,
            onBlocksChange = onBlocksChange,
            modifier = Modifier.weight(1f)
        )
        
        // Bottom controls: add emotion
        Surface(
            color = Color(0xFFFAFAFA),
            tonalElevation = 0.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
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
                            onBlocksChange(blocks + newBlock)
                        }
                    )
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
        shape = CircleShape,
        color = emotion.color.copy(alpha = 0.3f),
        modifier = Modifier.size(48.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Simple color dot
        }
    }
}

private fun calculateClustersForApp(blocks: List<AffectBlock>): List<Cluster> {
    if (blocks.size < 2) return emptyList()
    
    val minDistance = 120f
    val visited = mutableSetOf<String>()
    val clusters = mutableListOf<Cluster>()
    
    blocks.forEach { block ->
        if (block.id in visited) return@forEach
        
        val clusterBlocks = mutableListOf<AffectBlock>()
        val queue = mutableListOf(block)
        
        while (queue.isNotEmpty()) {
            val current = queue.removeAt(0)
            if (current.id in visited) continue
            
            visited.add(current.id)
            clusterBlocks.add(current)
            
            blocks.filter { other ->
                other.id != current.id &&
                other.id !in visited &&
                distanceApp(current, other) < minDistance * 1.5f
            }.forEach { queue.add(it) }
        }
        
        if (clusterBlocks.size >= 2) {
            val avgX = clusterBlocks.map { it.x }.average().toFloat()
            val avgY = clusterBlocks.map { it.y }.average().toFloat()
            val dominant = clusterBlocks
                .groupBy { it.emotionType }
                .maxByOrNull { it.value.size }?.key ?: EmotionType.NEUTRAL
            val avgIntensity = clusterBlocks.map {
                when (it.intensity) {
                    IntensityBand.FAINT -> 0.25f
                    IntensityBand.PRESENT -> 0.5f
                    IntensityBand.STRONG -> 0.75f
                    IntensityBand.OVERWHELMING -> 1.0f
                }
            }.average().toFloat()
            
            clusters.add(Cluster(clusterBlocks, avgX, avgY, dominant, avgIntensity))
        }
    }
    
    return clusters
}

private fun distanceApp(a: AffectBlock, b: AffectBlock): Float {
    return kotlin.math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y))
}
