package com.valenceyou

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val scrollView = ScrollView(this)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 96, 48, 48)
        }
        
        // Title
        val title = TextView(this).apply {
            text = "ValenceYou"
            textSize = 28f
            setTextColor(0xFF424242.toInt())
            setPadding(0, 0, 0, 8)
        }
        layout.addView(title)
        
        // Subtitle
        val subtitle = TextView(this).apply {
            text = "从身体感觉开始理解"
            textSize = 14f
            setTextColor(0xFF9E9E9E.toInt())
            setPadding(0, 0, 0, 48)
        }
        layout.addView(subtitle)
        
        // Composer button
        val composerBtn = Button(this).apply {
            text = "不知道如何描述? 从身体感觉开始"
            setBackgroundColor(0xFFF5F5F5.toInt())
            setTextColor(0xFF757575.toInt())
            setPadding(32, 24, 32, 24)
            setOnClickListener {
                startActivity(Intent(this@MainActivity, ComposerActivity::class.java))
            }
        }
        layout.addView(composerBtn)
        
        layout.addView(View(this).apply { setPadding(0, 32, 0, 0) })
        
        // Anchors (first 8)
        val anchors = listOf(
            Triple("我停不下来", "能量过剩，方向不明", 0xFFFFCC80),
            Triple("我一直想确认什么", "反复检查，无法安心", 0xFFFFB74D),
            Triple("我越来越冲动", "行动先于思考", 0xFFFFA726),
            Triple("我脑子停不下来", "思维奔逸，无法入睡", 0xFFFF9800),
            Triple("我感觉不到自己", "解离麻木，自我消失", 0xFF90CAF9),
            Triple("世界变得不真实", "现实解体，像在做梦", 0xFF64B5F6),
            Triple("我越来越依赖某个人", "依恋增强，无法独立", 0xFFCE93D8),
            Triple("我没有力气继续了", "能量耗尽，无法行动", 0xFFB0BEC5)
        )
        
        anchors.forEach { (title, subtitle, color) ->
            val card = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                setPadding(24, 24, 24, 24)
                setBackgroundColor(color.toInt() and 0x1FFFFFFF)
                isClickable = true
                isFocusable = true
                
                val dot = View(this@MainActivity).apply {
                    layoutParams = LinearLayout.LayoutParams(24, 24).apply {
                        setMargins(0, 8, 24, 0)
                    }
                    setBackgroundColor(color.toInt())
                }
                addView(dot)
                
                val textLayout = LinearLayout(this@MainActivity).apply {
                    orientation = LinearLayout.VERTICAL
                    
                    val titleView = TextView(this@MainActivity).apply {
                        text = title
                        textSize = 16f
                        setTextColor(0xFF424242.toInt())
                    }
                    addView(titleView)
                    
                    val subView = TextView(this@MainActivity).apply {
                        text = subtitle
                        textSize = 13f
                        setTextColor(0xFF757575.toInt())
                        setPadding(0, 4, 0, 0)
                    }
                    addView(subView)
                }
                addView(textLayout)
                
                setOnClickListener {
                    val intent = Intent(this@MainActivity, ContentActivity::class.java)
                    intent.putExtra("title", title)
                    intent.putExtra("subtitle", subtitle)
                    intent.putExtra("color", color.toInt())
                    startActivity(intent)
                }
            }
            layout.addView(card)
            layout.addView(View(this).apply { setPadding(0, 12, 0, 0) })
        }
        
        // More button
        val moreBtn = TextView(this).apply {
            text = "更多状态 (24)"
            textSize = 14f
            setTextColor(0xFF757575.toInt())
            setPadding(0, 24, 0, 24)
            gravity = android.view.Gravity.CENTER
        }
        layout.addView(moreBtn)
        
        scrollView.addView(layout)
        setContentView(scrollView)
    }
}
