package com.valenceyou.model

import androidx.compose.ui.graphics.Color

/**
 * PhenomenologicalAnchor: 现象学锚点
 *
 * 数据结构:
 * - id: 唯一标识
 * - title: 体验句子 (如"我停不下来")
 * - subtitle: 简短描述
 * - category: 内部分类 (不显示给用户)
 * - color: 视觉标识
 */
data class PhenomenologicalAnchor(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: AnchorCategory,
    val color: Color
) {
    val contentPath: String
        get() = "content/anchors/$id.json"
}

enum class AnchorCategory {
    ACTIVATION,   // 能量过剩
    NUMBNESS,     // 解离麻木
    ATTACHMENT,   // 依恋寻求
    COLLAPSE      // 崩溃耗竭
}

/**
 * AnchorContent: 锚点内容
 *
 * 从local json/markdown加载
 */
data class AnchorContent(
    val description: String,
    val bodySignals: List<String>,
    val riskSignals: List<String>,
    val grounding: List<String>
)

/**
 * AffectVector: 情绪向量
 *
 * 用于路由计算
 */
data class AffectVector(
    val emotions: Map<EmotionType, Float>,
    val intensity: Float,
    val proximity: Float
)

enum class EmotionType {
    JOY, SADNESS, ANGER, FEAR, DISGUST, SURPRISE, TRUST, ANTICIPATION
}
