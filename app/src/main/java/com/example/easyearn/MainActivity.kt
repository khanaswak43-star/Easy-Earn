package com.example.easyearn

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    
    private val captions = mapOf(
        "Bikes" to listOf(
            "🔥 Road badalte rahe, vibe nahi. 🖤\n✨ More: Living the moment.\n🏍️ Ride. Explore. Repeat.",
            "🏍️ Two wheels, endless adventures.\n🌅 Sunrise rides hit different.\n🚀 Speed is my meditation.",
            "🛣️ Highway diaries 📖\n🌙 Raat ka raasta, apna raasta.\n💨 Bas bike aur dreams."
        ),
        "Fashion" to listOf(
            "✨ Style is a statement.\n👗 Dress for the life you want.\n💃 Fashion forward, always.",
            "🌟 Confidence looks best in everything.\n👠 Strut your stuff.\n💅 Be yourself, everyone else is taken.",
            "🎨 Wearing confidence today.\n👔 Classic never goes out of style.\n💎 Elegance is timeless."
        ),
        "Food" to listOf(
            "🍕 Khana bina kya zindagi.\n😋 Taste the happiness.\n🍽️ Food is life, eat with love.",
            "🍜 Noodle dreams 🥟\n🌶️ Spicy life, sweet moments.\n🥘 Cooking love into every meal.",
            "☕ Coffee o'clock ✨\n🍰 Life is short, eat dessert first.\n🥗 Eating good, feeling good."
        ),
        "Travel" to listOf(
            "✈️ Collect moments, not things.\n🌍 Adventure awaits everywhere.\n🗺️ Wanderlust mode: ON",
            "🏖️ Beach vibes only 🌊\n🏔️ Mountain calls, we must go.\n🌴 Paradise found.",
            "🚂 Journey > Destination.\n🎒 Pack light, dream big.\n📸 Memories in every mile."
        ),
        "Lifestyle" to listOf(
            "💪 Living my best life.\n🌱 Growth mindset everyday.\n✨ Creating my own sunshine.",
            "🎯 Goals. Grind. Glory.\n💫 Dream big, work hard.\n🔥 Unstoppable is my middle name.",
            "🧘 Peace, love, positivity.\n🌿 Simple living, high thinking.\n💚 Gratitude over everything."
        )
    )

    private var currentCategory = "Bikes"
    private var currentCaption = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(createUI())
    }

    private fun createUI(): LinearLayout {
        // Main container
        val mainLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(ContextCompat.getColor(this@MainActivity, android.R.color.white))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        }

        // Scroll view for content
        val scrollView = ScrollView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1f
            )
        }

        val contentLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 24, 20, 24)
        }

        // Header
        val headerLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setPadding(0, 0, 0, 24)
        }

        val title = TextView(this).apply {
            text = "✨ EasyEarn"
            textSize = 32f
            setTextColor(ContextCompat.getColor(this@MainActivity, android.R.color.black))
            setTypeface(null, android.graphics.Typeface.BOLD)
        }

        val subtitle = TextView(this).apply {
            text = "AI-style Caption Generator"
            textSize = 14f
            setTextColor(ContextCompat.getColor(this@MainActivity, android.R.color.darker_gray))
            setPadding(0, 8, 0, 0)
        }

        headerLayout.addView(title)
        headerLayout.addView(subtitle)
        contentLayout.addView(headerLayout)

        // Category Selection
        val categoryLabel = TextView(this).apply {
            text = "📂 Select Category"
            textSize = 14f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(this@MainActivity, android.R.color.black))
            setPadding(0, 0, 0, 12)
        }
        contentLayout.addView(categoryLabel)

        val categoryLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val categories = listOf("Bikes", "Fashion", "Food", "Travel", "Lifestyle")
        for (category in categories) {
            val btnCategory = MaterialButton(this).apply {
                text = category
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )
                setMargins(0, 0, 8, 0)
                setBackgroundColor(
                    if (category == currentCategory)
                        ContextCompat.getColor(this@MainActivity, android.R.color.holo_purple)
                    else
                        ContextCompat.getColor(this@MainActivity, android.R.color.darker_gray)
                )
                setOnClickListener {
                    currentCategory = category
                    recreateUI()
                }
            }
            categoryLayout.addView(btnCategory)
        }

        contentLayout.addView(categoryLayout)

        // Input Section
        val inputLabel = TextView(this).apply {
            text = "📝 Your Topic"
            textSize = 14f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(this@MainActivity, android.R.color.black))
            setPadding(0, 24, 0, 12)
        }
        contentLayout.addView(inputLabel)

        val input = EditText(this).apply {
            hint = "Photo ka topic likho… e.g. Bike night ride"
            textSize = 14f
            setPadding(16, 16, 16, 16)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setBackgroundColor(ContextCompat.getColor(this@MainActivity, android.R.color.darker_gray))
            setHintTextColor(ContextCompat.getColor(this@MainActivity, android.R.color.white))
            setTextColor(ContextCompat.getColor(this@MainActivity, android.R.color.white))
        }
        contentLayout.addView(input)

        // Result Card
        val resultCard = MaterialCardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setMargins(0, 24, 0, 24)
            cardElevation = 8f
            setCardBackgroundColor(ContextCompat.getColor(this@MainActivity, android.R.color.white))
        }

        val result = TextView(this).apply {
            text = "✨ Your caption will appear here..."
            textSize = 14f
            setTextColor(ContextCompat.getColor(this@MainActivity, android.R.color.darker_gray))
            setPadding(16, 16, 16, 16)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        resultCard.addView(result)
        contentLayout.addView(resultCard)

        // Button Layout
        val buttonLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val generateBtn = MaterialButton(this).apply {
            text = "🎨 Generate Caption"
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setMargins(0, 8, 0, 8)
            setOnClickListener {
                val topic = input.text.toString().trim()
                currentCaption = if (topic.isEmpty()) {
                    "❌ Pehle photo/topic likho!"
                } else {
                    val captionList = captions[currentCategory] ?: captions["Bikes"]!!
                    captionList.random()
                }
                result.text = currentCaption
            }
        }
        buttonLayout.addView(generateBtn)

        val copyBtn = MaterialButton(this).apply {
            text = "📋 Copy Caption"
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setMargins(0, 8, 0, 8)
            setBackgroundColor(ContextCompat.getColor(this@MainActivity, android.R.color.holo_green_dark))
            setOnClickListener {
                if (currentCaption.isNotEmpty()) {
                    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Caption", currentCaption)
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(this@MainActivity, "✅ Caption copied!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        buttonLayout.addView(copyBtn)

        val shareBtn = MaterialButton(this).apply {
            text = "📤 Share Caption"
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setMargins(0, 8, 0, 8)
            setBackgroundColor(ContextCompat.getColor(this@MainActivity, android.R.color.holo_blue_dark))
            setOnClickListener {
                if (currentCaption.isNotEmpty()) {
                    val shareIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, currentCaption)
                        type = "text/plain"
                    }
                    startActivity(Intent.createChooser(shareIntent, "Share Caption"))
                }
            }
        }
        buttonLayout.addView(shareBtn)

        val premiumBtn = MaterialButton(this).apply {
            text = "⭐ Unlock Premium"
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setMargins(0, 8, 0, 0)
            setBackgroundColor(ContextCompat.getColor(this@MainActivity, android.R.color.holo_orange_dark))
            setOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    "⭐ Premium: 100+ captions, no ads, custom styles!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        buttonLayout.addView(premiumBtn)

        contentLayout.addView(buttonLayout)
        scrollView.addView(contentLayout)
        mainLayout.addView(scrollView)

        return mainLayout
    }

    private fun recreateUI() {
        setContentView(createUI())
    }

    private fun LinearLayout.LayoutParams.setMargins(left: Int, top: Int, right: Int, bottom: Int) {
        setMargins(left, top, right, bottom)
    }
}
