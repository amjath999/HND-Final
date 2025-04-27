package com.example.ai_vehicle_damage_app

data class User(
    var name: String = "",
    var email: String = "",
    var password: String = ""
) {
    // Default constructor required for calls to DataSnapshot.getValue(User::class.java)
    constructor() : this("", "", "")

    // Getter and setter methods are automatically handled by Kotlin's data class
}
