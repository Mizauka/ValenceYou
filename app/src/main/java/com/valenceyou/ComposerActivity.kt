package com.valenceyou

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.*

class ComposerActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 96, 48, 48)
        }
        
        // Back button
        val backBtn = TextView(this).apply {
            text = "返回"
            textSize = 14f
            setTextColor(0xFF757575.toInt())
            setPadding(0, 0, 0, 24)
            isClickable = true
            setOnClickListener { finish() }
        }
        layout.addView(backBtn)
        
        // Title
        val title = TextView(this).apply {
            text = "从身体感觉开始"
            textSize = 20f
            setTextColor(0xFF424242.toInt())
            setPadding(0, 0, 0, 24)
        }
        layout.addView(title)
        
        // Hint
        val hint = TextView(this).apply {
            text = "选择你现在的情绪颜色:\n靠近 = 有关系,大小 = 强度"
            textSize = 13f
            setTextColor(0xFF9E9E9E.toInt())
            setPadding(0, 0, 0, 32)
        }
        layout.addView(hint)
        
        // Emotion buttons
        val emotions = listOf(
            Triple("快乐", 0xFFFFF59D.toInt()),
            Triple("悲伤", 0xFF90CAF9.toInt()),
            Triple("愤怒", 0xFFEF9A9A.toInt()),
            Triple("恐惧", 0xFFCE93D8.toInt()),
            Triple("厌恶", 0xFFA5D6A7.toInt()),
            Triple("惊讶", 0xFFFFCC80.toInt()),
            Triple("信任", 0xFF80CBC4.toInt()),
            Triple("期待", 0xFFFFB74D.toInt())
        )
        
        val grid = GridLayout(this).apply {
            columnCount = 4
            setPadding(0, 0, 0, 32)
        }
        
        emotions.forEach { (label, color) ->
            val btn = Button(this).apply {
                text = label
                setBackgroundColor(color)
                setTextColor(0xFF424242.toInt())
                setPadding(16, 16, 16, 16)
            }
            grid.addView(btn)
        }
        layout.addView(grid)
        
        // Result area
        val resultTitle = TextView(this).apply {
            text = "最近锚点"
            textSize = 16f
            setTextColor(0xFF424242.toInt())
            setPadding(0, 32, 0, 16)
        }
        layout.addView(resultTitle)
        
        val anchorCard = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 24, 24, 24)
            setBackgroundColor(0x1FFFCC80)
            isClickable = true
            
            val anchorTitle = TextView(this@ComposerActivity).apply {
                text = "我停不下来"
                textSize = 16f
                setTextColor(0xFF424242.toInt())
            }
            addView(anchorTitle)
            
            val anchorSub = TextView(this@ComposerActivity).apply {
                text = "相似度: 85%"
                textSize = 12f
                setTextColor(0xFF757575.toInt())
                setPadding(0, 4, 0, 0)
            }
            addView(anchorSub)
            
            setOnClickListener {
                val intent = android.content.Intent(this@ComposerActivity, ContentActivity::class.java)
                intent.putExtra("title", "我停不下来")
                intent.putExtra("subtitle", "能量过剩，方向不明")
                intent.putExtra("color", 0xFFFFCC80.toInt())
                startActivity(intent)
            }
        }
        layout.addView(anchorCard)
        
        setContentView(layout)
    }
}
