package com.example.ai_vehicle_damage_app

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var loginEmail: EditText
    private lateinit var loginPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var signupRedirectText: TextView
    private lateinit var passViewIcon: ImageView
    private lateinit var auth: FirebaseAuth
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // View bindings
        loginEmail = findViewById(R.id.txt_newpassword)
        loginPassword = findViewById(R.id.txt_re_newpassword)
        loginButton = findViewById(R.id.btn_updatePassword)
        signupRedirectText = findViewById(R.id.id_login)
        passViewIcon = findViewById(R.id.passViewIcon)

        auth = FirebaseAuth.getInstance()

        // Toggle password visibility
        passViewIcon.setOnClickListener {
            if (isPasswordVisible) {
                loginPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            } else {
                loginPassword.inputType = InputType.TYPE_CLASS_TEXT
            }
            passViewIcon.setImageResource(R.drawable.passview)
            isPasswordVisible = !isPasswordVisible
            loginPassword.setSelection(loginPassword.text.length)
        }

        // Login button click listener
        loginButton.setOnClickListener {
            val email = loginEmail.text.toString().trim()
            val password = loginPassword.text.toString().trim()

            if (!isValidEmail(email)) {
                loginEmail.error = "Enter a valid email"
                loginEmail.requestFocus()
                return@setOnClickListener
            }

            if (!isValidPassword(password)) {
                loginPassword.error = "Password must be at least 6 characters"
                loginPassword.requestFocus()
                return@setOnClickListener
            }

            // Firebase login
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Login Failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

        // Redirect to signup
        signupRedirectText.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }
    }

    // Email validation
    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Password validation
    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }
}
