package com.valenceyou.affect

import kotlin.math.*

/**
 * RiskDetector: 情绪风险的连续投影
 * 
 * 不是 "危险/安全" 的二元判断
 * 是 "当前状态向风险方向的倾斜程度"
 * 
 * 核心: weighted blending of risk vectors
 */
class RiskDetector {
    
    /**
     * 风险向量: 每种情绪对整体风险状态的贡献
     */
    data class RiskVector(
        val emotionType: EmotionType,
        val intensityWeight: Float,    // 强度权重 (0-1)
        val riskDirection: Float,      // 风险方向 (-1 到 1, 负=保护性, 正=风险性)
        val isolationWeight: Float     // 孤立权重 (孤立的情绪更危险)
    )
    
    /**
     * 风险状态: 连续投影结果
     */
    data class RiskState(
        val overallRisk: Float,         // 0-1, 整体风险水平
        val riskDirection: Float,     // -1 到 1, 负=内摄, 正=外放
        val primaryRisk: String,      // 主要风险描述
        val contributingFactors: List<String>,
        val protectiveFactors: List<String>,
        val recommendation: String
    )
    
    /**
     * 情绪风险基线 (基于心理学研究)
     */
    private val emotionRiskBaselines = mapOf(
        EmotionType.JOY to -0.3f,           // 保护性
        EmotionType.SADNESS to 0.4f,        // 风险性 (抑郁倾向)
        EmotionType.ANGER to 0.5f,          // 风险性 (冲动)
        EmotionType.FEAR to 0.3f,           // 风险性 (焦虑)
        EmotionType.DISGUST to 0.2f,      // 轻微风险
        EmotionType.SURPRISE to -0.1f,     // 轻微保护
        EmotionType.TRUST to -0.4f,       // 保护性
        EmotionType.ANTICIPATION to -0.2f, // 轻微保护
        EmotionType.NEUTRAL to 0f
    )
    
    fun analyze(blocks: List<AffectBlock>, clusters: List<Cluster>): RiskState {
        if (blocks.isEmpty()) {
            return RiskState(
                overallRisk = 0f,
                riskDirection = 0f,
                primaryRisk = "无数据",
                contributingFactors = emptyList(),
                protectiveFactors = emptyList(),
                recommendation = "请添加情绪状态"
            )
        }
        
        // Calculate risk vectors for each block
        val riskVectors = blocks.map { block ->
            val baseline = emotionRiskBaselines[block.emotionType] ?: 0f
            val intensityValue = when (block.intensity) {
                IntensityBand.FAINT -> 0.25f
                IntensityBand.PRESENT -> 0.5f
                IntensityBand.STRONG -> 0.75f
                IntensityBand.OVERWHELMING -> 1.0f
            }
            
            // Isolation: blocks not in any cluster are more risky
            val isIsolated = clusters.none { cluster ->
                cluster.blocks.any { it.id == block.id }
            }
            val isolationWeight = if (isIsolated) 1.5f else 1.0f
            
            RiskVector(
                emotionType = block.emotionType,
                intensityWeight = intensityValue,
                riskDirection = baseline,
                isolationWeight = isolationWeight
            )
        }
        
        // Weighted blending
        val totalWeight = riskVectors.sumOf {
            (it.intensityWeight * it.isolationWeight).toDouble()
        }.toFloat()
        
        val weightedRisk = if (totalWeight > 0) {
            riskVectors.sumOf {
                (it.riskDirection * it.intensityWeight * it.isolationWeight).toDouble()
            }.toFloat() / totalWeight
        } else 0f
        
        // Normalize to 0-1 for overall risk
        val overallRisk = ((weightedRisk + 1f) / 2f).coerceIn(0f, 1f)
        
        // Determine risk direction
        val riskDirection = weightedRisk
        
        // Identify factors
        val contributingFactors = riskVectors
            .filter { it.riskDirection > 0.2f && it.intensityWeight > 0.5f }
            .map { "${it.emotionType.name.lowercase().replaceFirstChar { c -> c.uppercase() }} (${it.intensityWeight})" }
        
        val protectiveFactors = riskVectors
            .filter { it.riskDirection < -0.2f && it.intensityWeight > 0.5f }
            .map { "${it.emotionType.name.lowercase().replaceFirstChar { c -> c.uppercase() }} (${it.intensityWeight})" }
        
        // Generate recommendation
        val recommendation = generateRecommendation(overallRisk, riskDirection, clusters)
        
        // Primary risk description
        val primaryRisk = when {
            overallRisk < 0.2f -> "情绪状态稳定"
            overallRisk < 0.4f -> "轻微情绪波动"
            overallRisk < 0.6f -> "情绪压力明显"
            overallRisk < 0.8f -> "情绪风险较高"
            else -> "需要关注"
        }
        
        return RiskState(
            overallRisk = overallRisk,
            riskDirection = riskDirection,
            primaryRisk = primaryRisk,
            contributingFactors = contributingFactors,
            protectiveFactors = protectiveFactors,
            recommendation = recommendation
        )
    }
    
    private fun generateRecommendation(
        overallRisk: Float,
        riskDirection: Float,
        clusters: List<Cluster>
    ): String {
        return when {
            overallRisk < 0.2f -> "当前状态良好，保持关注即可"
            overallRisk < 0.4f -> {
                if (riskDirection > 0) "注意情绪外放，适当放松"
                else "注意情绪内摄，尝试表达"
            }
            overallRisk < 0.6f -> {
                if (clusters.any { it.isDense }) "情绪聚集明显，建议分散注意力"
                else "情绪较为分散，尝试找到核心问题"
            }
            overallRisk < 0.8f -> "情绪压力较大，建议寻求支持"
            else -> "情绪状态需要专业关注"
        }
    }
}
