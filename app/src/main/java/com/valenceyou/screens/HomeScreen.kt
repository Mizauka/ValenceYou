package com.valenceyou.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.valenceyou.model.*

@Composable
fun HomeScreen(
    anchors: List<PhenomenologicalAnchor>,
    onAnchorClick: (PhenomenologicalAnchor) -> Unit,
    onAffectComposerClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    var visibleCount by remember { mutableStateOf(8) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
            .verticalScroll(scrollState)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "ValenceYou",
            fontSize = 28.sp,
            fontWeight = FontWeight.Light,
            color = Color(0xFF424242)
        )

        Text(
            text = "从身体感觉开始理解",
            fontSize = 14.sp,
            color = Color(0xFF9E9E9E),
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
        )

        OutlinedButton(
            onClick = onAffectComposerClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFF757575)
            )
        ) {
            Text(
                "不知道如何描述? 从身体感觉开始",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        val visibleAnchors = anchors.take(visibleCount)

        visibleAnchors.forEach { anchor ->
            AnchorCard(
                anchor = anchor,
                onClick = { onAnchorClick(anchor) }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        if (visibleCount < anchors.size) {
            TextButton(
                onClick = { visibleCount = (visibleCount + 8).coerceAtMost(anchors.size) },
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text(
                    "更多状态 (${anchors.size - visibleCount})",
                    fontSize = 14.sp,
                    color = Color(0xFF757575)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun AnchorCard(
    anchor: PhenomenologicalAnchor,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = anchor.color.copy(alpha = 0.12f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(anchor.color, RoundedCornerShape(6.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = anchor.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF424242)
                )

                if (anchor.subtitle.isNotEmpty()) {
                    Text(
                        text = anchor.subtitle,
                        fontSize = 13.sp,
                        color = Color(0xFF757575),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}
