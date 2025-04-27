package com.example.ai_vehicle_damage_app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class SignUp : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var loginRedirectText: TextView


    private lateinit var databaseReference: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        // Bind UI components
        // EditText for Name
        nameEditText = findViewById(R.id.txt_newpassword) // ID updated from 'editTextText'

// EditText for Email
        emailEditText = findViewById(R.id.editTextText5) // ID updated from 'editTextText5'

// EditText for Password
        passwordEditText = findViewById(R.id.txt_re_newpassword) // ID updated from 'editTextText2'


// Register Button
        registerButton = findViewById(R.id.btn_updatePassword) // ID updated from 'button'

// Login Redirect Text
        loginRedirectText = findViewById(R.id.id_login) // ID updated from 'textView6'


        // Set click listeners
        registerButton.setOnClickListener { registerUser() }
        loginRedirectText.setOnClickListener {
            startActivity(Intent(this@SignUp, Login::class.java))
            finish()
        }
    }

    // Main registration logic
    private fun registerUser() {
        val name = nameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()


        registerButton.isEnabled = false

        // Check if user already exists
        databaseReference.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        showToast("User already exists! Try logging in.")
                        emailEditText.error = "Email already in use!"
                        registerButton.isEnabled = true
                    } else {
                        // Proceed with registration if user doesn't exist
                        registerNewUser(name, email, password)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    showToast("Database error: ${databaseError.message}")
                    registerButton.isEnabled = true
                }
            })
    }

    // Register a new user and save to Firebase
    private fun registerNewUser(name: String, email: String, password: String) {
        val userId = databaseReference.push().key // Generate unique ID for the user
        val user = User(name, email, password) // Create user object

        userId?.let {
            databaseReference.child(it).setValue(user)
                .addOnCompleteListener { task ->

                    registerButton.isEnabled = true
                    if (task.isSuccessful) {
                        showToast("User registered successfully!")
                        clearInputFields()
                    } else {
                        showToast("Registration failed. Please try again.")
                    }
                }
        }
    }

    // Validate user input
    private fun isValidInput(name: String, email: String, password: String, confirmPassword: String): Boolean {
        if (TextUtils.isEmpty(name)) {
            nameEditText.error = "Name is required!"
            return false
        }

        if (TextUtils.isEmpty(email)) {
            emailEditText.error = "Email is required!"
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Invalid email format!"
            return false
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.error = "Password is required!"
            return false
        }



        if (password.length < 6) {
            passwordEditText.error = "Password must be at least 6 characters long!"
            return false
        }

        return true
    }

    // Clear input fields after successful registration
    private fun clearInputFields() {
        nameEditText.setText("")
        emailEditText.setText("")
        passwordEditText.setText("")

    }

    // Utility function to show Toast message
    private fun showToast(message: String) {
        Toast.makeText(this@SignUp, message, Toast.LENGTH_SHORT).show()
    }
}
