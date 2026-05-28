package com.valenceyou.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.valenceyou.affect.*

/**
 * ResultScreen: 情绪风景
 * 
 * 不是"诊断报告"
 * 是"你刚才画的情绪的风景"
 * 
 * 风格: quiet, soft, held
 * 没有红色警告,没有临床术语
 */
@Composable
fun ResultScreen(
    blocks: List<AffectBlock>,
    clusters: List<Cluster>,
    riskState: RiskDetector.RiskState,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title: soft, not clinical
        Text(
            text = "你的情绪风景",
            fontSize = 24.sp,
            fontWeight = FontWeight.Light,
            color = Color(0xFF424242),
            modifier = Modifier.padding(top = 32.dp, bottom = 8.dp)
        )
        
        Text(
            text = "这是此刻的你",
            fontSize = 14.sp,
            color = Color(0xFF9E9E9E),
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        // Risk state: described, not measured
        RiskDescription(riskState)
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Cluster landscape
        if (clusters.isNotEmpty()) {
            Text(
                text = "情绪关系",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF616161),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 16.dp)
            )
            
            clusters.forEach { cluster ->
                ClusterCard(cluster)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
        
        // Isolated blocks
        val isolatedBlocks = blocks.filter { block ->
            clusters.none { it.blocks.any { it.id == block.id } }
        }
        
        if (isolatedBlocks.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "独立的感受",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF616161),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 16.dp)
            )
            
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                isolatedBlocks.forEach { block ->
                    IsolatedBlockChip(block)
                }
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Back button: soft
        OutlinedButton(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFF757575)
            )
        ) {
            Text("继续探索", fontSize = 14.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun RiskDescription(riskState: RiskDetector.RiskState) {
    val (bgColor, textColor, description) = when {
        riskState.overallRisk < 0.2f -> Triple(
            Color(0xFFE8F5E9), Color(0xFF2E7D32), "平静而稳定"
        )
        riskState.overallRisk < 0.4f -> Triple(
            Color(0xFFFFF8E1), Color(0xFFF57F17), "有些波动，但可控"
        )
        riskState.overallRisk < 0.6f -> Triple(
            Color(0xFFFFF3E0), Color(0xFFEF6C00), "情绪在寻找出口"
        )
        riskState.overallRisk < 0.8f -> Triple(
            Color(0xFFFFEBEE), Color(0xFFC62828), "需要被倾听"
        )
        else -> Triple(
            Color(0xFFFCE4EC), Color(0xFFAD1457), "需要陪伴"
        )
    }
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            .padding(20.dp)
    ) {
        Column {
            Text(
                text = description,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = textColor
            )
            
            if (riskState.contributingFactors.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "主要情绪: ${riskState.contributingFactors.joinToString(", ")}",
                    fontSize = 13.sp,
                    color = textColor.copy(alpha = 0.7f)
                )
            }
            
            if (riskState.protectiveFactors.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "支持力量: ${riskState.protectiveFactors.joinToString(", ")}",
                    fontSize = 13.sp,
                    color = textColor.copy(alpha = 0.7f)
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = riskState.recommendation,
                fontSize = 13.sp,
                color = textColor.copy(alpha = 0.6f),
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun ClusterCard(cluster: Cluster) {
    val density = when {
        cluster.isDense -> "紧密"
        cluster.isSoft -> "柔和"
        else -> "自然"
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = cluster.dominantEmotion.color.copy(alpha = 0.15f)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Color indicator
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(cluster.dominantEmotion.color)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column {
                Text(
                    text = "${cluster.dominantEmotion.name.lowercase().replaceFirstChar { it.uppercase() }} 聚集",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF424242)
                )
                
                Text(
                    text = "${cluster.blocks.size} 个情绪 · ${density}的联系",
                    fontSize = 12.sp,
                    color = Color(0xFF757575)
                )
            }
        }
    }
}

@Composable
private fun IsolatedBlockChip(block: AffectBlock) {
    val intensityDesc = when (block.intensity) {
        IntensityBand.FAINT -> "隐约"
        IntensityBand.PRESENT -> "明显"
        IntensityBand.STRONG -> "强烈"
        IntensityBand.OVERWHELMING -> "压倒"
    }
    
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = block.color.copy(alpha = 0.2f),
        modifier = Modifier.alpha(block.alpha)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(block.color)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "${block.label} · ${intensityDesc}",
                fontSize = 12.sp,
                color = Color(0xFF616161)
            )
        }
    }
}

// Simple FlowRow implementation
@Composable
private fun FlowRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable RowScope.() -> Unit
) {
    // Simplified - in production use Accompanist FlowLayout
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement
    ) {
        content()
    }
}
