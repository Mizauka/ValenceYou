package com.valenceyou.affect

import androidx.compose.ui.graphics.Color

/**
 * AffectBlock: 一个情绪状态的原子单位
 * 
 * 不是"数据点",是"感受的容器"
 * 大小 = 强度 (faint/present/strong/overwhelming)
 * 位置 = 关系 (靠近 = 有关系)
 * 颜色 = 情绪类型
 */
data class AffectBlock(
    val id: String,
    val label: String,
    val emotionType: EmotionType,
    val intensity: IntensityBand = IntensityBand.PRESENT,
    val x: Float = 0f,
    val y: Float = 0f,
    val scale: Float = 1f,
    val isDragging: Boolean = false
) {
    val visualRadius: Float
        get() = when (intensity) {
            IntensityBand.FAINT -> 24f
            IntensityBand.PRESENT -> 40f
            IntensityBand.STRONG -> 60f
            IntensityBand.OVERWHELMING -> 80f
        } * scale

    val influenceRadius: Float
        get() = visualRadius * 2.5f  // 吸附半径 > 视觉半径

    val color: Color
        get() = emotionType.color

    val alpha: Float
        get() = when (intensity) {
            IntensityBand.FAINT -> 0.4f
            IntensityBand.PRESENT -> 0.7f
            IntensityBand.STRONG -> 0.9f
            IntensityBand.OVERWHELMING -> 1.0f
        }
}

enum class EmotionType(val color: Color) {
    JOY(Color(0xFFFFF59D)),        // 柔和黄
    SADNESS(Color(0xFF90CAF9)),    // 柔和蓝
    ANGER(Color(0xFFEF9A9A)),      // 柔和红
    FEAR(Color(0xFFCE93D8)),       // 柔和紫
    DISGUST(Color(0xFFA5D6A7)),    // 柔和绿
    SURPRISE(Color(0xFFFFCC80)),   // 柔和橙
    TRUST(Color(0xFF80CBC4)),      // 柔和青
    ANTICIPATION(Color(0xFFFFB74D)), // 柔和琥珀
    NEUTRAL(Color(0xFFB0BEC5))     // 柔和灰
}

enum class IntensityBand {
    FAINT,      // 隐约存在
    PRESENT,    // 明显感受
    STRONG,     // 强烈占据
    OVERWHELMING // 压倒一切
}

/**
 * Cluster: 情绪状态的软聚合
 * 不是"分组",是"关系场"
 */
data class Cluster(
    val blocks: List<AffectBlock>,
    val centroidX: Float,
    val centroidY: Float,
    val dominantEmotion: EmotionType,
    val averageIntensity: Float
) {
    val isSoft: Boolean
        get() = blocks.size >= 2 && averageIntensity < 0.7f

    val isDense: Boolean
        get() = blocks.size >= 3 && averageIntensity >= 0.7f
}
