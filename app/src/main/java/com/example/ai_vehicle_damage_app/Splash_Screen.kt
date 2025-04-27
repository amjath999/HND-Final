package com.example.ai_vehicle_damage_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ai_vehicle_damage_app.R.id.main

class Splash_Screen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)

        // Apply system bar insets to root layout (ConstraintLayout with id "main")
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Reference the "GET START" button (id = button2)
        val getStartedBtn = findViewById<Button>(R.id.button2)
        getStartedBtn.setOnClickListener {
            // Navigate to the SignUp activity
            val intent = Intent(this, Login::class.java)  // Corrected the Intent
            startActivity(intent)
        }

        // Reference and modify TextView (id = textView14)
        val textView = findViewById<TextView>(R.id.textView14)
        textView.text = "SAFE DRIVE" // You can update this dynamically if necessary
    }
}
