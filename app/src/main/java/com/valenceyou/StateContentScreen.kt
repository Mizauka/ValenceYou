package com.valenceyou

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * StateContentScreen: 状态内容页
 *
 * 统一模板:
 * - 这种感觉是什么
 * - 身体里可能发生了什么
 * - 危险信号
 * - 先做这些
 *
 * 限制: ≤ 1 screen, ≤ 3 min read, ≤ 4 bullets / section
 */
@Composable
fun StateContentScreen(
    anchor: PhenomenologicalAnchor,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        // Top bar
        Surface(
            color = anchor.color.copy(alpha = 0.1f),
            tonalElevation = 0.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onBack) {
                    Text("返回", color = Color(0xFF757575))
                }
            }
        }

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(24.dp)
        ) {
            // Title section
            Text(
                text = anchor.experience,
                fontSize = 24.sp,
                fontWeight = FontWeight.Light,
                color = Color(0xFF424242)
            )

            Text(
                text = "这种感觉是什么",
                fontSize = 14.sp,
                color = Color(0xFF757575),
                modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
            )

            // Body Signals
            ContentSection(
                title = "身体里可能发生了什么",
                color = anchor.color,
                items = anchor.bodySignals
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Risk Signs
            ContentSection(
                title = "危险信号",
                color = Color(0xFFEF9A9A),
                items = anchor.riskSigns,
                isWarning = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Harm Reduction
            ContentSection(
                title = "先做这些",
                color = Color(0xFFA5D6A7),
                items = anchor.harmReduction
            )

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Composable
private fun ContentSection(
    title: String,
    color: Color,
    items: List<String>,
    isWarning: Boolean = false
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.12f)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = if (isWarning) Color(0xFFC62828) else Color(0xFF424242),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            items.forEachIndexed { index, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "${index + 1}.",
                        fontSize = 13.sp,
                        color = color.copy(alpha = 0.8f),
                        modifier = Modifier.width(24.dp)
                    )

                    Text(
                        text = item,
                        fontSize = 14.sp,
                        color = Color(0xFF616161),
                        lineHeight = 20.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
