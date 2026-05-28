package com.valenceyou.affect

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.*

/**
 * AffectCanvas: 情绪状态的关系场
 * 
 * 核心语义:
 * - 靠近 = "有关系" (soft emotional gravity)
 * - 吸附半径 > 视觉半径 (开始互相影响,不是被修正)
 * - high damping, low bounce (quiet, soft, held)
 * - near-direct manipulation (像在移动感受)
 */
@Composable
fun AffectCanvas(
    blocks: List<AffectBlock>,
    onBlocksChange: (List<AffectBlock>) -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    
    // Physics state
    val velocities = remember { mutableStateMapOf<String, Offset>() }
    val targetPositions = remember { mutableStateMapOf<String, Offset>() }
    
    // Soft emotional gravity parameters
    val gravityStrength = 0.03f      // 柔和引力
    val damping = 0.92f              // high damping (quiet)
    val snapThreshold = 0.5f         // 吸附阈值 (soft, not hard)
    val minDistance = 120f           // 最小距离 (呼吸空间)
    
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(blocks) {
                detectDragGestures(
                    onDragStart = { offset ->
                        blocks.find { block ->
                            val pos = Offset(block.x, block.y)
                            (offset - pos).getDistance() < block.visualRadius
                        }?.let { block ->
                            onBlocksChange(blocks.map {
                                if (it.id == block.id) it.copy(isDragging = true)
                                else it
                            })
                        }
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        blocks.find { it.isDragging }?.let { block ->
                            val newX = block.x + dragAmount.x
                            val newY = block.y + dragAmount.y
                            
                            onBlocksChange(blocks.map {
                                if (it.id == block.id) it.copy(x = newX, y = newY)
                                else it
                            })
                        }
                    },
                    onDragEnd = {
                        onBlocksChange(blocks.map { it.copy(isDragging = false) })
                    }
                )
            }
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        
        // Calculate soft clusters
        val clusters = calculateSoftClusters(blocks, minDistance)
        
        // Draw relationship field (subtle connections)
        clusters.forEach { cluster ->
            if (cluster.blocks.size >= 2) {
                drawClusterField(cluster, alpha = 0.15f)
            }
        }
        
        // Draw blocks with soft gravity influence
        blocks.forEach { block ->
            val nearbyBlocks = blocks.filter { other ->
                other.id != block.id && 
                distance(block, other) < block.influenceRadius
            }
            
            // Draw influence halo when near others
            if (nearbyBlocks.isNotEmpty() && !block.isDragging) {
                drawInfluenceHalo(block, nearbyBlocks.size)
            }
            
            drawAffectBlock(block)
        }
        
        // Apply soft gravity (only when not dragging)
        if (blocks.none { it.isDragging }) {
            val newBlocks = blocks.map { block ->
                var forceX = 0f
                var forceY = 0f
                
                // Soft emotional gravity: nearby blocks attract
        blocks.filter { it.id != block.id }.forEach { other ->
                    val dist = distance(block, other)
                    if (dist < block.influenceRadius && dist > minDistance * 0.5f) {
                        val strength = gravityStrength * (1 - dist / block.influenceRadius)
                        val angle = atan2(other.y - block.y, other.x - block.x)
                        forceX += cos(angle) * strength * 50f
                        forceY += sin(angle) * strength * 50f
                    }
                    // Soft repulsion when too close (breathing room)
                    if (dist < minDistance * 0.5f && dist > 0) {
                        val strength = -0.05f * (1 - dist / (minDistance * 0.5f))
                        val angle = atan2(other.y - block.y, other.x - block.x)
                        forceX += cos(angle) * strength * 100f
                        forceY += sin(angle) * strength * 100f
                    }
                }
                
                // Apply velocity with high damping
                val currentVel = velocities[block.id] ?: Offset.Zero
                val newVel = Offset(
                    (currentVel.x + forceX) * damping,
                    (currentVel.y + forceY) * damping
                )
                velocities[block.id] = newVel
                
                // Stop when very slow (quiet settling)
                val speed = sqrt(newVel.x * newVel.x + newVel.y * newVel.y)
                if (speed < 0.5f) {
                    velocities[block.id] = Offset.Zero
                }
                
                val newX = (block.x + newVel.x).coerceIn(
                    block.visualRadius, 
                    canvasWidth - block.visualRadius
                )
                val newY = (block.y + newVel.y).coerceIn(
                    block.visualRadius,
                    canvasHeight - block.visualRadius
                )
                
                block.copy(x = newX, y = newY)
            }
            
            // Update positions
            if (newBlocks != blocks) {
                onBlocksChange(newBlocks)
            }
        }
    }
}

private fun DrawScope.drawAffectBlock(block: AffectBlock) {
    // Outer glow for overwhelming intensity
    if (block.intensity == IntensityBand.OVERWHELMING) {
        drawCircle(
            color = block.color.copy(alpha = 0.2f),
            radius = block.visualRadius * 1.5f,
            center = Offset(block.x, block.y)
        )
    }
    
    // Main circle
    drawCircle(
        color = block.color.copy(alpha = block.alpha),
        radius = block.visualRadius,
        center = Offset(block.x, block.y)
    )
    
    // Inner highlight (soft)
    drawCircle(
        color = block.color.copy(alpha = 0.3f),
        radius = block.visualRadius * 0.7f,
        center = Offset(block.x - block.visualRadius * 0.1f, block.y - block.visualRadius * 0.1f)
    )
}

private fun DrawScope.drawInfluenceHalo(block: AffectBlock, nearbyCount: Int) {
    // Subtle halo indicating "relationship"
    val haloAlpha = 0.08f * nearbyCount.coerceAtMost(3)
    drawCircle(
        color = block.color.copy(alpha = haloAlpha),
        radius = block.influenceRadius,
        center = Offset(block.x, block.y)
    )
}

private fun DrawScope.drawClusterField(cluster: Cluster, alpha: Float) {
    // Soft field connecting related blocks
    val center = Offset(cluster.centroidX, cluster.centroidY)
    
    cluster.blocks.forEach { block ->
        val blockPos = Offset(block.x, block.y)
        val dist = (center - blockPos).getDistance()
        
        if (dist > 0) {
            val fieldAlpha = alpha * (1 - dist / 300f).coerceIn(0f, 1f)
            drawCircle(
                color = cluster.dominantEmotion.color.copy(alpha = fieldAlpha),
                radius = dist * 0.3f,
                center = center
            )
        }
    }
}

private fun calculateSoftClusters(
    blocks: List<AffectBlock>,
    minDistance: Float
): List<Cluster> {
    if (blocks.size < 2) return emptyList()
    
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
                distance(current, other) < minDistance * 1.5f
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

private fun distance(a: AffectBlock, b: AffectBlock): Float {
    return sqrt((a.x - b.x).pow(2) + (a.y - b.y).pow(2))
}
