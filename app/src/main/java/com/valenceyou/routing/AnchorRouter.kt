package com.valenceyou.routing

import com.valenceyou.model.*
import kotlin.math.*

/**
 * AnchorRouter: 锚点路由器
 */
class AnchorRouter(
    private val anchors: List<PhenomenologicalAnchor>
) {
    fun route(emotions: Map<EmotionType, Float>, intensity: Float): List<ScoredAnchor> {
        return anchors.map { anchor ->
            val score = calculateSimilarity(anchor, emotions, intensity)
            ScoredAnchor(anchor, score)
        }.sortedByDescending { it.score }
    }

    fun routeTopK(emotions: Map<EmotionType, Float>, intensity: Float, k: Int = 3): List<ScoredAnchor> {
        return route(emotions, intensity).take(k)
    }

    private fun calculateSimilarity(anchor: PhenomenologicalAnchor, emotions: Map<EmotionType, Float>, intensity: Float): Float {
        val categoryScore = when (anchor.category) {
            AnchorCategory.ACTIVATION -> (emotions[EmotionType.ANGER] ?: 0f) + (emotions[EmotionType.ANTICIPATION] ?: 0f)
            AnchorCategory.NUMBNESS -> (emotions[EmotionType.SADNESS] ?: 0f) + (emotions[EmotionType.FEAR] ?: 0f)
            AnchorCategory.ATTACHMENT -> (emotions[EmotionType.TRUST] ?: 0f) + (emotions[EmotionType.SURPRISE] ?: 0f)
            AnchorCategory.COLLAPSE -> (emotions[EmotionType.FEAR] ?: 0f) + (emotions[EmotionType.SADNESS] ?: 0f)
        }
        val intensityScore = 1f - abs(intensity - 0.5f) * 2f
        return (categoryScore * 0.6f + intensityScore * 0.4f).coerceIn(0f, 1f)
    }
}

data class ScoredAnchor(
    val anchor: PhenomenologicalAnchor,
    val score: Float
)
