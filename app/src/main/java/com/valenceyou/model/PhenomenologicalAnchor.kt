package com.valenceyou.model

import androidx.compose.ui.graphics.Color

/**
 * PhenomenologicalAnchor: 现象学锚点
 */
data class PhenomenologicalAnchor(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: AnchorCategory,
    val color: Color
)

enum class AnchorCategory {
    ACTIVATION,
    NUMBNESS,
    ATTACHMENT,
    COLLAPSE
}

data class AnchorContent(
    val description: String,
    val bodySignals: List<String>,
    val riskSignals: List<String>,
    val grounding: List<String>
)

enum class EmotionType {
    JOY, SADNESS, ANGER, FEAR, DISGUST, SURPRISE, TRUST, ANTICIPATION
}
