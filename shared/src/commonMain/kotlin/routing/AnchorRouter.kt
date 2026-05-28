package com.valenceyou.routing

import com.valenceyou.model.*
import kotlin.math.*

/**
 * AnchorRouter: 锚点路由器
 *
 * 输入: AffectVector (情绪向量)
 * 输出: List<PhenomenologicalAnchor> (按相似度排序)
 *
 * 不是 threshold classification
 * 是 continuous phenomenological navigation
 */
class AnchorRouter(
    private val anchors: List<PhenomenologicalAnchor>
) {
    /**
     * 计算锚点与情绪向量的相似度
     * 使用 cosine similarity + weighted distance
     */
    fun route(vector: AffectVector): List<ScoredAnchor> {
        return anchors.map { anchor ->
            val score = calculateSimilarity(anchor, vector)
            ScoredAnchor(anchor, score)
        }.sortedByDescending { it.score }
    }

    /**
     * 获取 top-k 最近锚点
     */
    fun routeTopK(vector: AffectVector, k: Int = 3): List<ScoredAnchor> {
        return route(vector).take(k)
    }

    private fun calculateSimilarity(anchor: PhenomenologicalAnchor, vector: AffectVector): Float {
        // 基于类别的相似度
        val categoryScore = when (anchor.category) {
            AnchorCategory.ACTIVATION -> vector.emotions[EmotionType.ANGER] ?: 0f +
                    (vector.emotions[EmotionType.ANTICIPATION] ?: 0f)
            AnchorCategory.NUMBNESS -> vector.emotions[EmotionType.SADNESS] ?: 0f +
                    (vector.emotions[EmotionType.FEAR] ?: 0f)
            AnchorCategory.ATTACHMENT -> vector.emotions[EmotionType.TRUST] ?: 0f +
                    (vector.emotions[EmotionType.SURPRISE] ?: 0f)
            AnchorCategory.COLLAPSE -> vector.emotions[EmotionType.FEAR] ?: 0f +
                    (vector.emotions[EmotionType.SADNESS] ?: 0f)
        }

        // 强度匹配
        val intensityScore = 1f - abs(vector.intensity - 0.5f) * 2f

        // 综合得分
        return (categoryScore * 0.6f + intensityScore * 0.4f).coerceIn(0f, 1f)
    }
}

data class ScoredAnchor(
    val anchor: PhenomenologicalAnchor,
    val score: Float
)