package com.valenceyou

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.*

class ContentActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val title = intent.getStringExtra("title") ?: ""
        val subtitle = intent.getStringExtra("subtitle") ?: ""
        val color = intent.getIntExtra("color", 0xFF424242.toInt())
        
        val scrollView = ScrollView(this)
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
        val titleView = TextView(this).apply {
            text = title
            textSize = 24f
            setTextColor(0xFF424242.toInt())
            setPadding(0, 0, 0, 8)
        }
        layout.addView(titleView)
        
        // Subtitle
        val subView = TextView(this).apply {
            text = subtitle
            textSize = 14f
            setTextColor(0xFF757575.toInt())
            setPadding(0, 0, 0, 48)
        }
        layout.addView(subView)
        
        // Sections
        addSection(layout, "身体里可能发生了什么", listOf(
            "心跳加速，即使坐着也像在跑",
            "肩膀紧绷，牙关咬紧",
            "胃部紧缩或恶心"
        ), color)
        
        addSection(layout, "危险信号", listOf(
            "连续48小时无法停止活动",
            "出现幻听或思维奔逸"
        ), 0xFFEF9A9A.toInt())
        
        addSection(layout, "先做这些", listOf(
            "冷水冲手腕：激活潜水反射，强制降速",
            "重毯子压身：提供边界感，减少漂浮感",
            "写下来：把循环思维外化，给大脑减负"
        ), 0xFFA5D6A7.toInt())
        
        scrollView.addView(layout)
        setContentView(scrollView)
    }
    
    private fun addSection(parent: LinearLayout, title: String, items: List<String>, color: Int) {
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
            setBackgroundColor(color and 0x1FFFFFFF)
        }
        
        val titleView = TextView(this@ContentActivity).apply {
            text = title
            textSize = 16f
            setTextColor(if (title == "危险信号") 0xFFC62828.toInt() else 0xFF424242.toInt())
            setPadding(0, 0, 0, 16)
        }
        card.addView(titleView)
        
        items.forEachIndexed { index, item ->
            val row = LinearLayout(this@ContentActivity).apply {
                orientation = LinearLayout.HORIZONTAL
                setPadding(0, 8, 0, 8)
                
                val num = TextView(this@ContentActivity).apply {
                    text = "${index + 1}."
                    textSize = 13f
                    setTextColor(color)
                    setPadding(0, 0, 16, 0)
                }
                addView(num)
                
                val text = TextView(this@ContentActivity).apply {
                    text = item
                    textSize = 14f
                    setTextColor(0xFF616161.toInt())
                }
                addView(text)
            }
            card.addView(row)
        }
        
        parent.addView(card)
        parent.addView(View(this).apply { setPadding(0, 20, 0, 0) })
    }
}
